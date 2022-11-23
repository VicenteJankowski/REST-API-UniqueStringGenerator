package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import lombok.Data;

@Data
@Service
public class UniqueStringGeneratorService {

    @Autowired
    UserRequestRepository userRequestRepository;

    UniqueStringGeneratorWorker uniqueStringGeneratorWorker;

   public int proceedJob(final UserRequest userRequest) throws Exception {

        if (!userRequest.isPossibletoFindThatManyResults())
            throw new Exception();

        System.out.println("Liczba możliwych kombinacji=" + userRequest.possiblePermutations());
        userRequestRepository.addUserRequestToDB(userRequest);

        uniqueStringGeneratorWorker = new UniqueStringGeneratorWorker(userRequest, userRequestRepository);

        return 1;
    }

}
