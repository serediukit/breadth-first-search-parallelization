import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {
    private final byte[][] graph;
    private final int size;
    private int[] distances;
    public List<Integer> nodes;

    public Graph(byte[][] g) {
        graph = g;
        size = g.length;
        distances = new int[size];
        Arrays.fill(distances, -1);
        this.nodes = IntStream.rangeClosed(0, size - 1).boxed().collect(Collectors.toList());
    }

    public byte[][] getGraph() {
        return graph;
    }

    public int getSize() {
        return size;
    }

    public int[] getDistances() {
        return distances;
    }

    public int getDistanceAt(int pos) {
        return distances[pos];
    }

    public void setDistancesAt(int pos, int value) {
        distances[pos] = value;
    }

    public List<Integer> getAdjacent(int vertix) {
        List<Integer> adjacent = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            if (graph[vertix][i] == 1) {
                adjacent.add(i);
            }
        }
        return adjacent;
    }
}
