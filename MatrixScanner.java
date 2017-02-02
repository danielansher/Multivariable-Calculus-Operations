import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class MatrixScanner {

    private final Scanner scanner;

    public MatrixScanner() {
        this.scanner = new Scanner(System.in);
    }

    public double nextDouble() {
        return scanner.nextDouble();
    }

    public int nextInt() {
        return scanner.nextInt();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public double[] readVector() {
        String stringVector = scanner.nextLine();
        double[] vector = parseRow(stringVector);
        return vector;
    }

    public Matrix readMatrix() {
        List<double[]> rowList = new ArrayList<double[]>();
        int width = -1;
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break; // We're done
            }
            double[] row = parseRow(line);
            if (width != row.length && width != -1) {
                throw new InputMismatchException("Row with " + row.length
                        + " elements cannot be applied to matrix with width "
                        + "of " + width + ".");
            }
            width = row.length;
            rowList.add(row);
        }
        return new Matrix(rowList.toArray(new double[rowList.size()][]));
    }

    private double[] parseRow(String row) {
        String[] asArray = row.split(" ");
        double[] result = new double[asArray.length];
        for (int i = 0; i < asArray.length; i++) {
            try {
                result[i] = Double.parseDouble(asArray[i]);
            } catch (NumberFormatException e) {
                throw new InputMismatchException("Could not parse \""
                        + asArray[i] + "\" as a double.");
            }
        }
        return result;
    }
}
