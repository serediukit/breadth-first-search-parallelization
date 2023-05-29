import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) {
        final int THREAD_COUNT = 20;

        long startTime = 0, endTime = 0, wastedTime, seconds, milliseconds;
        String formattedTime;
        int testNumber = 8;

        byte[][] g = FileLoader.getGraphFromFile(testNumber);
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
