package dev.sadik.GraphX;

//this class is used to represent data style that'll be used in the table
public class DataModel {
    private double x;
    private double y;

    public DataModel() {
        x = 0;
        y = 0;
    }

    public DataModel(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
