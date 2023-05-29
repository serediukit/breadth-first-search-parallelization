import java.util.*;
import java.util.concurrent.*;

public class BFS {
    private Graph graph;

    public BFS(int[][] g) {
        graph = new Graph(g);
    }

    public Result search(int start) {
        if (graph.getSize() == 0)
            return new Result(new int[]{-1});
        if (graph.getSize() == 1)
            return new Result(new int[]{0});

        int[] distances = graph.getDistances();
        distances[start] = 0;

        graph.offerQueue(start);

        while (!graph.getQueue().isEmpty()) {
            int current = graph.pollQueue();

            for (int neighbor = 0; neighbor < graph.getSize(); neighbor++) {
                if (graph.getGraph()[current][neighbor] == 1 && distances[neighbor] == -1) {
                    graph.offerQueue(neighbor);
                    distances[neighbor] = distances[current] + 1;
                }
            }
        }

        return new Result(distances);
    }

    public Result parallelSearch(int start, int threadCount) throws InterruptedException, ExecutionException {
        if (graph.getSize() == 0)
            return new Result(new int[]{-1});
        if (graph.getSize() == 1)
            return new Result(new int[]{0});

        int[] distances = graph.getDistances();
        distances[start] = 0;

        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);

        boolean changesMade = true;
        while (changesMade) {
            changesMade = false;

            List<BFSRecursiveAction> actions = new ArrayList<>();

            for (int neighbor = 0; neighbor < graph.getSize(); neighbor++) {
                if (distances[neighbor] != -1)
                    continue;

                if (graph.getGraph()[start][neighbor] == 1) {
                    distances[neighbor] = distances[start] + 1;
                    BFSRecursiveAction action = new BFSRecursiveAction(neighbor);
                    actions.add(action);
                    action.fork();
                    changesMade = true;
                }
            }

            for (BFSRecursiveAction action : actions) {
                action.join();
            }
        }

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

            for (int neighbor = 0; neighbor < graph.getSize(); neighbor++) {
                if (graph.getGraph()[vertex][neighbor] == 1 && (graph.getDistanceAt(neighbor) == -1 || graph.getDistanceAt(neighbor) > graph.getDistanceAt(vertex) + 1)) {
                    graph.setDistancesAt(neighbor, graph.getDistanceAt(vertex) + 1);
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
        if (graph.getSize() == 0)
            return new Result(new int[]{-1});
        if (graph.getSize() == 1)
            return new Result(new int[]{0});

        List<Integer> path = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] parent = new int[graph.getSize()];
        boolean[] visited = new boolean[graph.getSize()];

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

            for (int neighbor = 0; neighbor < graph.getSize(); neighbor++) {
                if (graph.getGraph()[current][neighbor] == 1 && !visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                }
            }
        }

        return new Result(path);
    }
}
