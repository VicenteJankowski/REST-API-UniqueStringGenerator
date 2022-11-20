package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class AppModel {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String parseUserCharsToDB(ArrayList<Character> userChars){
        StringBuilder str = new StringBuilder();

        userChars.forEach(userChar -> str.append(userChar + ","));

        return str.substring(0, str.length() - 1);
    }
    public int addUserRequestToDB(UserRequest userRequest){

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO userRequests(userChars, maxLength, minLength, howManyWanted, jobFinished) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(conn -> {

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, parseUserCharsToDB(userRequest.getUserChars()));
            preparedStatement.setInt(2, userRequest.getMaxLength());
            preparedStatement.setInt(3, userRequest.getMinLength());
            preparedStatement.setInt(4, userRequest.getHowManyWanted());
            preparedStatement.setObject(5, UserRequest.Status.NOT_STARTED);

            return preparedStatement;

        }, generatedKeyHolder);

        userRequest.setId(generatedKeyHolder.getKey().intValue());
        System.out.println("USerRequestID=" + userRequest.getId());

        return 1;
    }

    public int changeUserRequestStatusInDB(int status, int requestId){
        return jdbcTemplate.update("UPDATE userRequests SET jobFinished=? WHERE id=?", status, requestId);
    }

    public int proceedJob(UserRequest userRequest) throws Exception{

            if(!userRequest.isPossibletoFindThatManyResults())
                throw new Exception();

            System.out.println("Liczba możliwych kombinacji=" + userRequest.possiblePermutations());
            addUserRequestToDB(userRequest);
            UniqueStrGenerator.generate(userRequest);
            userRequest.setStatus(UserRequest.Status.FINISHED);
            AppFileSupport.writeAllUniqueStringToFile(userRequest);
            changeUserRequestStatusInDB(UserRequest.Status.FINISHED, userRequest.getId());

        return 1;
    }

}
