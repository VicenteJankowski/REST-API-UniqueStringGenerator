package pl.admonster.uniqueStringGenerator;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class UniqueStrGenerator {

    private final int requestId;
    private ArrayList<String> generatedStr = new ArrayList<>();
    public boolean isNewStringUnique(final String s) {
        for (String previousSingleUniqueString : getGeneratedStr()) {
            if (previousSingleUniqueString.equals(s))
                return false;
        }

        return true;
    }

    public int generate(final UserRequest userRequest) {
        int stringLength = 0;
        int randomCharIndex = 0;

        for (int i = 0; i < userRequest.getHowManyWanted(); i++) {
            final StringBuffer strBuild = new StringBuffer();
            stringLength = userRequest.getMinLength() + (int) Math.round(Math.random() * (userRequest.getMaxLength() - userRequest.getMinLength()));

            for (int j = 0; j < stringLength; j++) {
                randomCharIndex = (int) Math.round(Math.random() * (userRequest.getUserChars().size() - 1));
                strBuild.append(userRequest.getUserChars().get(randomCharIndex));
            }

            if (isNewStringUnique(strBuild.toString())) {
                generatedStr.add(strBuild.toString());
            }
        }

        return 1;
    }

}
