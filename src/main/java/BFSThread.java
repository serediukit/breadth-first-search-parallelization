public class BFSThread extends Thread{
    private final Graph graph;

    public BFSThread(Graph g) {
        graph = g;
        graph.countThreat++;
    }

    @Override
    public void run() {
        while (!graph.getQueue().isEmpty()) {
            int current;
            synchronized (graph.getQueue()) {
                current = graph.pollQueue();
            }
            graph.activeThreat++;

            for (int neighbor = 0; neighbor < graph.getSize(); neighbor++) {
                synchronized (graph) {
                    if (graph.getGraph()[current][neighbor] == 1 && graph.getDistanceAt(neighbor) == -1) {
                        graph.offerQueue(neighbor);
                        graph.setDistancesAt(neighbor, graph.getDistanceAt(current) + 1);
                    }
                }
            }

            synchronized (this) {
                while (graph.countThreat != graph.activeThreat) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                graph.activeThreat = 0;
                notifyAll();
            }
        }
        graph.countThreat--;
    }
}
