import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CallCenter {
    static final int TIME_FOR_PROCESSING_CALL = 3000;
    static final int TIME_OUT = 1000;
    static final int NUMBER_OF_CALLS_PER_TIME_UNIT = 30;

    Queue<String> callsQueue = new ConcurrentLinkedQueue<>();

    public Queue<String> getCallsQueue() {
        return callsQueue;
    }

    public void addCallToQueue() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < NUMBER_OF_CALLS_PER_TIME_UNIT; i++) {
                    String phoneNumber = "+792904587" + i;
                    callsQueue.add(phoneNumber);
                    System.out.println("Call Center got a call from phone number: " + phoneNumber);
                    Thread.sleep(TIME_OUT);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Adding calls to queue was interrupted");
            ;
        }
    }

    public void takeCallFromQueue() {
        try {
            String phoneNumber;
            while (!Thread.currentThread().isInterrupted()) {
                while (callsQueue.size() > 0) {
                    if ((phoneNumber = callsQueue.poll()) != null) {
                        System.out.println("Call Center specialist " + Thread.currentThread().getName() + " took call from phone number: " + phoneNumber);
                    }
                    Thread.sleep(TIME_FOR_PROCESSING_CALL);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Conversion between specialist " + Thread.currentThread().getName() +
                    " and subscriber was interrupted suddenly");
        }
    }
}
