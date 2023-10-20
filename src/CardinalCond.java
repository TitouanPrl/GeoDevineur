public class CardinalCond extends Condition{
    private Cardinal seaside;

    public CardinalCond (Departement dep_) {
        setAttributes(dep_);
    }

    protected void setAttributes(Departement dep) {
        seaside = dep.getCardinal();
    }

    public boolean checksCondition(Departement dep) {
        return dep.getCardinal() == seaside;
    }
}
