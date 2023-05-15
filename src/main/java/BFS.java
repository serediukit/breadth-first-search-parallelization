package main.java;

public class BFS {
    int[][] graph;
    int size;

    public BFS(int[][] g) {
        this.graph = g;
        this.size = g.length;
    }

    public Result search(int start, int end) {
        int[] way = new int[size+1];
        int[][] distance = new int[size][size];
        Result result = new Result(getCorrectWay(way), distance[start][end]);
        return result;
    }

    public Result parallelSearch(int start, int end) {
        int[] way = new int[size+1];
        int[][] distance = new int[size][size];
        Result result = new Result(getCorrectWay(way), distance[start][end]);
        return result;
    }

    private int[] getCorrectWay(int[] way) {
        for(int i = 0; i < way.length; i++)
            if (way[i] == 0) {
                int[] res = new int[i];
                System.arraycopy(way, 0, res, 0, i);
                return res;
            }
        return way;
    }
}
