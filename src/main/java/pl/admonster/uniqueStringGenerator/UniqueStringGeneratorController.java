package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniqueStringGeneratorController {

    @Autowired
    UniqueStringGeneratorModel uniqueStringGeneratorModel;

    @PostMapping("/generate")
    public int startGeneration(@RequestBody UserRequest userRequest){
        try {
            uniqueStringGeneratorModel.proceedJob(userRequest);
        }
        catch(Exception e){
            System.out.println("Error: impossible to create that many results.");
        }

        return 1;
    }

}
