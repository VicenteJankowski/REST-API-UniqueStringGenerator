package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AppController {

    @Autowired
    AppModel appModel;

    @GetMapping("/workingJobs")
    public int getWorkingJobsCount(){
        return AppWorker.UniqueStringGeneratorAction.activeCount();
    }

    @PostMapping("/generate")
    public int startGeneration(@RequestBody ArrayList<UserRequest> userRequest){
        try {
            for(UserRequest singleUserRequest : userRequest){
                appModel.proceedJob(singleUserRequest);
            }
        }
        catch(Exception e){
            System.out.println("Error: impossible to create that many results.");
        }

        return 1;
    }

}
