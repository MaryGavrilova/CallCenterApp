public class Main {
    static final int TELEPHONE_STATION_WORK_TIME = 20000;
    static final int TIME_FOR_CALL_CENTER_TO_FINISH_WORK = 1000;
    static final int QUANTITY_OF_SPECIALISTS = 2;

    public static void main(String[] args) {
        try {
            CallCenter callCenter = new CallCenter();

            Thread automaticTelephoneStation = new Thread(callCenter::addCallToQueue);
            automaticTelephoneStation.start();

            ThreadGroup callCenterSpecialists = new ThreadGroup("Call Center Specialists");
            for (int i = 0; i < QUANTITY_OF_SPECIALISTS; i++) {
                Thread specialist = new Thread(callCenterSpecialists, callCenter::takeCallFromQueue);
                specialist.setName("№ " + (i + 1));
                specialist.start();
            }

            Thread.sleep(TELEPHONE_STATION_WORK_TIME);

            while (!automaticTelephoneStation.isInterrupted()) {
                automaticTelephoneStation.interrupt();
            }
            System.out.println("Telephone Station has finished generating calls");
            while (callCenter.getCallsQueue().size() > 0) {
                Thread.sleep(TIME_FOR_CALL_CENTER_TO_FINISH_WORK);

            }
            while (callCenterSpecialists.activeCount() > 0) {
                callCenterSpecialists.interrupt();
            }
            System.out.println("Call Center specialists have finished taking calls");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
