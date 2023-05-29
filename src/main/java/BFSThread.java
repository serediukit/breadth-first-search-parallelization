public class BFSThread extends Thread{
    private Graph graph;
    private int vertex;

    public BFSThread(Graph g, int v) {
        graph = g;
        vertex = v;
    }

    @Override
    public void run() {
        while(!graph.getQueue().isEmpty()) {
            try {
                int current = graph.pollQueue();

                for (int neighbor = 0; neighbor < graph.getSize(); neighbor++) {
                    if (graph.getGraph()[current][neighbor] == 1 && graph.getDistanceAt(neighbor) == -1) {
                        graph.offerQueue(neighbor);
                        graph.setDistancesAt(neighbor, graph.getDistanceAt(current) + 1);
                    }
                }
            } catch (NullPointerException e) {
                break;
            }
        }
    }
}
