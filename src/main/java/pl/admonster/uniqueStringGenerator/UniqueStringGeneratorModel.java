package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueStringGeneratorModel{

    @Autowired
    UserRequest userRequest;

    //Thread runnable = new Thread();

    public static int startGeneration(UserRequest userRequest) {

        StringBuilder singleUniqueString;
        int stringLength = 0;
        int randomCharIndex = 0;

        for(int i = 0; i < userRequest.getHowManyWanted(); i++){
            singleUniqueString= new StringBuilder();
            stringLength = userRequest.getMinLength() + (int) Math.round(Math.random() * (userRequest.getMaxLength() - userRequest.getMinLength()));

            for(int j = 0; j < stringLength; j++){
                randomCharIndex = (int) Math.round(Math.random() * (userRequest.getUserChars().size() - 1));
                singleUniqueString.append(userRequest.getUserChars().get(randomCharIndex));
            }

            if(!singleUniqueString.isEmpty())
                userRequest.getGeneratedResult().add(singleUniqueString.toString());
        }

        userRequest.setJobFinished(true);

        return 1;
    }

   //@Override
    //public void run() {

    //}
}
