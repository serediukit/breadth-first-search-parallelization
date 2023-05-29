import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestGraphFileGenerator {
    private static String getString(int[] array) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length - 1; i++) {
            sb.append(array[i]);
            sb.append(" ");
        }
        sb.append(array[array.length - 1]);

        return sb.toString();
    }

    public static void main(String[] args) {
        int[] graphsSize = { 10, 25, 100, 250, 500, 1000, 5000, 10000, 15000, 20000 };

        for (int fileNumber = 0; fileNumber < 10; fileNumber++) {
            byte[][] matrix = RandomGraphGenerator.getRandomGraph(graphsSize[fileNumber]);

            String filePath = "src\\main\\resources\\test_graph_" + fileNumber + ".txt";

            try {
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (byte[] array : matrix) {
                    for (byte a : array) {
                        bufferedWriter.write(a + " ");
                    }
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();

                System.out.println("Matrix saved to " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BFS bfs = new BFS(matrix);
            Result res = bfs.search(0);
            int[] distances = res.getDistances();

            filePath = "src\\main\\resources\\test_answer_" + fileNumber + ".txt";

            try {
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                String str = getString(distances);
                System.out.println(str);
                bufferedWriter.write(str);

                bufferedWriter.close();

                System.out.println("Matrix saved to " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
