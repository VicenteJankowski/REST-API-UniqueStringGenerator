package pl.admonster.uniqueStringGenerator;

import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@NoArgsConstructor
public class UniqueStringGeneratorFileSupport {

    public final static String FILE_PREFIX = "job";
    public final static String FILE_EXTENSION = ".txt";
    public final static String RESULTS_NAME = "UniqueStringGenerator";

    public static int writeAllUniqueStringToFile(final UserRequest userRequest) {
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

    public static File createZipWithResults(final List<String> filePath) throws IOException {

        File zipResults = new File(RESULTS_NAME + ".zip");
        FileOutputStream zipFileOut = new FileOutputStream(zipResults);
        ZipOutputStream zipOut = new ZipOutputStream(zipFileOut);

        for (String singleFile : filePath) {
            FileSystemResource resource = new FileSystemResource(singleFile);
            if(!resource.exists()) continue;

            ZipEntry zipEntry = new ZipEntry(resource.getFilename());
            zipEntry.setSize(resource.contentLength());
            zipOut.putNextEntry(zipEntry);
            StreamUtils.copy(resource.getInputStream(), zipOut);
            zipOut.closeEntry();
         }

        zipOut.finish();
        zipOut.close();

        return zipResults;
    }

}
