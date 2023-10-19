public class NbCharacters extends Condition{
    private int nbChar;

    public NbCharacters(Departement d) {
        setAttributes(d);
    }

    protected void setAttributes(Departement d) {
        nbChar = d.getName().length();
    }

    public boolean checksCondition(Departement d) {
        return (d.getName().length() == nbChar);
    }
}
