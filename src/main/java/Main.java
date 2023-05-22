package main.java;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("MENU");
        System.out.println("----");
        System.out.println("1. Search");
        System.out.println("2. Parallel search");
        System.out.println("3. Search path to Vertex");
        System.out.println("----");

        System.out.print("Your choice: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        System.out.print("Graph size: ");
        int n = sc.nextInt();

        BFS bfs = new BFS(RandomGraphGenerator.getRandomGraph(n));

        System.out.print("Start node: ");
        int start = sc.nextInt();

        Result result;

        switch (choice) {
            case 1 -> {
                result = bfs.search(--start);
                result.printDistance();
            }
            case 2 -> {
                try {
                    result = bfs.parallelSearch(--start, 5);
                    result.printDistance();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                System.out.print("End node: ");
                int end = sc.nextInt();
                result = bfs.searchToVertex(--start, --end);
                result.printPath();
            }
            default -> {
                System.out.println("Incorrect input");
            }
        }
    }
}
