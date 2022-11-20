package pl.admonster.uniqueStringGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class UniqueStrGenerator {

    private final int requestId;
    private ArrayList<String> generatedStr = new ArrayList<String>();
    public boolean isNewStringUnique(String s){
        for (String previousSingleUniqueString : getGeneratedStr()) {
            if(previousSingleUniqueString.equals(s))
                return false;
        }

        return true;
    }

    public int generate(UserRequest userRequest){
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

            if(isNewStringUnique(strBuild.toString())) {
                generatedStr.add(strBuild.toString());
            }
        }

        return 1;
    }

}
