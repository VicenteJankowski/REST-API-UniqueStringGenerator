package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UniqueStringGeneratorModel{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String userCharsToDB(ArrayList<Character> userChars){
        StringBuilder str = new StringBuilder();

        userChars.forEach(userChar -> str.append(userChar + ","));

        return str.substring(0, str.length() - 1);
    }
    public int addUserRequestToDB(UserRequest userRequest){
        return jdbcTemplate.update("INSERT INTO userRequests(userChars, maxLength, minLength, howManyWanted, jobFinished) " +
                        "VALUES (?, ?, ?, ?, ?)",
                        userCharsToDB(userRequest.getUserChars()),
                        userRequest.getMaxLength(),
                        userRequest.getMinLength(),
                        userRequest.getHowManyWanted(),
                        0
                );
    }

    public int startGeneration(UserRequest userRequest) {

        addUserRequestToDB(userRequest);

        StringBuilder stringBuilder;
        int stringLength = 0;
        int randomCharIndex = 0;

        for(int i = 0; i < userRequest.getHowManyWanted(); i++){
            stringBuilder = new StringBuilder();
            boolean unique = true;
            stringLength = userRequest.getMinLength() + (int) Math.round(Math.random() * (userRequest.getMaxLength() - userRequest.getMinLength()));

            for(int j = 0; j < stringLength; j++){
                randomCharIndex = (int) Math.round(Math.random() * (userRequest.getUserChars().size() - 1));
                stringBuilder.append(userRequest.getUserChars().get(randomCharIndex));
            }

            if(userRequest.isNewStringUnique(stringBuilder.toString())) {
                userRequest.getGeneratedResult().add(stringBuilder.toString());
            }
        }

        userRequest.setJobFinished(true);

        return 1;
    }

}
