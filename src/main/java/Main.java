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

        BFS bfs = new BFS(RandomGraphGenerator.getRandomGraph(n));

        System.out.print("Start node: ");
        int start = sc.nextInt();

        Result result;

        switch (choice) {
            case 1:
                result = bfs.search(start);
                result.printDistance();
                break;
            case 2:
                result = bfs.parallelSearch(start);
                result.printDistance();
                break;
            default:
                result = new Result(new int[1]);
                result.printPath();
        };
    }
}
