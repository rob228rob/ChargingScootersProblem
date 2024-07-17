package com.example.DataStructures.Node.Node;

import lombok.*;

import java.util.List;

import static java.lang.Math.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Node {

    private Long id;

    private double latitude;

    private double longitude;

    private List<Node> edges;

    public Node(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Node(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addEdge(Node edge) {
        edges.add(edge);
    }

    private double haversine(double lat1Deg, double lon1_deg, double lat2Deg, double lon2_deg) {
        double degToRad = PI / 180.0;

        double deltaLatRad = (lat2Deg - lat1Deg) * degToRad;
        double deltaLonRad = (lon2_deg - lon1_deg) * degToRad;

        lat1Deg *= degToRad;
        lat2Deg *= degToRad;

        double coefficientA = sin(deltaLatRad / 2) * sin(deltaLatRad / 2)
                + cos(lat1Deg) * cos(lat2Deg)
                * sin(deltaLonRad / 2) * sin(deltaLonRad / 2);
        double coefficientC = 2 * atan2(sqrt(coefficientA), sqrt(1 - coefficientA));
        double earth_radius_km = 6371.0;

        return earth_radius_km * coefficientC;
    }

    public double distanceTo(@NonNull Node other) {
        return haversine(latitude, longitude, other.getLatitude(), other.getLongitude());
    }

    public int getChargeLevel() {
        throw new UnsupportedOperationException("Charge level is not supported for this: " + this.getClass().getSimpleName());
    }

    public Node getNextNode() {
        throw new UnsupportedOperationException("Next node is not supported for this: " + this.getClass().getSimpleName());
    }

    public Node getPreviousNode() {
        throw new UnsupportedOperationException("Previous node is not supported for this: " + this.getClass().getSimpleName());
    }

    public void setChargeLevel(int node) {
        throw new UnsupportedOperationException("Charge level is not supported for this: " + this.getClass().getSimpleName());
    }

    public void setNextNode(Node node) {
        throw new UnsupportedOperationException("Next node is not supported for this: " + this.getClass().getSimpleName());
    }

    public void setPreviousNode(Node node) {
        throw new UnsupportedOperationException("Previous node is not supported for this: " + this.getClass().getSimpleName());
    }

    public String getTypename()
    {
        return "default_node";
    }

    @Override
    public String toString() {
        return "{" + "id=" + id + ", latitude=" + latitude + ", longitude=" + longitude;
    }
}
