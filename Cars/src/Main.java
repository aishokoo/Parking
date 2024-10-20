import java.util.concurrent.Semaphore;

public class Main {
    //Семафори для денних та нічних паркувальних місць
    private static Semaphore dayParkingSpaces = new Semaphore(5, true);
    private static Semaphore nightParkingSpaces = new Semaphore(8, true);

    public static void main(String[] args) {
        //Потоки для машин
        for (int i = 1; i <= 10; i++) {
            Cars car = new Cars(i, dayParkingSpaces, nightParkingSpaces);
            Thread thread = new Thread(car);
            thread.start();
        }
    }
}
