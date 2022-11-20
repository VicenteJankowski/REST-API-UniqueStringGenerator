package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.update("INSERT INTO userRequests(userChars, maxLength, minLength, howManyWanted, jobFinished) " +
                        "VALUES (?, ?, ?, ?, ?)",
                        parseUserCharsToDB(userRequest.getUserChars()),
                        userRequest.getMaxLength(),
                        userRequest.getMinLength(),
                        userRequest.getHowManyWanted(),
                        UserRequest.Status.NOT_STARTED
                );
    }

    public int changeUserRequestStatusInDB(boolean status, int requestId){
        return jdbcTemplate.update("UPDATE userRequests SET jobFinished=? WHERE id=?", status, requestId);
    }

    public int startGeneration(UserRequest userRequest) {

        addUserRequestToDB(userRequest);

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

        userRequest.setStatus(UserRequest.Status.FINISHED);
        //changeUserRequestStatusInDB(UserRequest.Status.FINISHED, userRequest.getId());

        return 1;
    }

}
