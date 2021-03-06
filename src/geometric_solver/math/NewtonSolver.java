package geometric_solver.math;

import geometric_solver.gaus.Gaus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewtonSolver {
    private final double epsilon = 1e-2;
    private double[][] HesseMatrix;
    private double[] resultVector;
    private double[] vectorX;
    private Gaus gausSolver;
    private MatrixBuilder builder;
    private int dimension;
    private Source solverSource;
    private Lagrange lagrange;

    public NewtonSolver(Lagrange lagrange, Source solverSource) {
        this.solverSource = solverSource;
        this.lagrange = lagrange;
        updateSolver();
    }

    private double getMaxX(double[] vector) {
        return Arrays.stream(vector).max().getAsDouble();
    }

    private void updateSolver() {
        dimension = solverSource.getSize();
        HesseMatrix = new double[dimension][dimension];
        resultVector = new double[dimension];
        vectorX = new double[dimension];
        builder = new MatrixBuilder(dimension, lagrange, solverSource);
    }

    public void solve() {

        updateSolver();
        int count = 0;
        //int iMax = 10;
        do {
            HesseMatrix = builder.createMatrixA();

            solverSource.print();
            System.out.println("Matrix A: ");
            for(int i =0 ; i < dimension; ++i) {
                for (int j = 0; j < dimension; ++j) {
                    System.out.print(HesseMatrix[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("Vector R: ");
            for(int i =0 ; i < dimension; ++i) {
                    System.out.println(resultVector[i]);
            }
            System.out.println();

            resultVector = builder.createVectorB();
            vectorX = Gaus.solve(HesseMatrix, resultVector);

            for(int i =0 ; i < dimension; ++i) {
                System.out.println(vectorX[i]);
            }
            System.out.println("-------------------------");
            /*
            System.out.println("After GAUS");
            Arrays.stream(vectorX).forEach((elem) -> System.out.println(elem));
            */
            solverSource.update(vectorX);
            ++count;
        }
        while (getMaxX(vectorX) > epsilon);
        // while (getMaxX(vectorX) > epsilon || i < iMax);
    }
}
