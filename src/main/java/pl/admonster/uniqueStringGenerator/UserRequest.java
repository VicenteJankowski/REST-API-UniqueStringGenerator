package pl.admonster.uniqueStringGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserRequest {
    enum Status { NOT_STARTED, WORKING, FINISHED }

    private int id;
    @NonNull private ArrayList<Character> userChars;
    @NonNull private int maxLength;
    @NonNull private int minLength;
    @NonNull private int howManyWanted;

    private ArrayList<String> generatedResult = new ArrayList<>();
    private Status status = Status.NOT_STARTED;

    public int possiblePermutations(){
        int possiblePermutations = 0;

        for (int strLen = minLength; strLen <= maxLength; strLen++) {
            possiblePermutations += (int) Math.pow(userChars.size(), strLen);
        }

        return possiblePermutations;
    }

    public boolean isPossibleToFindThatManyResults() {
        return howManyWanted <= possiblePermutations();
    }

}
