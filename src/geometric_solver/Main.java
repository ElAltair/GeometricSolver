package geometric_solver;

import geometric_solver.math.*;

public class Main {

    public static void main(String[] args) {

        Variable var1 = new Variable(1, VariableType.X);
        Variable var2 = new Variable(2, VariableType.X);
        Source source = new Source();
        source.add(var1, 4.0);
        source.add(var2, 4.0);
        SquaredSumm ss1 = new SquaredSumm(var1, 5.0);
        SquaredSumm ss2 = new SquaredSumm(var2, 7.0);

        Lagrange lagrange = new Lagrange(ss1, ss2);
        System.out.println(lagrange.diff(var1, source));
        System.out.println(lagrange.diff(var2, source));

    }
}
