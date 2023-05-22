package main.java;

import java.util.List;

public class Result {
    List<Integer> path;
    int length;
    int[] distance;

    public Result(List<Integer> path) {
        this.path = path;
        this.length = path.size() - 1;
    }

    public Result(int[] distance) {
        this.distance = distance;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getLength() {
        return length;
    }

    public int[] getDistance() {
        return distance;
    }

    public void printPath() {
        System.out.print("Path: ");
        for (Integer p : path)
            System.out.print(p + " ");
        System.out.println();
        System.out.println("Path length: " + length);
    }

    public void printDistance() {
        for (int i = 0; i < distance.length; i++) {
            System.out.println("Distance to " + i + " - " + distance[i]);
        }
        System.out.println();
    }
}
