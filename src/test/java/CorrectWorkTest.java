import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CorrectWorkTest {
    final int graphsCount = 10;
    int[] graphsSize = { 10, 100, 500, 1000, 10000, 20000, 50000, 100000, 1000000, 2000000 };

    private int[][] getGraphFromFile(int testNumber) {
        int[][] g = new int[graphsSize[testNumber]][graphsSize[testNumber]];
        File file = new File("src\\main\\resources\\test_graph_" + testNumber + ".txt");

        try {
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < graphsSize[testNumber]; i++) {
                for (int j = 0; j < graphsSize[testNumber]; j++) {
                    g[i][j] = scanner.nextInt();
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
    public void testLinearBFS() {
        for (int i = 0; i < graphsCount; i++) {
            BFS bfs = new BFS(getGraphFromFile(i));
            Result res = bfs.search(0);
            for (int j = 0; j < graphsSize[i]; j++)
                assertEquals(res.getDistances()[j], getAnswerFromFile(i)[j]);
        }
    }

    @Test
    public void testEmptyGraph() {
        BFS bfs = new BFS(new int[1][]);
        Result res = bfs.search(0);
        assertEquals(res.getLength(), -1);
    }
}