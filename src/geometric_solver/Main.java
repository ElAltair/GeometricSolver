package geometric_solver;

import geometric_solver.geometry.*;
import geometric_solver.math.*;
import geometric_solver.math.constraints.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Begin");
        Source testSource = new Source();
        Lagrange testLagrange = new Lagrange(testSource);
        Point testPoint = new Point(5.0, 7.0);
        testLagrange.addComponents((testPoint.getLagrangeComponents()));
        FixAxis testFixXPoint = new FixAxis(Axis.AXIS_X, 4.0);
        testLagrange.addComponentsAxis(testFixXPoint);
        FixAxis testFixYPoint = new FixAxis(Axis.AXIS_Y, 3.0);
        testLagrange.addComponentsAxis(testFixYPoint);
        MatrixBuilder testMatrix = new MatrixBuilder(testLagrange.getSize(), testLagrange, testSource);
        testMatrix.generateAndPrint();
        System.out.println(testLagrange.print());
    }
}
