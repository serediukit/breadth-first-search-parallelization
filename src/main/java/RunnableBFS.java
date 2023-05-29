import java.util.*;

public class RunnableBFS implements Runnable {
    private final Graph graph;
    private final Queue<Integer> queue;
    private final Set<Integer> visited;
    private final int id;

    public RunnableBFS(Graph graph, Queue<Integer> queue, Set<Integer> visited, int id) {
        this.graph = graph;
        this.queue = queue;
        this.visited = visited;
        this.id = id;
    }

    public void run() {
        Integer node = id;
        visitNode(node, 1);
        while (!queue.isEmpty()) {
            Integer current = queue.poll();
            if (current != null) {
                List<Integer> adjacent = graph.getAdjacent(current);
                for (Integer adj : adjacent) {
                    visitNode(adj, graph.getDistanceAt(current));
                }
            }
        }
    }

    private void visitNode(Integer node, int dist) {
        synchronized (graph.nodes.get(node)) {
            if (!visited.contains(node)) {
                visited.add(node);
                queue.add(node);
                graph.setDistancesAt(node, dist + 1);
            }
        }
    }
}
