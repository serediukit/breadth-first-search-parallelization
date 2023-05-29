import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        System.out.print("Graph size: ");
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int[][] graph = RandomGraphGenerator.getRandomGraph(size);

        BFS lbfs = new BFS(graph);
        Result lres = lbfs.search(0);

        BFS bfs = new BFS(graph);
        long startTime = System.currentTimeMillis();
        Result res = bfs.parallel(0, 10);
        long endTime = System.currentTimeMillis();
        res.printDistance();

        long wastedTime = endTime - startTime;
        long seconds = wastedTime / 1000;
        long milliseconds = wastedTime % 1000;
        String formattedTime = String.format("%02ds:%03dms", seconds, milliseconds);

        System.out.println("Parallel wasted time: " + formattedTime);

        System.out.println(Arrays.equals(lres.getDistances(), res.getDistances()));
    }
}
