import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class CorrectWorkTest {
    int[] graphsSize = { 10, 25, 100, 250, 500, 1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000 };
    final int graphsCount = graphsSize.length;

    private byte[][] getGraphFromFile(int testNumber) {
        byte[][] g = new byte[graphsSize[testNumber]][graphsSize[testNumber]];
        File file = new File("src\\main\\resources\\test_graph_" + testNumber + ".txt");

        try {
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < graphsSize[testNumber]; i++) {
                for (int j = 0; j < graphsSize[testNumber]; j++) {
                    g[i][j] = scanner.nextByte();
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return g;
    }

    private int[] getAnswerFromFile(int testNumber) {
        int[] a = new int[graphsSize[testNumber]];
        File file = new File("src\\main\\resources\\test_answer_" + testNumber + ".txt");

        try {
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < graphsSize[testNumber]; i++) {
                a[i] = scanner.nextInt();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return a;
    }

    @Test
    public void testEmptyGraph() throws ExecutionException, InterruptedException {
        BFS bfs = new BFS(new byte[1][]);
        Result res = bfs.threadSearch(10);
        assertNull(res.getVisited());
    }

    @Test
    public void testLinearBFS() throws ExecutionException, InterruptedException {
        for (int i = 0; i < graphsCount; i++) {
            BFS bfs = new BFS(getGraphFromFile(i));
            Result res = bfs.threadSearch(1);
            Set<Integer> vis = res.getVisited();
            int[] ans = getAnswerFromFile(i);
            for (int j = 0; j < graphsSize[i]; j++)
                assertTrue(vis.contains(ans[j]));
        }
    }

    @Test
    public void testParallelBFS() throws ExecutionException, InterruptedException {
        for (int i = 0; i < graphsCount; i++) {
            BFS bfs = new BFS(getGraphFromFile(i));
            Result res = bfs.threadSearch(10);
            Set<Integer> vis = res.getVisited();
            int[] ans = getAnswerFromFile(i);
            for (int j = 0; j < graphsSize[i]; j++)
                assertTrue(vis.contains(ans[j]));
        }
    }
}
