import java.util.concurrent.Semaphore;
import java.time.LocalTime;

public class Cars implements Runnable {
    private int carId;
    private Semaphore dayParkingSpaces;
    private Semaphore nightParkingSpaces;

    //Конструктор для машини, що приймає ідентифікатор машини та семафори
    public Cars(int carId, Semaphore dayParkingSpaces, Semaphore nightParkingSpaces) {
        this.carId = carId;
        this.dayParkingSpaces = dayParkingSpaces;
        this.nightParkingSpaces = nightParkingSpaces;
    }

    @Override
    public void run() {
        try {
            //Перевіряється час доби і вибираємо відповідний семафор
            if (isDayTime()) {
                System.out.println("Машина " + carId + " приїхала вдень.");
                parkCar(dayParkingSpaces);
            } else {
                System.out.println("Машина " + carId + " приїхала вночі.");
                parkCar(nightParkingSpaces);
            }
        } catch (InterruptedException e) {
            System.out.println("Машина " + carId + " була перервана при спробі припаркуватися.");
            Thread.currentThread().interrupt(); //Відновлюємо статус переривання
        }
    }

    // Метод для паркування машини
    private void parkCar(Semaphore parkingSpaces) throws InterruptedException {
        System.out.println("Машина " + carId + " чекає на місце для паркування.");
        parkingSpaces.acquire(); //Очікується на вільне місце
        System.out.println("Машина " + carId + " успішно припаркувалася.");
        Thread.sleep(2000); //Симуляція того, скільки часу машина стоїть
        System.out.println("Машина " + carId + " покидає паркінг.");
        parkingSpaces.release(); //Звільняться місце після виїзду
    }

    //Метод для визначення часу доби
    private boolean isDayTime() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startDay = LocalTime.of(6, 0);  //06:00
        LocalTime endDay = LocalTime.of(21, 0);   //21:00
        return currentTime.isAfter(startDay) && currentTime.isBefore(endDay);
    }
}
