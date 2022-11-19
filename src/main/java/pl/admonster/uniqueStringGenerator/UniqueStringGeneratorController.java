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
        uniqueStringGeneratorModel.startGeneration(userRequest);

        for (String uniqueString : userRequest.getGeneratedResult()) {
            System.out.println(uniqueString);
        }

        return 1;
    }

}
