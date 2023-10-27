package hu.elte.inf.szofttech2023.team3.spacewar.space;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides an implementation of the {@code Iterator} interface
 * with the {@code Point}s of the found path.
 */
public class Path implements Iterator<Point> {
    private final List<Point> path = new ArrayList<>();
    private double totalCost = 0;
    private int iterator = -1;

    public void add(Point p) {
        path.add(p);
        ++iterator;
    }

    public void setCost(double cost) {
        totalCost += cost;
    }

    public int getLength() {
        return path.size();
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public boolean hasNext() {
        return iterator >= 0;
    }

    @Override
    public Point next() {
        return path.get(iterator--);
    }
}