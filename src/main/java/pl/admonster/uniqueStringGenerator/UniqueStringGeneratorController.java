package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UniqueStringGeneratorController {

    @Autowired
    UniqueStringGeneratorService uniqueStringGeneratorService;

    @Autowired
    UserRequestRepository userRequestRepository;

    @GetMapping("/workingJobs")
    public int getWorkingJobsCount(){
        return UniqueStringGeneratorWorker.UniqueStringGeneratorAction.activeCount();
    }

    @GetMapping("/results")
    public ResponseEntity getResults() {

        List<UserRequest> finshedUserRequest = userRequestRepository.getFinishedJobsIdFromDB();
        List<String> finishedJobsFilePath = new ArrayList<>();

        finshedUserRequest.forEach(
                userRequest -> finishedJobsFilePath.add(
                        UniqueStringGeneratorFileSupport.FILE_PREFIX + userRequest.getId() + UniqueStringGeneratorFileSupport.FILE_EXTENSION
                )
        );

        File zipResults = new File("");

        try {
            zipResults = UniqueStringGeneratorFileSupport.createZipWithResults(finishedJobsFilePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Path path = Paths.get(zipResults.getPath());
        Resource urlResource = null;
        try {
            urlResource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipResults.getName() + "\"")
                .body(urlResource);

    }

    @PostMapping("/generate")
    public int startGeneration(@RequestBody final ArrayList<UserRequest> userRequest) {
        try {
            for (UserRequest singleUserRequest : userRequest) {
                uniqueStringGeneratorService.proceedJob(singleUserRequest);
            }
        }
        catch (Exception e) {
            System.out.println("Error: impossible to create that many results.");
        }

        return 1;
    }

}
