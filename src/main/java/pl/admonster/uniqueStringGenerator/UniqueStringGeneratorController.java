package pl.admonster.uniqueStringGenerator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniqueStringGeneratorController {

    @PostMapping("/generate")
    public int startGeneration(@RequestBody UserRequest userRequest){
        UniqueStringGeneratorModel.startGeneration(userRequest);

        for (String uniqueString : userRequest.getGeneratedResult()) {
            System.out.println(uniqueString);
        }

        return 1;
    }

}
