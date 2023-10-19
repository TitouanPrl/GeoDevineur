import java.util.Random;

public class ContainLetter extends Condition{
    private Random random;
    private char letter;

    public ContainLetter(Departement dep_) {
        random = new Random();
        setAttributes(dep_);
    }
    protected void setAttributes(Departement departement) {
        char selected;
        do {
            int a = random.nextInt(departement.getName().length());
            selected = departement.getName().charAt(a);
        } while ((selected == ' ') || (selected == '-') || (selected == '\''));
        letter = selected;
    }

    public boolean checksCondition(Departement d) {
        boolean res = false;
        String foreignName = d.getName();
        for (int i = 0; i < foreignName.length() && !res; i++) {
            res = foreignName.charAt(i) == letter;
        }
        return res;
    }
}
