package org.firstinspires.ftc.teamcode.utils;

/**
 * Also is literally never used. Maybe one day...
 *
 * @author github.com/jakeslye
 */
public class Vector3 {
    public double x, y, z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3 other){
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    public static Vector3 add(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
}
