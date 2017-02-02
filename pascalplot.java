import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.awt.Color;
import java.util.*;
/*
NOTE: Sometimes the code will not generate the graph because it gets stuck when calculating the error value.
      Unfortunately I did not have enough time to fix this problem. However, it can still work.
      To obtain a graph, you just need to re-run the code by running 'java pascalplot' in the cmd line.
      After a few tries, it should work.
 */
public class pascalplot extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Errors Obtained as a Function of n");
        final NumberAxis xAxis = new NumberAxis(0, 8, 8);
        final NumberAxis yAxis = new NumberAxis(0, .0000000000001, .0000000000001);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Matrix Size");
        yAxis.setLabel("Error Approximation");
        sc.setTitle("Errors Obtained as a Function of n");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("LU Errors");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("QR Givens Errors");

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("QR Householder Errors");

        //Generate random 1x1-4x4 matrix
        for (int iter = 1; iter <= 5; iter++) {
            int n = 2 + (int)(Math.random() * ((4 - 2) + 1));
            System.out.println("Generating " + n + "x" + n + " matrix.");
            double[][] matrix = new double[n][n];
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[i].length; j++) {
                    double randVal = 1 + (int)(Math.random() * ((30 - 1) + 1));
                    System.out.println("filling matrix in spot (" + i + ", " + j + ") with random value=" + randVal);
                    matrix[i][j] = randVal;
                }
            }
            Matrix theMatrix = new Matrix(matrix);
            System.out.println(theMatrix);
            System.out.println();
            Matrix QR1 = MathOperations.confirmQRgivens(theMatrix);
            double QR1Error = MathOperations.QRError(QR1, theMatrix);
            System.out.println("GIVENS ERROR FOUND: " + QR1Error);
            series2.getData().add(new XYChart.Data(n, QR1Error));


            Matrix QR2 = MathOperations.confirmQRhouseholder(theMatrix);
            double QR2Error = MathOperations.QRError(QR2, theMatrix);
            System.out.println("HOUSEHOLDER ERROR FOUND: " + QR2Error);
            series3.getData().add(new XYChart.Data(n, QR2Error));

            Matrix[] QR3 = MathOperations.LU(theMatrix);
            double QR3Error = MathOperations.LUError(QR3[0], QR3[1], theMatrix);
            System.out.println("LU ERROR FOUND: " + QR3Error);
            series1.getData().add(new XYChart.Data(n, QR3Error));
        }


        sc.getData().addAll(series1, series2, series3);
        Scene scene  = new Scene(sc, 500, 400);
        scene.getStylesheets().add("GraphEffects.css");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
