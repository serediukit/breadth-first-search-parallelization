import java.util.*;
import java.util.concurrent.*;

public class BFS {
    private final Graph graph;

    public BFS(byte[][] g) {
        graph = new Graph(g);
    }

    public Result serialSearch(int start) {
        if (graph.getSize() == 0)
            return new Result(new int[]{-1});
        if (graph.getSize() == 1)
            return new Result(new int[]{0});

        graph.setDistancesAt(start, 0);
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            List<Integer> neighborList = graph.getAdjacent(current);
            for (Integer neighbor : neighborList) {
                if (graph.getGraph()[current][neighbor] == 1 && graph.getDistanceAt(neighbor) == -1) {
                    queue.offer(neighbor);
                    graph.setDistancesAt(neighbor, graph.getDistanceAt(current) + 1);
                }
            }
        }

        return new Result(graph.getDistances());
    }

    public Result threadSearch(int threadCount) throws InterruptedException, ExecutionException {
        if (graph.getSize() == 0)
            return new Result(new int[]{-1});
        if (graph.getSize() == 1)
            return new Result(new int[]{0});
        if (threadCount > graph.getSize())
            threadCount = graph.getSize();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        Queue<Integer> queue = new LinkedBlockingQueue<>();
        Set<Integer> visitedSet = Collections.newSetFromMap(new ConcurrentHashMap<>(graph.getSize()));

        for (int i = 0; i < threadCount; i++) {
            Runnable worker = new RunnableBFS(graph, queue, visitedSet, i);
            executor.execute(worker);
        }

        executor.shutdown();

        try {
            //noinspection ResultOfMethodCallIgnored
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Result(visitedSet);
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
