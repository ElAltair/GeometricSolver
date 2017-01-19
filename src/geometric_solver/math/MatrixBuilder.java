package geometric_solver.math;

public class MatrixBuilder {

    private Lagrange lagrange;
    private Source source;
    private int dimension;

    MatrixBuilder(int dimension, Lagrange lagrange, Source source) {
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

    public int getSize() {
        return dimension;
    }
}
