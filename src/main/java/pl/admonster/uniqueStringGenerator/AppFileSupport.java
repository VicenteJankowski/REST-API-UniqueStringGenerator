package pl.admonster.uniqueStringGenerator;

import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@NoArgsConstructor
public class AppFileSupport {

    private final static String FILE_PREFIX = "job";
    private final static String FILE_EXTENSION = ".txt";

    public static int writeAllUniqueStringToFile(UserRequest userRequest){
        try {
            FileWriter fileWriter = new FileWriter(FILE_PREFIX + userRequest.getId() + FILE_EXTENSION);
            BufferedWriter buffFileWriter = new BufferedWriter(fileWriter);

            for(String singleUniqueString : userRequest.getGeneratedResult()){
                buffFileWriter.write(singleUniqueString);
                buffFileWriter.newLine();
            }
            buffFileWriter.close();
        } catch (IOException e) {
            System.out.println("Saving to file error: " + e.getMessage());
        }

        return 1;
    }

}
