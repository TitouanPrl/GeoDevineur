public class NbCharactersCond extends Condition{
    private int nbChar;

    public NbCharactersCond(Departement dep_) {
        setAttributes(dep_);
    }

    protected void setAttributes(Departement dep) {
        nbChar = dep.getName().length();
    }

    public boolean checksCondition(Departement d) {
        return (d.getName().length() == nbChar);
    }
}
