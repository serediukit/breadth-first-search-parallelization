import java.util.concurrent.ExecutionException;

public class AlgorithmTime {
    public static void main(String[] args) {
        int threadCount = 10;
        int[] graphsSize = { 10, 25, 100, 250, 500, 1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000 };
        int graphsCount = graphsSize.length;
        long startTime = 0, endTime = 0, wastedTime;

        for (int i = 0; i < graphsCount; i++) {
            byte[][] g = FileLoader.getGraphFromFile(i);
            BFS bfs = new BFS(g);
            try {
                startTime = System.currentTimeMillis();
                bfs.threadSearch(threadCount);
                endTime = System.currentTimeMillis();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            wastedTime = endTime - startTime;
            System.out.println("Graph size " + graphsSize[i] + " time: " + wastedTime);
        }
    }
}
