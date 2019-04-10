package ru.stqa.ptf.sandbox;

public class DistanceBetweenPoint {

    public static void main(String[] args) {
        Point p1 = new Point(1,2);
        Point p2 = new Point(4,4);

       double result = p1.distance(p2);
       System.out.print(result);
    }


}
