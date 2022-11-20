package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class UniqueStringGeneratorModel{

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

    public int startGeneration(UserRequest userRequest) {

        addUserRequestToDB(userRequest);
        generateUniqueStrings(userRequest);
        userRequest.setStatus(UserRequest.Status.FINISHED);
        changeUserRequestStatusInDB(UserRequest.Status.FINISHED, userRequest.getId());

        return 1;
    }

    public ArrayList<String> generateUniqueStrings(UserRequest userRequest){
        StringBuilder strBuild;
        int strLen = 0;
        int randomCharIndex = 0;

        for(int i = 0; i < userRequest.getHowManyWanted(); i++){
            strBuild = new StringBuilder();
            strLen = userRequest.getMinLength() + (int) Math.round(Math.random() * (userRequest.getMaxLength() - userRequest.getMinLength()));

            for(int j = 0; j < strLen; j++){
                randomCharIndex = (int) Math.round(Math.random() * (userRequest.getUserChars().size() - 1));
                strBuild.append(userRequest.getUserChars().get(randomCharIndex));
            }

            if(userRequest.isNewStringUnique(strBuild.toString())) {
                userRequest.getGeneratedResult().add(strBuild.toString());
            }
        }

        return userRequest.getGeneratedResult();
    }

}
