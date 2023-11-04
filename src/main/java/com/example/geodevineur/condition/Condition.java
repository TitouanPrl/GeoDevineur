package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;

/* Used to create the question of the quizz */
public abstract class Condition<E extends Departement> {

    /* Checks if answer of the player match the expected answer */
    public abstract boolean checksCondition(E e);

    /* Defines condition attributes */
    protected abstract void setAttributes(E e);

    /* Set the text print for the question */
    public abstract String getSentence();
}
