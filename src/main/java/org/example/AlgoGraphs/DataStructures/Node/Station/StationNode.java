package org.example.AlgoGraphs.DataStructures.Node.Station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.AlgoGraphs.DataStructures.Node.Node.Node;

@AllArgsConstructor
@Getter
@Setter
public class StationNode extends Node {

    private int batteryCapacity;

    private int batteryCurrent;

    private boolean isBatteryFullyCharged;

    Node nextStation;

    Node prevStation;

    public StationNode(Long id, double lat, double lng, int batteryCapacity, int batteryCurrent, boolean isBatteryFullyCharged, Node nextStation, Node prevStation) {
        super(id, lat, lng);
        this.batteryCapacity = batteryCapacity;
        this.batteryCurrent = batteryCurrent;
        this.isBatteryFullyCharged = isBatteryFullyCharged;
        this.nextStation = nextStation;
        this.prevStation = prevStation;
    }

    public StationNode(Long id, double lat, double lng, int batteryCapacity, int batteryCurrent, boolean isBatteryFullyCharged) {
        super(id, lat, lng);
        this.batteryCapacity = batteryCapacity;
        this.batteryCurrent = batteryCurrent;
        this.isBatteryFullyCharged = isBatteryFullyCharged;
        this.nextStation = null;
        this.prevStation = null;
    }

    public StationNode( double lat, double lng) {
        super(lat, lng);
        this.batteryCapacity = 20;
        this.batteryCurrent = 20;
        this.isBatteryFullyCharged = true;
        this.nextStation = null;
        this.prevStation = null;
    }

    @Override
    public Node getNextNode() {
        return nextStation;
    }

    @Override
    public void setNextNode(Node nextStation) {
        this.nextStation = nextStation;
    }

    @Override
    public String getTypename() {
        return "StationNode";
    }
}
