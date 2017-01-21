package geometric_solver.math;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.LinkedHashMap;
import java.util.Map;

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
        LinkedHashMap<Variable, Double> sourceList = source.getVariables();
        int i = 0, j = 0;
        for (Variable iV : sourceList.keySet()) {
            for (Variable jV : sourceList.keySet()) {
                matrixA[i][j] = lagrange.doubleDiff(iV, jV, source);
                ++j;
            }
            ++i;
            j = 0;
        }
        return matrixA;
    }

    public double[] createVectorB() {

        double[] vectorB = new double[dimension];
        LinkedHashMap<Variable, Double> sourceList = source.getVariables();
        int i = 0;
        for (Variable iV : sourceList.keySet()) {
            vectorB[i] = -lagrange.diff(iV, source);
            ++i;
        }
        return vectorB;
    }

    public void printMatrixes() {
        //Generate full Matrix. Here is just copy of previous methods (I-m too lazy)
        double[][] matrixA = createMatrixA();
        System.out.println("MatrixA");
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                System.out.print(matrixA[i][j] + " ");
            }
            System.out.println();
        }
        double[] vectorB = createVectorB();
        System.out.println("VectorB");
        for (int i = 0; i < dimension; ++i) {
                System.out.println(vectorB[i] + " ");
        }
    }

    public int getSize() {
        return dimension;
    }
}
