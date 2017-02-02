import java.util.ArrayList;
import java.util.Random;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Created by Jacob on 11/19/2015.
 */
public class powerMethodTester {
    public static void main (String args[]) {

        //double[][] a = {{2, -12}, {1, -5}};
        double[][] a = {{20,2,1},{9,0,2},{4,1,3}};
        double[][] startingApprox = {{2.1 * (Math.pow(10, 5))}, {1.9 * (Math.pow(10, 5))}, {1.8 * (Math.pow(10, 5))}, {2.1 * (Math.pow(10, 5))}, {2.0 * (Math.pow(10, 5))}, {1.7 * Math.pow(10,  5)}, {1.2 * (Math.pow(10, 5))}, {0.9 * (Math.pow(10, 5))}, {0.5 * (Math.pow(10, 5))}};
        final double TOLERANCE = 0.00005;
        final int ITERATIONS = 100;



        double[][] m2by2 = {{2, -12}, {1, -5}};
        double[][] approx2by2 = {{1},{1}};
        ArrayList<Double> traceList = new ArrayList<>(1000);
        ArrayList<Double> detList = new ArrayList<>(1000);
        ArrayList<Integer> iterationsList = new ArrayList<>(1000);
        ArrayList<Integer> invIterationsList = new ArrayList<>(1000);

        Matrix startingApproxMatrix = new Matrix(approx2by2);
        Matrix am = new Matrix(a);


        for (int i = 0; i < 1000; i++) {
            Matrix current = create2by2Matrix();
            Matrix invMatrix = current.inverse2by2();
            System.out.print("Matrix: ");
            System.out.println("{{" + current.get(0, 0) + ", " + current.get(0, 1) + "}, {" + current.get(1, 0) + ", " + current.get(1,1) + "}}");
            power_method powMethod = new power_method(current, startingApproxMatrix, TOLERANCE, ITERATIONS);
            iterationsList.add(powMethod.iterationsCount);
            System.out.println("");

            invIterationsList.add(powMethod.iterationsCount);
            System.out.println("");

            double det = 1 / ((current.get(0, 0) * current.get(1, 1)) - (current.get(0, 1) * current.get(1, 0)));
            System.out.println("Determinant: " + det);
            detList.add(det);
            System.out.println("Trace: " + current.trace());
            traceList.add(current.trace());

            System.out.println("\n");

        }
        //printToExcelSheet();

    }

    public static Matrix create2by2Matrix() {
        Random numGenerator = new Random();
        double[][] matrix = {{0, 0}, {0, 0}};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j] = (numGenerator.nextFloat() * 4) - 2;
            }
        }
        Matrix ret = new Matrix(matrix);
        return ret;
    }

}
