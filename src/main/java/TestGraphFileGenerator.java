import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TestGraphFileGenerator {

    public static void main(String[] args) {
        int[] graphsSize = { 10, 25, 100, 250, 500, 1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000 };

        for (int fileNumber = 0; fileNumber < graphsSize.length; fileNumber++) {
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
            Result res = null;
            try {
                res = bfs.threadSearch(1);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            filePath = "src\\main\\resources\\test_answer_" + fileNumber + ".txt";

            try {
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                assert res != null;
                String str = res.getVisited()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" "));
                bufferedWriter.write(str);

                bufferedWriter.close();

                System.out.println("Matrix saved to " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
