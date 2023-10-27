package com.example.geodevineur.enumerations;

public enum Politic {
    LR, UDI, PS, DVD, DVG, Regionaliste, Renaissance, PRG, FaC, RE;

    public static Politic fromString(String input) {
        if (input.equals("LR")) return Politic.LR;
        if (input.equals("UDI")) return Politic.UDI;
        if (input.equals("PS")) return Politic.PS;
        if (input.equals("DVD")) return Politic.DVD;
        if (input.equals("DVG")) return Politic.DVG;
        if (input.equals("Régionaliste")) return Politic.Regionaliste;
        if (input.equals("Renaissance")) return Politic.Renaissance;
        if (input.equals("PRG")) return Politic.PRG;
        if (input.equals("FaC")) return Politic.FaC;
        if (input.equals("RE")) return Politic.RE;

        return null;
    }
}
