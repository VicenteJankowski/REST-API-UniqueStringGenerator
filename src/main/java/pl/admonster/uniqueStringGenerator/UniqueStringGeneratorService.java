package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import lombok.Data;

@Data
@Service
public class UniqueStringGeneratorService {

    @Autowired
    UserRequestRepository userRequestRepository;

    UniqueStringGeneratorWorker uniqueStringGeneratorWorker;

   public int proceedJob(final UserRequest userRequest) throws IllegalArgumentException {

        if (!userRequest.isPossibleToFindThatManyResults())
            throw new IllegalArgumentException("Impossible to create that many results");

        System.out.println("Number of possible variations=" + userRequest.possiblePermutations());
        userRequestRepository.addUserRequestToDB(userRequest);

        uniqueStringGeneratorWorker = new UniqueStringGeneratorWorker(userRequest, userRequestRepository);

        return 1;
    }

}
