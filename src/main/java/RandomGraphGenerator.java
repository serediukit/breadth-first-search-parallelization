package main.java;

public class RandomGraphGenerator {
    public static int[][] getRandomGraph(int n, int maxWeight) {
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                graph[i][j] = (int) (Math.random() * (maxWeight + 1));

        return graph;
    }
}
