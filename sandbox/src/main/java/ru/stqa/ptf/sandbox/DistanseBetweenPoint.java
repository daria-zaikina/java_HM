package ru.stqa.ptf.sandbox;

public class DistanseBetweenPoint {

    public static void main(String[] args) {
        Point p1 = new Point(2,2);
        Point p2 = new Point(4,4);

       double result = distance( p1, p2);
       System.out.print(result);
    }


    public static double distance(Point p1, Point p2) {
        double dist = Math.sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y));
        return dist;
    }

}
