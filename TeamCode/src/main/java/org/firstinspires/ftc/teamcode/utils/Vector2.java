package org.firstinspires.ftc.teamcode.utils;

/**
 * Is literally never used. Maybe one day...
 *
 * @author github.com/jakeslye
 */
public class Vector2 {

    public double x, y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 other){
        this.x += other.x;
        this.y += other.y;
    }

    public static Vector2 add(Vector2 v1, Vector2 v2){
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }
}
