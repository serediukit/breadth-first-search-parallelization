import java.util.List;
import java.util.Set;

public class Result {
    private List<Integer> path;
    private Set<Integer> visited;
    private int length;
    private int[] distances;

    public Result(Set<Integer> s) {
        this.visited = s;
    }

    public Result(List<Integer> path) {
        this.path = path;
        this.length = path.size() - 1;
    }

    public Result(int[] distances) {
        this.distances = distances;
    }

    public Set<Integer> getVisited() {
        return visited;
    }

    public int getLength() {
        return length;
    }

    public int[] getDistances() {
        return distances;
    }

    public void printVisited() {
        if (visited.isEmpty()) {
            System.out.println("No visited");
        } else {
            System.out.print("Visited: ");
            for (Integer s : visited)
                System.out.print((s + 1) + " ");
            System.out.println();
            System.out.println("Visited count: " + visited.size());
        }
    }

    public void printPath() {
        if (length == -1) {
            System.out.println("No path");
            return;
        } else {
            System.out.print("Path: ");
            for (Integer p : path)
                System.out.print((p + 1) + " ");
            System.out.println();
            System.out.println("Path length: " + length);
        }
    }

    public void printDistance() {
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] != -1)
                System.out.println("Distance to " + (i + 1) + " - " + distances[i]);
            else
                System.out.println("Distance to " + (i + 1) + " - impossible");
        }
        System.out.println();
    }
}
