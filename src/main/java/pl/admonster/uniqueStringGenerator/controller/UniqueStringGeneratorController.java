package pl.admonster.uniqueStringGenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.admonster.uniqueStringGenerator.UniqueStringGeneratorService;
import pl.admonster.uniqueStringGenerator.UniqueStringGeneratorWorker;
import pl.admonster.uniqueStringGenerator.UserRequest;

import java.util.ArrayList;

@RestController
public class UniqueStringGeneratorController {

    @Autowired
    UniqueStringGeneratorService uniqueStringGeneratorService;

    @GetMapping("/workingJobs")
    public int getWorkingJobsCount(){
        return UniqueStringGeneratorWorker.UniqueStringGeneratorAction.activeCount();
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
