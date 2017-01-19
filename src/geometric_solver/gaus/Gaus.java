package geometric_solver.gaus;

public class Gaus {
    private static final double EPSILON = 1e-10;

    // Gaussian elimination with partial pivoting
    public static double[] solve(double[][] A, double[] b) {
        int N = b.length;

        for (int p = 0; p < N; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < N; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < N; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < N; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    /*
    public static double[] solve(ArrayList<ArrayList<Double>>A, ArrayList<Double> b) {
        int N  = b.size();

        for (int p = 0; p < N; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < N; i++) {
                if (Math.abs(A.get(i).get(p)) > Math.abs(A.get(max).get(p))){
                    max = i;
                }
            }
            ArrayList<Double> temp = A.get(p);
            A.set(p, A.get(max));
            A.set(max, temp);
            A.set(max, temp);
            double   t    = b.get(p);
            b.set(p, b.get(max));
            b.set(max, t;

            // singular or nearly singular
            if (Math.abs(A.get(p).get(p)) <= EPSILON) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < N; i++) {
                double alpha = A.get(i).get(p) / A.get(p).get(p);
                b.get(i) -= alpha * b.get(p);
                for (int j = p; j < N; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }
    */


    /*
    // javafx client
    public static void main(String[] args) {
        int N = 3;
        double[][] A = {{0, 3, -1},
                {1, 0, 1},
                {1, 0, 0}
        };
        double[] b = {9, 3, 2};
        double[] x = solve(A, b);


        // print results
        for (int i = 0; i < N; i++) {
            System.out.println(x[i]);
        }

    }
    */

}

