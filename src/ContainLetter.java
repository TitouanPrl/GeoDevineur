import java.util.Random;

public class ContainLetter implements Condition{
    private Departement departement;
    private Random random;
    private char letter;

    public ContainLetter(Departement dep_) {
        departement = dep_;
        random = new Random();
    }
    public void setAttributes() {
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
