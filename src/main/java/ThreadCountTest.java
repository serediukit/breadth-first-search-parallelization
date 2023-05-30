import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ThreadCountTest {
    public static void main(String[] args) {
        int[] graphsSize = { 10, 25, 100, 250, 500, 1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000 };
        int graphsCount = graphsSize.length;
        long startTime = 0, endTime = 0, wastedTime;
        long[] sumTime = new long[10];
        Arrays.fill(sumTime, 0);

        System.out.printf("+----------------------------------------------------------------------------------------------+%n");
        System.out.print("| THREAD COUNT |");
        for (int i = 5; i <= 50; i += 5) {
            System.out.printf(" %-5d |", i);
        }
        System.out.printf("%n+----------------------------------------------------------------------------------------------+%n");

        for (int i = 0; i < graphsCount; i++) {
            System.out.printf("| %-12d |", graphsSize[i]);
            byte[][] g = FileLoader.getGraphFromFile(i);
            for (int threadsCount = 5; threadsCount <= 50; threadsCount += 5) {
                try {
                    BFS bfs = new BFS(g);
                    startTime = System.currentTimeMillis();
                    bfs.threadSearch(threadsCount);
                    endTime = System.currentTimeMillis();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                wastedTime = endTime - startTime;
                sumTime[threadsCount / 5 - 1] += wastedTime;
                System.out.printf(" %-5d |", wastedTime);
            }
            System.out.printf("%n+----------------------------------------------------------------------------------------------+%n");
            if ((i + 1) % 2 == 0) {
                System.out.print("| TOTAL TIME   |");
                for (long t : sumTime) {
                    System.out.printf(" %-5d |", t);
                }
                System.out.printf("%n+----------------------------------------------------------------------------------------------+%n");
            }
        }

    }
}
