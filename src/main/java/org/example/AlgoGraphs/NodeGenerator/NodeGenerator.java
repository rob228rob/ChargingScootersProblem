package org.example.AlgoGraphs.NodeGenerator;

import org.example.AlgoGraphs.DataStructures.Node.Node.Node;

import java.util.List;

public interface NodeGenerator {

    public List<Node> generateScooterList(int length);

    public List<Node> generateStationList(int length);

    public List<Node> generateStationsAndScootersList(int stationsCount, int scootersCount);
}
