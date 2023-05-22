package main.java;

import java.util.*;

public class BFS {
    int[][] graph;
    int size;

    public BFS(int[][] g) {
        this.graph = g;
        this.size = g.length;
    }

    public Result searchToNode(int start, int end) {
        List<Integer> path = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] parent = new int[graph.length];
        boolean[] visited = new boolean[graph.length];

        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == end) {
                path.add(current);
                while (current != start) {
                    path.add(parent[current]);
                    current = parent[current];
                }
                Collections.reverse(path);
                break;
            }

            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[current][neighbor] == 1 && !visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                }
            }
        }

        return new Result(path);
    }

    public int[] search(int start) {
        int[] distances = new int[graph.length];
        Arrays.fill(distances, -1);
        distances[start] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[current][neighbor] == 1 && distances[neighbor] == -1) {
                    queue.offer(neighbor);
                    distances[neighbor] = distances[current] + 1;
                }
            }
        }

        return distances;
    }

    public Result parallelSearch(int start, int end) {
        int[] way = new int[size+1];
        int[][] distance = new int[size][size];

        /* ... */

        return new Result(getCorrectWay(way));
    }
}
