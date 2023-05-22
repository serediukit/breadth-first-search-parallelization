//package main.java;
//
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.concurrent.CountDownLatch;
//
//public class SearchThread implements Runnable {
//    private final int[][] graph;
//    private final int[] distances;
//    private final int vertex;
//    private final int parent;
//
//    private final CountDownLatch latch;
//
//    public SearchThread(int[][] graph, int[] distances, int vertex, int parent, CountDownLatch latch) {
//        this.graph = graph;
//        this.distances = distances;
//        this.vertex = vertex;
//        this.parent = parent;
//        this.latch = latch;
//    }
//
//    @Override
//    public void run() {
//        Queue<Integer> queue = new LinkedList<>();
//        queue.offer(vertex);
//
//        while (!queue.isEmpty()) {
//            int current = queue.poll();
//
//            if (distances[current] == -1) {
//                distances[current] = distances[parent] + 1;
//
//                for (int neighbor = 0; neighbor < graph.length; neighbor++) {
//                    if (graph[current][neighbor] == 1 && neighbor != current && neighbor != parent) {
//                        queue.offer(neighbor);
//                    }
//                }
//            }
//        }
//
//        latch.countDown();
//    }
//}
