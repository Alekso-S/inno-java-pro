import component.Pool;

public class Main {
    public static void main(String[] args) {
        Pool pool = new Pool(4);

        pool.execute(() -> System.out.println(1));
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(2);
        });
        pool.shutdown();
        System.out.println(pool.isTerminated());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(pool.isTerminated());
        pool.execute(() -> System.out.println(3));
    }
}
