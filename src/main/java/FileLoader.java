import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader {
    private final static int[] graphsSize = { 10, 25, 100, 250, 500, 1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000 };

    public static byte[][] getGraphFromFile(int testNumber) {
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
}
