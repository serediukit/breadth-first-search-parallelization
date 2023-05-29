import java.util.concurrent.ExecutionException;

public class MainCompare {
    public static void main(String[] args) {
        final int THREAD_COUNT = 10;

        long startTime, endTime, wastedTime, seconds, milliseconds;
        String formattedTime;

        System.out.println("Running test cases\n");
        for (int testNumber = 0; testNumber < 14; testNumber++) {
            boolean isEqual = false;
            try {
                byte[][] g = FileLoader.getGraphFromFile(testNumber);
                BFS linearBFS = new BFS(g);
                startTime = System.currentTimeMillis();
                Result linearRes = linearBFS.threadSearch(1);
                endTime = System.currentTimeMillis();
                wastedTime = endTime - startTime;
                seconds = wastedTime / 1000;
                milliseconds = wastedTime % 1000;
                formattedTime = String.format("%02ds:%03dms", seconds, milliseconds);
                System.out.println("Linear search time for " + testNumber + " test: " + formattedTime);

                BFS parallelBFS = new BFS(g);
                startTime = System.currentTimeMillis();
                Result parallelRes = parallelBFS.threadSearch(THREAD_COUNT);
                endTime = System.currentTimeMillis();
                wastedTime = endTime - startTime;
                seconds = wastedTime / 1000;
                milliseconds = wastedTime % 1000;
                formattedTime = String.format("%02ds:%03dms", seconds, milliseconds);
                System.out.println("Parallel search time for " + testNumber + " test: " + formattedTime);

                isEqual = linearRes.getVisited().equals(parallelRes.getVisited());
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
