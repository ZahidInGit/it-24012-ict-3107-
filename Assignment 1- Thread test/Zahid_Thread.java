
import java.util.concurrent.atomic.AtomicLong;

public class Zahid_Thread extends Thread {

    
    static AtomicLong safeCounter = new AtomicLong(0);

    static long unsafeCounter = 0;

    long instanceCounter = 0;

    private final int increments;
    private final boolean threadSafe;

    public Zahid_Thread(int increments, boolean threadSafe) {
        this.increments = increments;
        this.threadSafe = threadSafe;
    }

    @Override
    public void run() {
        for (int i = 0; i < increments; i++) {
            if (threadSafe) {
                safeCounter.incrementAndGet();
            } else {
                unsafeCounter++;
            }

            instanceCounter++;
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        if (args.length != 3) {
            System.out.println(
                "Usage: java Zahid_Thread <threads> <increments> <true|false>"
            );
            return;
        }

        int numberOfThreads = Integer.parseInt(args[0]);
        int increments = Integer.parseInt(args[1]);
        boolean threadSafe = Boolean.parseBoolean(args[2]);

        if (numberOfThreads <= 0 || increments < 0
                || (!args[2].equalsIgnoreCase("true")
                && !args[2].equalsIgnoreCase("false"))) {
            System.out.println("Invalid input arguments.");
            return;
        }

        safeCounter.set(0);
        unsafeCounter = 0;

        Zahid_Thread[] threads =
                new Zahid_Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Zahid_Thread(increments, threadSafe);
            threads[i].start();
        }

        long nonStaticTotal = 0;

        for (Zahid_Thread thread : threads) {
            thread.join();
            nonStaticTotal += thread.instanceCounter;
        }

        long staticCount = threadSafe
                ? safeCounter.get()
                : unsafeCounter;

        long expectedCount =
                (long) numberOfThreads * increments;

        long absoluteDifference =
                Math.abs(staticCount - nonStaticTotal);

        double percentageDifference;

        if (nonStaticTotal == 0) {
            percentageDifference =
                    staticCount == 0 ? 0.0 : Double.NaN;
        } else {
            percentageDifference =
                    (absoluteDifference * 100.0)
                    / nonStaticTotal;
        }

        System.out.println("========== RESULTS ==========");
        System.out.println("Mode: "
                + (threadSafe ? "THREAD-SAFE" : "UNSYNCHRONIZED"));
        System.out.println("Number of threads: " + numberOfThreads);
        System.out.println("Increments per thread: " + increments);
        System.out.println("Expected count: " + expectedCount);
        System.out.println("Static count: " + staticCount);
        System.out.println("Non-static total: " + nonStaticTotal);
        System.out.println("Absolute difference: "
                + absoluteDifference);

        if (Double.isNaN(percentageDifference)) {
            System.out.println("Percentage difference: Undefined");
        } else {
            System.out.printf(
                    "Percentage difference: %.4f%%%n",
                    percentageDifference
            );
        }

        System.out.println("=============================");
    }
}

