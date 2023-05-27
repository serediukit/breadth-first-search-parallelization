import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("MENU");
        System.out.println("----");
        System.out.println("1. Linear search");
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
        long startTime = 0, endTime = 0;

        switch (choice) {
            case 1 -> {
                startTime = System.currentTimeMillis();
                result = bfs.search(--start);
                endTime = System.currentTimeMillis();
                result.printDistance();
            }
            case 2 -> {
                try {
                    startTime = System.currentTimeMillis();
                    result = bfs.parallelSearch(--start, 10);
                    endTime = System.currentTimeMillis();
                    result.printDistance();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                System.out.print("End node: ");
                int end = sc.nextInt();
                startTime = System.currentTimeMillis();
                result = bfs.searchToVertex(--start, --end);
                endTime = System.currentTimeMillis();
                result.printPath();
            }
            default -> {
                System.out.println("Incorrect input");
            }
        }

        long wastedTime = endTime - startTime;
        long seconds = wastedTime / 1000;
        long milliseconds = wastedTime % 1000;
        String formattedTime = String.format("%02ds:%03dms", seconds, milliseconds);

        System.out.println("Wasted time: " + formattedTime);
    }
}
