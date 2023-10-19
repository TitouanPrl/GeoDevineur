
public abstract class Condition {
    // Constructors must take a Departement as arg and run setAttributes
    public abstract boolean checksCondition(Departement d);
    protected abstract void setAttributes(Departement d);
}
