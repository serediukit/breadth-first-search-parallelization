import java.util.List;

public class Result {
    private List<Integer> path;
    private int length;
    private int[] distances;

    public Result(List<Integer> path) {
        this.path = path;
        this.length = path.size() - 1;
    }

    public Result(int[] distances) {
        this.distances = distances;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getLength() {
        return length;
    }

    public int[] getDistances() {
        return distances;
    }

    public void printPath() {
        if (length == -1) {
            System.out.println("No path");
            return;
        }
        System.out.print("Path: ");
        for (Integer p : path)
            System.out.print((p + 1) + " ");
        System.out.println();
        System.out.println("Path length: " + length);
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
