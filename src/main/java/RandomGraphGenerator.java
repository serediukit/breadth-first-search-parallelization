import java.util.Random;

public class RandomGraphGenerator {
    public static int[][] getRandomGraph(int n) {
        int[][] graph = new int[n][n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                graph[i][j] = rnd.nextInt(n / 10) > 0 ? 0 : 1;

            graph[i][i] = 0;
        }

        return graph;
    }
}
