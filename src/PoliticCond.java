public class PoliticCond extends Condition{
    private Politic seaside;

    public PoliticCond (Departement dep_) {
        setAttributes(dep_);
    }

    protected void setAttributes(Departement dep) {
        seaside = dep.getPolitic();
    }

    public boolean checksCondition(Departement dep) {
        return dep.getPolitic() == seaside;
    }
}
