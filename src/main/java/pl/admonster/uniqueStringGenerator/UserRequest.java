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

    public boolean isNewStringUnique(String s){
        for (String previousSingleUniqueString : getGeneratedResult()) {
            if(previousSingleUniqueString.equals(s))
                return false;
        }

        return true;
    }

}
