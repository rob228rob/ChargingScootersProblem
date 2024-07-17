package com.example.DataStructures.Point;

public record Point(double latitude, double longitude) implements Comparable<Point> {

    @Override
    public int compareTo(Point other) {
        int latitudeComparison = Double.compare(this.latitude, other.latitude);
        if (latitudeComparison != 0) {
            return latitudeComparison;
        }
        return Double.compare(this.longitude, other.longitude);
    }
}
