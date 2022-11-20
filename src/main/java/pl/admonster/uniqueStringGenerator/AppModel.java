package pl.admonster.uniqueStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppModel{

    @Autowired
    UserRequestRepository userRequestRepository;

   public int proceedJob(UserRequest userRequest) throws Exception{

        if(!userRequest.isPossibletoFindThatManyResults())
            throw new Exception();
        System.out.println("Liczba możliwych kombinacji=" + userRequest.possiblePermutations());
        userRequestRepository.addUserRequestToDB(userRequest);

        new AppWorker(userRequest, userRequestRepository);

        return 1;
    }

}
