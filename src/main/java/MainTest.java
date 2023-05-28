import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainTest {
    public static void main(String[] args) {
        final int THREAD_COUNT = 10;

        Result result;
        long startTime = 0, endTime = 0, wastedTime, seconds, milliseconds;
        String formattedTime;

        System.out.println("Running test cases\n");
        for (int testNumber = 0; testNumber < 10; testNumber++) {
            int[][] g = FileLoader.getGraphFromFile(testNumber);
            BFS linearBFS = new BFS(g);
            startTime = System.currentTimeMillis();
            Result linearRes = linearBFS.search(0);
            endTime = System.currentTimeMillis();
            wastedTime = endTime - startTime;
            seconds = wastedTime / 1000;
            milliseconds = wastedTime % 1000;
            formattedTime = String.format("%02ds:%03dms", seconds, milliseconds);
            System.out.println("Linear search time for " + testNumber + " test: " + formattedTime);

            boolean isEqual = false;

            try {
                BFS parallelBFS = new BFS(g);
                startTime = System.currentTimeMillis();
                Result parallelRes = parallelBFS.parallelSearch(0, THREAD_COUNT);
                endTime = System.currentTimeMillis();
                wastedTime = endTime - startTime;
                seconds = wastedTime / 1000;
                milliseconds = wastedTime % 1000;
                formattedTime = String.format("%02ds:%03dms", seconds, milliseconds);
                System.out.println("Parallel search time for " + testNumber + " test: " + formattedTime);

                isEqual = Arrays.equals(linearRes.getDistances(), parallelRes.getDistances());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if (isEqual) {
                System.out.println("Answers are equal\n");
            } else {
                System.out.println("Answers are NOT equal\n");
            }
        }
    }
}
