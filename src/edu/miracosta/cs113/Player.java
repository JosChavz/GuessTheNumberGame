package edu.miracosta.cs113;

import java.io.Serializable;

public class Player implements Serializable, Comparable {
    private String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    void setName(String name1) {
        this.name = name1;
    }

    public int getScore() { return this.score; }

    @Override
    public String toString() {
        return this.name + " - " + this.score;
    }

    @Override
    public int compareTo(Object o) {
        // In the case that o is not a Player
        if( !(o instanceof Player) ) throw new ClassCastException();

        // This object's score is greater than o's score.
        if( this.score > ((Player) o).score) return 1;
        // This object's score is less than o's score.
        else if( this.score < ((Player) o).score ) return -1;

        // Both scores are the same
        return 0;
    }
}