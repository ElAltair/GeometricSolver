package geometric_solver;

import geometric_solver.geometry.*;
import geometric_solver.math.*;
import geometric_solver.math.constraints.*;
import javafx.Pos;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        System.out.println("Begin");
        Source source = new Source();
        Lagrange lagrange = new Lagrange(source);
        NewtonSolver newtonSolver = new NewtonSolver(lagrange, source);
        Point point = new Point(508.0, 62.0);
        lagrange.addComponents((point.getLagrangeComponents()));
        point.fixAxis(Axis.AXIS_Y, 199.0);
        lagrange.addConstraint(point.getConstrains());

        //point.fixAxis(Axis.AXIS_Y, 100.0);
        //lagrange.addConstraint(point.getConstrains());

        source.print();
        System.out.println(lagrange.print());
        newtonSolver.solve();
        System.out.println("Result: ");
        source.print();



        /*
        System.out.println("Begin");
        Source source = new Source();
        Lagrange lagrange = new Lagrange(source);
        NewtonSolver newtonSolver = new NewtonSolver(lagrange, source);
        Line line = new Line(100.0,100.0,200.0,200.0);
        Point point = new Point(508.0, 62.0);
        lagrange.addComponents((point.getLagrangeComponents()));
        //lagrange.addConstraint(point.fixAxis(Axis.AXIS_X,199.0));
        lagrange.addConstraint(point.fixAxis(Axis.AXIS_Y, 100.0));
        source.print();
        System.out.println(lagrange.print());
        newtonSolver.solve();
        System.out.println("Result: ");
        source.print();
        */

    }
}
