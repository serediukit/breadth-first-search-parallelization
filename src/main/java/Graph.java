import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private final int[][] graph;
    private final int size;
    private int[] distances;
    private Queue<Integer> queue;

    public Graph(int[][] g) {
        graph = g;
        size = g.length;
        queue = new LinkedList<>();
        distances = new int[size];
        Arrays.fill(distances, -1);
    }

    public int[][] getGraph() {
        return graph;
    }

    public int getSize() {
        return size;
    }

    public int[] getDistances() {
        return distances;
    }

    public synchronized void setDistances(int[] dist) {
        distances = dist;
    }

    public int getDistanceAt(int pos) {
        return distances[pos];
    }

    public synchronized void setDistancesAt(int pos, int value) {
        distances[pos] = value;
    }

    public Queue<Integer> getQueue() {
        return queue;
    }

    public synchronized Integer pollQueue() {
        return queue.poll();
    }

    public synchronized void offerQueue(Integer value) {
        queue.offer(value);
    }
}
