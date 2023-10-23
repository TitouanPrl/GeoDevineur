package com.example.geodevineur.enumerations;

public enum Cardinal {
    W, NW, N, NE, E, SE, S, SW, C;

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
}
