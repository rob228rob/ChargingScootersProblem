package org.example.NodeGenerator;

import org.example.DataStructures.node.node.Node;

import java.util.List;

public interface NodeGenerator {

    public List<Node> generateScooterList(int length);

    public List<Node> generateStationList(int length);

    public List<Node> generateStationsAndScootersList(int stationsCount, int scootersCount);
}
