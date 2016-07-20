package junit;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myeongju.jung
 */
public class BetweenSupplier extends ParameterSupplier {

    public BetweenSupplier() {
    }

    @Override
    public List<PotentialAssignment> getValueSources(ParameterSignature sig) throws Throwable {
        Between annotation = sig.getAnnotation(Between.class);

        ArrayList<PotentialAssignment> list = new ArrayList();
        for (int i = annotation.first(); i <= annotation.last(); i++)
            list.add(PotentialAssignment.forValue("value : " + i, i));
        return list;
    }
}
