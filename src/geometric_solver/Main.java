package geometric_solver;

import geometric_solver.math.*;

public class Main {

    public static void main(String[] args) {

        Source source = new Source();
        Lagrange lagrange = new Lagrange(source);
        lagrange.addPoint(5.0).addPoint(7.0);
        System.out.println(lagrange.diff(source.getVariable(0), source));
        System.out.println(lagrange.diff(source.getVariable(1), source));

    }
}
