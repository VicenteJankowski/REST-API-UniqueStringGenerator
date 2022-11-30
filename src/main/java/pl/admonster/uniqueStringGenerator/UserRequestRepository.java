package pl.admonster.uniqueStringGenerator;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
public class UserRequestRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String parseUserCharsToDB(final ArrayList<Character> userChars) {
        StringBuilder stringBuilder = new StringBuilder();

        userChars.forEach(userChar -> stringBuilder.append(userChar + ","));

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
    public int addUserRequestToDB(final UserRequest userRequest) {

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO userRequests(userChars, maxLength, minLength, howManyWanted, status) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(conn -> {

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, parseUserCharsToDB(userRequest.getUserChars()));
            preparedStatement.setInt(2, userRequest.getMaxLength());
            preparedStatement.setInt(3, userRequest.getMinLength());
            preparedStatement.setInt(4, userRequest.getHowManyWanted());
            preparedStatement.setString(5, String.valueOf(UserRequest.Status.NOT_STARTED));

            return preparedStatement;

        }, generatedKeyHolder);

        userRequest.setId(generatedKeyHolder.getKey().intValue());
        System.out.println("UserRequestID=" + userRequest.getId());

        return 1;
    }

    public int changeUserRequestStatusInDB(final UserRequest.Status status, final int requestId) {
        return jdbcTemplate.update("UPDATE userRequests SET status=? WHERE id=?", String.valueOf(status), requestId);
    }

    public List<UserRequest> getFinishedJobsIdFromDB(){
        return jdbcTemplate.query(
                "SELECT id FROM userRequests WHERE status = " + UserRequest.Status.FINISHED,
                BeanPropertyRowMapper.newInstance(UserRequest.class));
    }
}
