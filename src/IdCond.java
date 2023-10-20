import java.util.Random;

public class IdCond extends Condition {
    private Random random;
    private Departement compareDep;
    private boolean isLess; // If more, isLess is false
    private String threshold;

    public IdCond (Departement dep_, Departement compareDep_) {
        random = new Random();
        setAttributes(dep_);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare) {
            compareDep = compareDep_;
        } else {
            compareDep = null;
        }
    }

    protected void setAttributes(Departement dep) {
        if (compareDep != null) {
            threshold = compareDep.getId();
            if (compareId(threshold, dep.getId()) < 0) {
                isLess = true;
            } else {
                isLess = false;
            }
        } else {
            // create appropriate thresholds
        }
    }

    public boolean checksCondition(Departement dep) {
        if (isLess) {
            return compareId(dep.getId(), threshold) < 0;
        } else {
            return compareId(dep.getId(), threshold) > 0;
        }
    }

    private int compareId(String id1, String id2) {
        if (Character.compare(id1.charAt(0), id2.charAt(0)) != 0) 
            return Character.compare(id1.charAt(0), id2.charAt(0));
        return Character.compare(id1.charAt(1), id2.charAt(1));

    }
}
