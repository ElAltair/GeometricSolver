package geometric_solver.math;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class MatrixBuilder {

    private Lagrange lagrange;
    private Source source;
    private int dimension;

    public MatrixBuilder(int dimension, Lagrange lagrange, Source source) {
        this.dimension = dimension;
        this.lagrange = lagrange;
        this.source = source;
    }

    public double[][] createMatrixA() {

        double[][] matrixA = new double[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                matrixA[i][j] = lagrange.doubleDiff(source.getVariable(i), source.getVariable(j), source);
            }
        }
        return matrixA;
    }

    public double[] createVectorB() {

        double[] vectorB = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            vectorB[i] = lagrange.diff(source.getVariable(i), source);
        }
        return vectorB;
    }

    public void generateAndPrint() {
        //Generate full Matrix. Here is just copy of previous methods (I-m too lazy)
        double[][] matrixA = new double[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                matrixA[i][j] = lagrange.doubleDiff(source.getVariable(i), source.getVariable(j), source);
            }
        }
        double[] vectorB = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            vectorB[i] = lagrange.diff(source.getVariable(i), source);
        }

        System.out.println("MatrixA");
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                System.out.print(matrixA[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("VectorB");
        for (int i = 0; i < dimension; ++i) {
                System.out.println(vectorB[i] + " ");
        }
    }

    public int getSize() {
        return dimension;
    }
}
