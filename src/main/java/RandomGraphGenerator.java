import java.util.Random;

public class RandomGraphGenerator {
    public static byte[][] getRandomGraph(int n) {
        byte[][] graph = new byte[n][n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                graph[i][j] = rnd.nextInt(n / 10 + 1) > 0 ? (byte) 0 : (byte) 1;

            graph[i][i] = 0;
        }

        return graph;
    }
}
