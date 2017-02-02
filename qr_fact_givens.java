import java.util.InputMismatchException;
public class qr_fact_givens {
    public static void main(String[] args) {
        MatrixScanner input = new MatrixScanner();
        System.out.println("Please enter a matrix to find the QR using givens rotation: \n ");
        try {
            Matrix matrix = input.readMatrix();
            System.out.println();

            Matrix QR = MathOperations.confirmQRgivens(matrix);
            System.out.println("QR: \n");
            System.out.println(QR);

            Matrix Q = MathOperations.Qgivens(matrix);
            System.out.println("Q: \n");
            System.out.println(Q);

            Matrix R = MathOperations.Rgivens(matrix);
            System.out.println("R: \n");
            System.out.println(R);

            System.out.print("Error: ");
            System.out.println(MathOperations.QRError(QR, matrix));

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException me) {
            System.out.println(me.getMessage());
        }
    }
}
