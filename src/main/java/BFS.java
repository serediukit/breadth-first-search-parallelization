package main.java;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BFS {
    int[][] graph;
    int size;

    private int[] distances;
    CountDownLatch latch;

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
        distances[start] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor = 0; neighbor < size; neighbor++) {
                if (graph[current][neighbor] == 1 && distances[neighbor] == -1) {
                    queue.offer(neighbor);
                    distances[neighbor] = distances[current] + 1;
                }
            }
        }

        return new Result(distances);
    }

    public Result parallelSearch(int start) {
        distances[start] = 0;

        ExecutorService executor = Executors.newFixedThreadPool(size);

        for (int neighbor = 0; neighbor < size; neighbor++) {
            if (graph[start][neighbor] == 1 && distances[neighbor] == -1) {
                executor.execute(new SearchThread(neighbor, start));
            }
        }

        executor.shutdown();

        return new Result(distances);
    }

    public class SearchThread implements Runnable {
        private final int vertex;
        private final int parent;

        public SearchThread(int vertex, int parent) {
            this.vertex = vertex;
            this.parent = parent;
        }

        @Override
        public void run() {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(vertex);

            while (!queue.isEmpty()) {
                int current = queue.poll();

                if (distances[current] == -1) {
                    distances[current] = distances[parent] + 1;

                    for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                        if (graph[current][neighbor] == 1 && neighbor != parent) {
                            queue.offer(neighbor);
                        }
                    }
                }
            }
        }
    }
}
