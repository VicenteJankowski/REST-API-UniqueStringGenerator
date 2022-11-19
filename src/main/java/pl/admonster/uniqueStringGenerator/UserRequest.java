package pl.admonster.uniqueStringGenerator;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class UserRequest {

    @NonNull private final ArrayList<Character> userChars;
    @NonNull private final int maxLength;
    @NonNull private final int minLength;
    @NonNull private final int howManyWanted;

    private ArrayList<String> generatedResult = new ArrayList<String>();
    private boolean JobFinished = false;

}
