import java.util.ArrayList;
public class MathOperations {

    public static Matrix[] LU(Matrix A) {
        Matrix Lower = null;
        Matrix Upper = null;
        double[][] L = null;
        double[][] U = null;

        Matrix calcMatrix = null;
        double[][] calc = null;

        Matrix other2 = null;
        double[][] other1 = null;

        Matrix A2 = null;
        int m = 0;
        int n = 0;

        A2 = A;
        Upper = A;
        U = A.getArrayCopy();
        m = A.getRowDimension();
        n = A.getColumnDimension();

        //Check matrix dimensions
        if (m != n) {
            throw new IllegalArgumentException("Matrix needs to be a square");
        }

        other1 = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    other1[i][j] = 1;
                }
            }
        }
        other2 = new Matrix(other1);
        L = other2.getArrayCopy();
        return performLU(L, U, calc, Lower, Upper, calcMatrix, other2, m, n);
    }

    public static Matrix[] performLU(double[][] L, double[][] U, double[][] calc, Matrix Lower, Matrix Upper,
                                     Matrix calcMatrix, Matrix other2, int m, int n) {
        int row = 1;
        for (int j = 0; j < n-1; j++) {
            for (int i = row; i < m; i++) {
                if (U[i][j] != 0) {
                    double reduce = -1 * (U[i][j]/U[row-1][j]);
                    calc = other2.getArrayCopy();
                    calc[i][j] = reduce;
                    L[i][j] = -1 * reduce;
                    calcMatrix = new Matrix(calc);
                    Upper = calcMatrix.multiply(Upper);
                    U = Upper.getArrayCopy();
                }
            }
            row += row;
        }
        Lower = new Matrix(L);

        //Returns an array of Matrices: L, U, & LU.
        Matrix[] toReturn = new Matrix[3];
        toReturn[0] = Lower;
        toReturn[1] = Upper;
        toReturn[2] = Lower.multiply(Upper);

        return toReturn;
    }

    public static Matrix generatePascalMatrix(int n) {
        double[][] toReturn = new double[n][n];

        for (int i = 0; i < toReturn.length; i++) {
            for (int j = 0; j < toReturn[i].length; j++) {
                if (i == 0 || j == 0 ){
                    toReturn[i][j] = 1;
                }
                else {
                    double numerator = MathOperations.factorial((i)+(j));

                    double mFactorial = MathOperations.factorial(i);
                    double nFactorial = MathOperations.factorial(j);
                    double denominator = mFactorial * nFactorial;

                    toReturn[i][j] = numerator / denominator;
                }

            }
        }
        return new Matrix(toReturn);
    }

    public static Matrix generateBPascalMatrix(int n) {
        double[][] toReturn = new double[n][1];
        int counter = 1;

        for (int i = 0; i < toReturn.length; i++) {
            for(int j = 0; j < toReturn[i].length; j++) {

                toReturn[i][j] = 1.0 / counter;
                counter++;
            }
        }
        return new Matrix(toReturn);
    }

    public static int factorial(int n) {
        int sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    public static Matrix givens(Matrix m) {
        double[] x = new double[2];

        double sin;
        double cos;
        double r;

        double[][] givensArray = new double[m.getRowDimension()][m.getRowDimension()]; //
        Matrix givens;

        if (m.getRowDimension() < m.getColumnDimension()) {
            throw new IndexOutOfBoundsException("ERROR: QR cannot be performed. ");
        }

        int i = -1;
        int j = -1;
        for (int y = m.getColumnDimension() - 1; y >= 0; y--) {
            for (int under = m.getRowDimension() - 1; under > y; under--) {
                if (m.get(under, y) != 0) {
                    i = y;
                    j = under;
                }
            }
        }
        if (i == -1 || j == -1) {
            return null;
        } else {
            x[0] = m.get(i, i);
            x[1] = m.get(j, i);
            r = Math.sqrt(x[0]*x[0] + x[1]*x[1]);
            cos = x[0]/r;
            sin = -x[1]/r;

            for (int row  = 0; row < m.getRowDimension(); row++) {
                for (int col = 0; col < m.getRowDimension(); col++) {
                    givensArray[row][col] = 0;
                    if (row == col) {
                        givensArray[row][col] = 1;
                    }
                }
            }
            givensArray[i][i] = cos;
            givensArray[j][j] = cos;
            givensArray[i][j] = -sin;
            givensArray[j][i] = sin;

            givens = new Matrix(givensArray);
            return givens;
        }
    }

    public static Matrix QTgivens(Matrix a) {
        double[][] identityArray = new double[a.getRowDimension()][a.getRowDimension()];
        Matrix[] qs = givensOrthogonals(a);
        for (int i = 0; i < a.getRowDimension() ; i++) {
            for (int t = 0; t < a.getRowDimension(); t++) {
                identityArray[i][t] = 0;
                if (i == t) {
                    identityArray[i][t] = 1;
                }
            }
        }

        Matrix qT = new Matrix(identityArray);
        Matrix temp = qT.times(1);
        for (int index = 0; index < qs.length; index++) {
            qT = qs[index].multiply(temp);
            temp = qT.times(1);
        }
        return qT;
    }

    public static Matrix[] givensOrthogonals(Matrix matrix) {
        Matrix temp = matrix.times(1);
        Matrix temp2;
        ArrayList<Matrix> qs = new ArrayList();
        int i;
        for (i = 0; i > -1; i++) {
            qs.add(givens(temp));
            if (qs.get(i) == null) {
                break;
            }
            temp2 = qs.get(i).multiply(temp);
            temp = temp2.times(1);
        }
        qs.remove(i);
        Matrix[] result = new Matrix[qs.size()];
        result = qs.toArray(result);


        return result;
    }

    public static Matrix Qgivens(Matrix m) {
        return QTgivens(m).transpose();
    }

    public static Matrix Rgivens(Matrix a) {
        return QTgivens(a).multiply(a);
    }

    public static Matrix confirmQRgivens(Matrix matrix) {
        Matrix q = Qgivens(matrix);
        Matrix r = Rgivens(matrix);
        Matrix toReturn = q.multiply(r);
        return toReturn;
    }


    public static Matrix householder(Matrix matrix, int j) {
        int i, t;
        double xSum;
        double vSum;
        double[] x;
        double[] v;
        double[] u;
        double[][] identityArray;
        double[][] uVecArray;
        double[][] uVecTArray;
        double xNorm;
        double vNorm;

        Matrix uVec, uVecT, identity;

        if (matrix.getColumnDimension() < matrix.getRowDimension()) {
            throw new IndexOutOfBoundsException("ERROR: QR cannot be performed. ");
        }

        identityArray = new double[matrix.getRowDimension()][matrix.getRowDimension()];
        for (i = 0; i < matrix.getRowDimension() ; i++) {
            for (t = 0; t < matrix.getRowDimension(); t++) {
                identityArray[i][t] = 0;
                if (i == t) {
                    identityArray[i][t] = 1;
                }
            }
        }

        identity = new Matrix(identityArray);
        uVecArray = new double[matrix.getRowDimension()][1];
        uVecTArray = new double[1][matrix.getRowDimension()];
        x = new double[matrix.getRowDimension()];
        v = new double[matrix.getRowDimension()];
        u = new double[matrix.getRowDimension()];
        xSum = 0;
        vSum = 0;

        for (i = j; i < matrix.getRowDimension(); i++) {
            x[i] = matrix.get(i, j);
            xSum += Math.pow((x[i]), 2);
            v[i] = x[i];
        }

        xNorm = Math.sqrt(xSum);

        v[j] = x[j] + xNorm;
        vSum = 0;

        for (i = j; i < matrix.getRowDimension(); i++) {
            vSum += Math.pow(v[i], 2);
        }
        vNorm = Math.sqrt(vSum);
        for (i = j; i < matrix.getRowDimension(); i++) {
            u[i] = v[i]/vNorm;
        }
        for (i = j; i < matrix.getRowDimension(); i++) {
            uVecArray[i][0] = u[i];
            uVecTArray[0][i] = u[i];
        }
        uVec = new Matrix(uVecArray);
        uVecT = new Matrix(uVecTArray);
        return uVec.multiply(uVecT).times(-2).plus(identity);
    }

    public static Matrix applyCramersRule(Matrix A, Matrix b, int columnToAdjust) {
        double[][] adjustedMatrix = A.getArrayCopy();
        for (int row = 0; row < A.getRowDimension(); row++) {
            for (int col = 0; col < A.getColumnDimension(); col++) {
                if (col == columnToAdjust) {
                    //System.out.println("row, col " + row + " " + col);
                    adjustedMatrix[row][col] = b.get(row, 0);
                }
            }
        }
        Matrix finalAdjustedMatrix = new Matrix(adjustedMatrix);
        return finalAdjustedMatrix;
    }

    public static Matrix QThouseholder(Matrix a) {
        Matrix[] qs = householderOrthogonals(a);
        double[][] identityArray = new double[a.getRowDimension()][a.getRowDimension()];
        for (int i = 0; i < a.getRowDimension() ; i++) {
            for (int t = 0; t < a.getRowDimension(); t++) {
                identityArray[i][t] = 0;
                if (i == t) {
                    identityArray[i][t] = 1;
                }
            }
        }

        Matrix qT = new Matrix(identityArray);
        Matrix temp = qT.times(1);
        for (int index = 0; index < qs.length; index++) {
            qT = qs[index].multiply(temp);
            temp = qT.times(1);
        }
        return qT;
    }

    public static Matrix[] householderOrthogonals(Matrix matrix) {
        Matrix[] q = new Matrix[matrix.getColumnDimension()];
        q[0] = householder(matrix, 0);
        Matrix temp = q[0].multiply(matrix);
        Matrix temp2 = q[0].multiply(matrix);
        for (int i = 1; i < q.length; i++) {
            q[i] = householder(temp, i);
            temp2 = q[i].multiply(temp);
            temp = temp2.times(1);
        }
        return q;
    }

    public static Matrix Rhouseholder(Matrix a) {
        Matrix qT = QThouseholder(a);
        Matrix r = qT.multiply(a);
        return r;
    }

    public static Matrix Qhouseholder(Matrix matrix) {
        return QThouseholder(matrix).transpose();
    }

    public static double QRError(Matrix QR, Matrix A) {
        Matrix QRMinusA = QR.minus(A);
        return QRMinusA.maxNorm();
    }

    public static double PXError(Matrix PX, Matrix B) {
        double[][] Barray = B.getArrayCopy();
        double[][] correctedDimensionForB = PX.getArrayCopy();
        for(int i = 0; i < correctedDimensionForB.length; i++) {
            for(int j = 0; j < correctedDimensionForB[i].length; j++) {
                correctedDimensionForB[i][j] = Barray[j][i];
            }
        }
        Matrix correctedDimensionForBMatrix = new Matrix(correctedDimensionForB);

        Matrix PXMinusB = correctedDimensionForBMatrix.minus(PX);
        return PXMinusB.maxNorm();
    }

    public static double LUError(Matrix L, Matrix U, Matrix A) {
        Matrix LU = L.multiply(U);
        Matrix LUMinusA = LU.minus(A);
        return LUMinusA.maxNorm();
    }

    public static Matrix confirmQRhouseholder(Matrix matrix) {
        Matrix q = Qhouseholder(matrix);
        Matrix r = Rhouseholder(matrix);
        Matrix toReturn = q.multiply(r);
        return toReturn;
    }

}
