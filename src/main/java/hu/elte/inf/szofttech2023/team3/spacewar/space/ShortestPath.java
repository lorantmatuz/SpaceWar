package hu.elte.inf.szofttech2023.team3.spacewar.space;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;

/**
 * This class contains the {@code static} method called
 * {@code run(start,end)}to determine the shortest path
 * between to {@code Point}s in the {@code Space}.
 */
public class ShortestPath extends Space {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * Searches the shortest path from start to end {@code Point} in the {@code Space}.
     * @param start the start {@code Point} of the path to search
     * @param end the end {@code Point} of the path to search
     * @return the {@code Point} array containing the {@code Point}s of the path
     * or {@code null} if no path is found
     */
    public static Point[] run(Point start, Point end) {
        Queue<Point> queue = new PriorityQueue<>(ShortestPath::manhattan);
        boolean[][] visited = new boolean[width][height];
        Map<Point,Point> preNodes = new HashMap<>();
        queue.add(start);
        preNodes.put(start,null);
        visited[start.x][start.y] = true;
        while(!queue.isEmpty()) {
            Point curr = queue.poll();
            if (curr.equals(end)) {
                return decodePath(start,end,preNodes);
            }
            for(int[] dir : DIRECTIONS) {
                Point next = new Point(curr.x + dir[0],curr.y + dir[1]);
                if(isValidStep(next,end,visited)) {
                    queue.add(next);
                    visited[next.x][next.y] = true;
                    preNodes.put(next,curr);
                }
            }
        }
        return null;
    }

    /**
     * Determines the Manhattan distance between the given {@code Point}s
     * @param a the start {@code Point} in the {@code Space}
     * @param b the end {@code Point} in the {@code Space}
     * @return the Manhattan distance between the given {@code Point}s
     */
    private static int manhattan(Point a, Point b) {
        return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
    }

    /**
     * Decides if the given {@code Point} can be a valid step
     * @param p a {@code Point} to be validated
     * @param end the end {@code Point} of the path to search
     * @param visited the {@code visited} boolean array of the search
     * @return {@code true} if the given {@code Point} can be a valid step
     */
    private static boolean isValidStep(Point p, Point end, boolean[][] visited) {
        return p.x >= 0 && p.x < width &&
               p.y >= 0 && p.y < height &&
                (!isSpaceObject[p.x][p.y] && !visited[p.x][p.y]) ||
                p.equals(end);
    }

    /**
     * Decodes the found path from the predecessor nodes.
     * @param start the start {@code Point} of the path
     * @param end the end {@code Point} of the path
     * @param preNodes the map of the predecessor {@code Point}s
     * @return the {@code Point} array containing the {@code Point}s of the path
     */
    private static Point[] decodePath(Point start, Point end, Map<Point,Point> preNodes) {
        ArrayList<Point> reversedPath = new ArrayList<>();
        Point p = new Point(end);
        reversedPath.add(p);
        do {
            p = preNodes.get(p);
            reversedPath.add(p);
        }
        while(!p.equals(start));
        final int size = reversedPath.size();
        Point[] path = new Point[size];
        for (int i = 0; i < size; i++) {
            path[i] = reversedPath.get(size-i-1);
        }
        return path;
    }
}
