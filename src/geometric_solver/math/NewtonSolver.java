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

    public ArrayList<Double> solve() {

        updateSolver();

        while (getMaxX(vectorX) < epsilon) {
            HesseMatrix = builder.createMatrixA();
            resultVector = builder.createVectorB();
            vectorX = Gaus.solve(HesseMatrix, resultVector);
            solverSource.update(vectorX);
        }
        List<Double> list = Arrays.stream(vectorX).boxed().collect(Collectors.toList());
        return new ArrayList<>(list);
    }
}
