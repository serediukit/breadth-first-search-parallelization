package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("MENU");
        System.out.println("----");
        System.out.println("1. Search");
        System.out.println("2. Parallel search");
        System.out.println("----");

        System.out.print("Your choice: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        System.out.print("Graph size: ");
        int n = sc.nextInt();

        System.out.print("Max edge weight: ");
        int weight = sc.nextInt();

        BFS bfs = new BFS(RandomGraphGenerator.getRandomGraph(n, weight));

        System.out.print("Start node: ");
        int start = sc.nextInt();

        System.out.print("End node: ");
        int end = sc.nextInt();

        Result result = switch (choice) {
            case 1 -> bfs.search(start, end);
            case 2 -> bfs.parallelSearch(start, end);
            default -> new Result(new int[1], 0);
        };

        result.print();
    }
}
