import java.util.InputMismatchException;
public class lu_fact {
    public static void main(String[] args) {
        MatrixScanner input = new MatrixScanner();
        System.out.println("Please enter a matrix to find the LU decomposition: \n ");
        try {
            //Matrix matrix = input.readMatrix();
            System.out.println();

            //For testing purposes
            double[][] theMatrix = {{1,1,1,1},{1,2,3,4},{1,3,6,10},{1,4,10,20}};
            Matrix matrix = new Matrix(theMatrix);

            Matrix[] results = MathOperations.LU(matrix);

            System.out.println("L: \n");
            System.out.println(results[0]);

            System.out.println("U: \n");
            System.out.println(results[1]);

            System.out.println("LU: \n");
            System.out.println(results[2]);


            System.out.print("Error: \n");
            System.out.println(MathOperations.LUError(results[0], results[1], matrix));


        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException me) {
            System.out.println(me.getMessage());
        }
    }
}
