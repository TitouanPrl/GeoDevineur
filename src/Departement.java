public class Departement {
    private int id;
    private String name;
    private String mainCity;

    public void setMainCity(String mainCity) {
        this.mainCity = mainCity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkVeracity() {
        // Asks the database if the attributes match the id, return true if that's the case
        return false;
    }
}
