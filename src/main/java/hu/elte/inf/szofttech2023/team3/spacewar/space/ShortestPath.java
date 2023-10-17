package hu.elte.inf.szofttech2023.team3.spacewar.space;

import java.awt.Point;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * This class contains provides the method called
 * {@code run(start,target)} to determine the shortest path
 * between to {@code Point}s in the {@code Space}.
 */
public class ShortestPath {
    private static final int[][] DIRECTIONS = { {1,0}, {0,1}, {-1,0}, {0,-1},
                                                {-1,-1}, {-1,1}, {1,1}, {1,-1}};
    private static double[][] dist;
    private static Map<Point,Point> preNodes;
    private static Path path;
    private final Space space;

    /**
     * Initializes the ShortestPath object.
     * @param space the Space object in which the path is searched.
     */
    public ShortestPath(Space space) {
        this.space = space;
    }

    /**
     * Searches the shortest path from start to end {@code Point} in the {@code Space}.
     * @param s the source {@code Point} of the path to search
     * @param t the target {@code Point} of the path to search
     * @return the {@code Point} array containing the {@code Point}s of the path
     * or {@code null} if no path is found
     * @throws IllegalArgumentException if the target point is invalid
     */
    public Path run(Point s, Point t) throws IllegalArgumentException {
        if(space.isSpaceObject[t.x][t.y]) {
            throw new IllegalArgumentException("Illegal target point!");
        }
        boolean[][] visited = new boolean[space.width][space.height];
        dist = new double[space.width][space.height];
        Arrays.stream(dist).forEach(row -> Arrays.fill(row, Double.MAX_VALUE));
        preNodes = new HashMap<>();
        path = new Path();
        Queue<Point> queue = new PriorityQueue<>(
                Comparator.comparingDouble(a -> euclidean(a, t) + dist[a.x][a.y])
        );
        queue.add(s);
        visited[s.x][s.y] = true;
        dist[s.x][s.y] = 0;
        preNodes.put(s,null);
        while(!queue.isEmpty()) {
            var curr = queue.poll();
            if (curr.equals(t)) {
                return decodePath(s,t);
            }
            for(int[] dir : DIRECTIONS) {
                var next = new Point(curr.x + dir[0],curr.y + dir[1]);
                if(isValidStep(next)) {
                    var newDist = dist[curr.x][curr.y] + euclidean(curr,next);
                    if(!visited[next.x][next.y] || newDist < dist[next.x][next.y]) {
                        queue.add(next);
                        visited[next.x][next.y] = true;
                        dist[next.x][next.y] = newDist;
                        preNodes.put(next,curr);
                    }
                }
            }
        }
        return path;
    }

    /**
     * Determines the Euclidean distance between the given {@code Point}s
     * @param a the start {@code Point} in the {@code Space}
     * @param b the end {@code Point} in the {@code Space}
     * @return the Euclidean distance between the given {@code Point}s
     */
    private static double euclidean(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    /**
     * Decides if the given {@code Point} can be a valid step
     * @param p a {@code Point} to be validated
     * @return {@code true} if the given {@code Point} can be a valid step
     */
    private boolean isValidStep(Point p) {
        return p.x >= 0 && p.x < space.width &&
               p.y >= 0 && p.y < space.height &&
               !space.isSpaceObject[p.x][p.y];
    }

    /**
     * Decodes the found path from the predecessor nodes.
     * @param s the source {@code Point} of the path
     * @param t the target {@code Point} of the path
     * @return the {@code Path} containing the {@code Point}s of the path
     */
    private static Path decodePath(Point s, Point t) {
        Point p = new Point(t);
        path.add(p);
        do {
            p = preNodes.get(p);
            path.add(p);
        }
        while(!p.equals(s));
        path.setCost(dist[t.x][t.y]);
        return path;
    }

    // demo usage
    public static void main(String[] args) {
        final var space = Space.getInstance(10,7);
        final var generateSpace = new GenerateSpace(space);
        generateSpace.run(7,3,1);
        final var shortestPath = new ShortestPath(space);
        try {
            final var path = shortestPath.run(new Point(0,0), new Point(9,6));
            System.out.println("Path: ");
            while(path.hasNext()) {
                System.out.println(path.next());
            }
            System.out.println("Length: " + path.getLength());
            System.out.println("Total cost: " + path.getTotalCost());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

