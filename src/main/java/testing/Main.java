package testing;

import testing.dto.Graph;
import testing.runnable.NonSilentBFS;
import testing.runnable.SilentBFS;

import java.util.Collections;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Graph size: ");
        int vertices = sc.nextInt();
        Graph graph = Graph.generateRandomGraph(vertices);
        System.out.println("Num threads: ");
        int numThreads = sc.nextInt();
        long startTime = System.currentTimeMillis();
        runBFS(false, numThreads, graph);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total execution time for current run (millis): " + totalTime);
    }

    private static void runBFS(boolean isQuiet, int numThreads, Graph graph) {
        if (graph != null) {
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            Queue<Integer> queue = new LinkedBlockingQueue<>();
            Set<Integer> set = Collections.newSetFromMap(new ConcurrentHashMap<>(graph.size));
            if (isQuiet) {
                for (int i = 0; i < numThreads; i++) {
                    Runnable worker = new SilentBFS(queue, set, i % numThreads + 1, graph);
                    executor.execute(worker);
                }
            } else {
                for (int i = 0; i < numThreads; i++) {
                    Runnable worker = new NonSilentBFS(queue, set, i % numThreads + 1, graph);
                    executor.execute(worker);
                }
            }
            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException е) {
                System.out.println();
            }
        }
    }
}
