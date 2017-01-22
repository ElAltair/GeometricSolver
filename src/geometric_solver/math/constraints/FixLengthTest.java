package geometric_solver.math.constraints;

import geometric_solver.math.Source;
import geometric_solver.math.Variable;
import geometric_solver.math.VariableType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Laiserg on 22.01.2017.
 */
public class FixLengthTest {

    public FixLength fixLength;
    public Source source;

    @Before
    public void init() {
        source = new Source();
        fixLength = new FixLength(  new Variable(0,0, VariableType.X),
                                   new Variable(1,0, VariableType.X),
                                   new Variable(2,20, VariableType.X),
                                   new Variable(3,20, VariableType.X),
                                   20);
    }

    @Test
    public void diff() throws Exception {
        assertEquals(/** ожидаеммое значение */ 4, /** посчитанное значение */ 2 + 2);
    }

    @Test
    public void doubleDiff() throws Exception {

    }

}