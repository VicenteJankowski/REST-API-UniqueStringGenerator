package pl.admonster.uniqueStringGenerator;

import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class UniqueStrGenerator {

    public static ArrayList<String> generate(UserRequest userRequest){
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
