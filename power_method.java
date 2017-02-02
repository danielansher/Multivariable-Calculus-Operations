public class power_method {
    private Matrix approx, initial;
    private double TOLERANCE;
    public int iterationsCount;

    /**
     * Performs the PowerMethod on a given matrix A until a given
     * tolerance is reached, or until the maximum iterations
     * @param A matrix to perform power method on
     * @param approx the initial approximation for the power method
     * @param tol minimum tolerance of error for the method
     * @param maxIterations maximum amount of iterations possible
     */
    public power_method(Matrix A, Matrix approx, double tol, int maxIterations) {
        TOLERANCE = tol;
        initial = A;
        this.approx = approx;
        printApprox();
        printEigenvalue();
        System.out.println("Iterations: " + powerMethod(tol, maxIterations));
    }



    /**
     * Performs one iteration of power method
     * @return new approximation
     */
    private Matrix powerMethodIteration() {
        Matrix tempApprox = new Matrix(initial.getRowDimension(), 1);
        double rowSum = 0;
        for (int row = 0; row < initial.getRowDimension(); row++) {
            for (int col = 0; col < initial.getColumnDimension(); col++) {
                rowSum += initial.get(row, col) * approx.get(col, 0);
            }
            tempApprox.set(row, 0, rowSum);
            rowSum = 0;
        }
        return tempApprox;
    }


    /**
     * Runs the power method with enough iterations to get a tol < TOLERANCE
     * @return iterations of powerMethod
     */
    private int powerMethod(double tolerance, int maxIterations) {
        //Matrix tempApprox = powerMethodIteration();
        double tol = 100;
        int iterations = 0;
        double eigenvalue = 0;
        while (tol > (tolerance) && iterations < maxIterations) {


            approx = powerMethodIteration();
            double tempEigen = getEigenvalue();
            tol = Math.abs(tempEigen - eigenvalue);
            eigenvalue = tempEigen;
            iterations++;
            //if (iterations >= maxIterations) {
            //    throw new Error("Did not converge within required amount of iterations.");
            //}
        }
        //Normalizing
        double normalizer = approx.get(approx.getRowDimension() - 1, 0);
        for (int i = 0; i < approx.getRowDimension(); i++) {
            approx.set(i, 0, approx.get(i, 0) / normalizer);
        }
        iterationsCount = iterations;
        return iterations;
    }



    /**
     * Calculates and prints an Eigenvalue approximation as an int
     **/
    private void printEigenvalue() {
        Matrix tempApprox = new Matrix(initial.getRowDimension(), 1);
        double rowSum = 0;
        for (int row = 0; row < initial.getRowDimension(); row++) {
            for (int col = 0; col < initial.getColumnDimension(); col++) {
                rowSum += initial.get(row, col) * approx.get(col, 0);
            }
            tempApprox.set(row, 0, rowSum);
            rowSum = 0;
        }
        //System.out.println("{" + tempApprox.get(0, 0) + "}, {" + tempApprox.get(1, 0) + "}");
        double eigenvalue = 0;
        for (int i = 0; i < tempApprox.getRowDimension(); i++) {
            eigenvalue += tempApprox.get(i, 0) * approx.get(i, 0);
        }

        eigenvalue = eigenvalue / (Matrix.dotProduct(approx.getColumnVector(0,0), approx.getColumnVector(0,0)));
        System.out.println("Eigenvalue: " + (eigenvalue));
    }

    /**
     * Calculates and returns an approximate eigenvalue as a double
     * @return
     **/
    private double getEigenvalue() {
        //System.out.println("Approx.: {" + approx.get(0, 0) + "}, {" + approx.get(1, 0) + "}");
        //System.out.println("Initial: {" + initial.get(0,0) + ", " + initial.get(0,1) + "}, {" + initial.get(1,0) + ", " + initial.get(1,1) + "}}");
        Matrix tempApprox = new Matrix(initial.getRowDimension(), 1);
        double rowSum = 0;
        for (int row = 0; row < initial.getRowDimension(); row++) {
            for (int col = 0; col < initial.getColumnDimension(); col++) {
                rowSum += initial.get(row, col) * approx.get(col, 0);
                //System.out.println(initial.get(row, col) + " * " + approx.get(col, 0) + " = " + initial.get(row, col) * approx.get(col, 0));
            }
            //System.out.println("rowSum: " + rowSum);
            tempApprox.set(row, 0, rowSum);
            rowSum = 0;
        }
        //System.out.println("{" + tempApprox.get(0, 0) + "}, {" + tempApprox.get(1, 0) + "}");
        double eigenvalue = 0;
        for (int i = 0; i < tempApprox.getRowDimension(); i++) {
            eigenvalue += tempApprox.get(i, 0) * approx.get(i, 0);
        }

        eigenvalue = eigenvalue / (Matrix.dotProduct(approx.getColumnVector(0,0), approx.getColumnVector(0,0)));
        return eigenvalue;
    }

    /**
     * Returns the value of approx
     * @return approx, the n by 1 matrix used as an approximation
     */
    public Matrix getApprox() {
        return approx;
    }

    /**
     * Prints the current value of approx
     */
    public void printApprox() {
        System.out.print("Eigenvector Approximation: {");
        for (int i = 0; i < approx.getRowDimension(); i++) {
            System.out.print("{" + (approx.get(i, 0)) + "}");
            if (i != approx.getRowDimension() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }

    public double matrixTotal(Matrix approx) {
        double total = 0;
        for (int row = 0; row < approx.getRowDimension(); row++) {
            total += approx.get(row, 0);
        }
        return total;
    }
}

