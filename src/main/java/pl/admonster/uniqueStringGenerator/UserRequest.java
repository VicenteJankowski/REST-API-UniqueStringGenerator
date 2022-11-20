package pl.admonster.uniqueStringGenerator;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class UserRequest {
    class Status {
        public static final int NOT_STARTED = 0;
        public static final int WORKING = 1;
        public static final int FINISHED = 2;
    }

    private int id;
    @NonNull private final ArrayList<Character> userChars;
    @NonNull private final int maxLength;
    @NonNull private final int minLength;
    @NonNull private final int howManyWanted;

    private ArrayList<String> generatedResult = new ArrayList<String>();
    private int status = Status.NOT_STARTED;

    public int possiblePermutations(){
        int possiblePermutations = 0;

        for(int strLen = minLength; strLen <= maxLength; strLen++){
            possiblePermutations += (int) Math.pow(userChars.size(), strLen);
        }

        return possiblePermutations;
    }

    public boolean isPossibletoFindThatManyResults(){
        return howManyWanted > possiblePermutations() ? false : true;
    }

}
