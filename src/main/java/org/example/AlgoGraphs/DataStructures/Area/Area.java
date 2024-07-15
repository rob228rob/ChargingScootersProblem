package org.example.AlgoGraphs.DataStructures.Area;

import lombok.Getter;
import lombok.Setter;
import org.example.AlgoGraphs.DataStructures.Pair.Pair;
import org.example.AlgoGraphs.DataStructures.Node.Node.Node;

import java.util.Objects;

@Getter
@Setter
public class Area {
    private int gridSize;
    private Pair<Integer, Integer> areaId;
    private Pair<Double, Double> leftMinPoint;
    private Pair<Double, Double> rightMaxPoint;

    public Area() {
        this.areaId = new Pair<>(-1, -1);
    }

    public Area(double latMin, double latMax, double lonMin, double lonMax, Pair<Integer, Integer> areaId, int gridSize) {
        this.leftMinPoint = new Pair<>(latMin, lonMin);
        this.rightMaxPoint = new Pair<>(latMax, lonMax);
        this.areaId = areaId != null ? areaId : new Pair<>(-1, -1);
        this.gridSize = gridSize;
    }

    public void setAreaId(int first, int second) {
        this.areaId = new Pair<>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return gridSize == area.gridSize &&
                Objects.equals(areaId, area.areaId) &&
                Objects.equals(leftMinPoint, area.leftMinPoint) &&
                Objects.equals(rightMaxPoint, area.rightMaxPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridSize, areaId, leftMinPoint, rightMaxPoint);
    }

    public boolean lessThan(Area other) {
        return this.areaId.compareTo(other.areaId) < 0;
    }

    public void setBoundaries(double latMin, double lonMin, double latMax, double lonMax, int gridSize) {
        this.leftMinPoint = new Pair<>(latMin, lonMin);
        this.rightMaxPoint = new Pair<>(latMax, lonMax);
        this.gridSize = gridSize;
    }

    public boolean containsNode(Node n) {
        double lat = n.getLatitude();
        double lon = n.getLongitude();

        return lat >= leftMinPoint.getFirst() && lat <= rightMaxPoint.getFirst() &&
                lon >= leftMinPoint.getSecond() && lon <= rightMaxPoint.getSecond();
    }

}
