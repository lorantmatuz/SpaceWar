package hu.elte.inf.szofttech2023.team3.spacewar.model.space;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides an implementation of the {@code Iterator} interface
 * with the {@code Point}s of the found path.
 */
public class Path implements Iterator<ArcOfPath> {
    private final List<Point> path = new ArrayList<>();
    private double accumulatingTotalCost = 0;
    private double totalCost;
    private int head = -1;

    public void add(Point p) {
        path.add(p);
        ++head;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getLength() {
        return path.size();
    }

    @Override
    public boolean hasNext() {
        return head >= 0;
    }

    @Override
    public ArcOfPath next() {
        final var node = path.get(head);
        if(head + 1 < path.size()) {
            accumulatingTotalCost += ShortestPath.euclidean(node, path.get(head + 1));
        }
        --head;
        return new ArcOfPath(node, accumulatingTotalCost);
    }

    public void print(Space space, boolean[][] visited) {
        for (int j = 0; j < space.height; j++) {
            for (int i = 0; i < space.width; i++) {
                if(path.contains(new Point(i,j))) {
                    System.out.print("O ");
                } else if(space.isSpaceObject[i][j]) {
                    System.out.print("X ");
                } else if(visited[i][j]) {
                    System.out.print("# ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
