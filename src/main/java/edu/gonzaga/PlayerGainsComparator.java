package edu.gonzaga;

import java.util.Comparator;

public class PlayerGainsComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        // a negative integer, zero, or a positive integer as
        // the second argument is less than, equal to, or greater than the first.
        return (int) (o2.getGains() - o1.getGains());
    }
}
