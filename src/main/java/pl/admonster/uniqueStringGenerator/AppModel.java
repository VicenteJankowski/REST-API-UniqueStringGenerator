package pl.admonster.uniqueStringGenerator;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class AppModel{

    @Autowired
    UserRequestRepository userRequestRepository;

    AppWorker appWorker;

   public int proceedJob(UserRequest userRequest) throws Exception{

        if(!userRequest.isPossibletoFindThatManyResults())
            throw new Exception();
        System.out.println("Liczba możliwych kombinacji=" + userRequest.possiblePermutations());
        userRequestRepository.addUserRequestToDB(userRequest);

        appWorker = new AppWorker(userRequest, userRequestRepository);

        return 1;
    }

}
