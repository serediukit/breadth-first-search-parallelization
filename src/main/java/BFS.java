package main.java;

import java.util.*;
import java.util.concurrent.*;

public class BFS {
    private final int[][] graph;
    private final int size;

    private int[] distances;

    public BFS(int[][] g) {
        this.graph = g;
        this.size = g.length;
        distances = new int[size];
        Arrays.fill(distances, -1);
    }

    public Result searchToVertex(int start, int end) {
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

    public Result search(int start) {
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

        return new Result(distances);
    }

    public Result parallelSearch(int start, int numThreads) {
        Map<Integer, Integer> distance = new ConcurrentHashMap<>();
        distance.put(start, 0);

        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.offer(start);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor = 0; neighbor < size; neighbor++) {
                if (graph[current][neighbor] == 1 && distance.putIfAbsent(neighbor, distance.get(current) + 1) == null) {
                    final int finalNeighbor = neighbor;
                    executor.execute(() -> queue.offer(finalNeighbor));
                }
            }
        }
        
        executor.shutdown();

        for (int i = 0; i < size; i++) {
            distances[i] = distance.getOrDefault(i, -1);
        }

        return new Result(distances);
    }
}
