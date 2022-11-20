package pl.admonster.uniqueStringGenerator;

import lombok.Data;

@Data
public class AppWorker implements Runnable{

    private final UserRequest userRequest;
    private final UserRequestRepository userRequestRepository;
    private UniqueStrGenerator uniqueStrGenerator;
    public static final ThreadGroup UniqueStringGeneratorAction = new ThreadGroup("UniqueStringGeneratorAction");
    private Thread runner;

    AppWorker(UserRequest userRequest, UserRequestRepository userRequestRepository){
        this.userRequest = userRequest;
        this.userRequestRepository = userRequestRepository;
        uniqueStrGenerator = new UniqueStrGenerator(userRequest.getId());

        if(runner == null){
            runner = new Thread(UniqueStringGeneratorAction, this);
            runner.start();
        }
    }

    @Override
    public void run() {
        userRequest.setStatus(UserRequest.Status.WORKING);
        userRequestRepository.changeUserRequestStatusInDB(UserRequest.Status.WORKING, userRequest.getId());

        uniqueStrGenerator.generate(userRequest);
        userRequest.setGeneratedResult(uniqueStrGenerator.getGeneratedStr());
        userRequest.setStatus(UserRequest.Status.FINISHED);

        AppFileSupport.writeAllUniqueStringToFile(userRequest);
        userRequestRepository.changeUserRequestStatusInDB(UserRequest.Status.FINISHED, userRequest.getId());

        System.out.println("Execution of job number " + userRequest.getId() + " is finished");
    }
}
