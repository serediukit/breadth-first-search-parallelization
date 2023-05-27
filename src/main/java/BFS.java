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

    public Result search(int start) {
        if (graph.length == 0)
            return new Result(new int[]{-1});
        if (graph.length == 1)
            return new Result(new int[]{0});

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

    public Result parallelSearch(int start, int threadCount) throws InterruptedException, ExecutionException {
        if (graph.length == 0)
            return new Result(new int[]{-1});
        if (graph.length == 1)
            return new Result(new int[]{0});

        distances[start] = 0;

        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);

        forkJoinPool.invoke(new BFSRecursiveAction(start));

        forkJoinPool.shutdown();

        return new Result(distances);
    }

    private class BFSRecursiveAction extends RecursiveAction {
        private final int vertex;

        public BFSRecursiveAction(int vertex) {
            this.vertex = vertex;
        }

        @Override
        protected void compute() {
            List<BFSRecursiveAction> actions = new ArrayList<>();

            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[vertex][neighbor] == 1 && (distances[neighbor] == -1 || distances[neighbor] > distances[vertex] + 1)) {
                    distances[neighbor] = distances[vertex] + 1;
                    BFSRecursiveAction action = new BFSRecursiveAction(neighbor);
                    actions.add(action);
                    action.fork();
                }
            }

            for (BFSRecursiveAction action : actions) {
                action.join();
            }
        }
    }

    public Result searchToVertex(int start, int end) {
        if (graph.length == 0)
            return new Result(new int[]{-1});
        if (graph.length == 1)
            return new Result(new int[]{0});

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
}
