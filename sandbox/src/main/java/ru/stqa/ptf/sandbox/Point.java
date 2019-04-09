package ru.stqa.ptf.sandbox;

public class Point {
     double x;
     double y;


    public Point(double a, double b) {
        this.x = a;
        this.y = b;
    }

    public double distance(Point p2) {
        double dist = Math.sqrt((p2.x - this.x)*(p2.x - this.x) + (p2.y - this.y)*(p2.y - this.y));
        return dist;
    }

}
