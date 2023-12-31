package com.example.geodevineur.enumerations;

public enum Cardinal {
    W, NW, N, NE, E, SE, S, SW, C;

    /* Converts a position from string to Cardinal type */
    public static Cardinal fromString(String input) {
        if (input.equals("N")) return Cardinal.N;
        if (input.equals("NE")) return Cardinal.NE;
        if (input.equals("E")) return Cardinal.E;
        if (input.equals("SE")) return Cardinal.SE;
        if (input.equals("S")) return Cardinal.S;
        if (input.equals("SO")) return Cardinal.SW;
        if (input.equals("O")) return Cardinal.W;
        if (input.equals("NO")) return Cardinal.NW;
        if (input.equals("C")) return Cardinal.C;
        return null;
    }

    /* Prints a Cardinal type to string */
    public String toString(){
        if (this.equals(Cardinal.N)) return "Nord";
        if (this.equals(Cardinal.NE)) return "Nord-Est";
        if (this.equals(Cardinal.E)) return "Est";
        if (this.equals(Cardinal.SE)) return "Sud-Est";
        if (this.equals(Cardinal.S)) return "Sud";
        if (this.equals(Cardinal.SW)) return "Sud-Ouest";
        if (this.equals(Cardinal.W)) return "Ouest";
        if (this.equals(Cardinal.NW)) return "Nord-Ouest";
        if (this.equals(Cardinal.C)) return "Centre";
        return null;
    }
}
