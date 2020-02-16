public class Client implements Runnable{
    @Override
    public void run() {
        try {
            goToBarbershop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToBarbershop() throws InterruptedException {
        Barbershop.stayInQueue(this);
    }
}
