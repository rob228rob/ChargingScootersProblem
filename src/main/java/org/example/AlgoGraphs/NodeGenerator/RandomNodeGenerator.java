package org.example.AlgoGraphs.NodeGenerator;

import org.example.AlgoGraphs.DataStructures.Node.Node.Node;
import org.example.AlgoGraphs.DataStructures.Node.Scooter.ScooterNode;
import org.example.AlgoGraphs.DataStructures.Node.Station.StationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNodeGenerator implements NodeGenerator {

    private final double minLat;

    private final double maxLat;

    private final double minLon;

    private final double maxLon;

    private final Random random = new Random();

    public RandomNodeGenerator(double minLat, double maxLat, double minLon, double maxLon) {
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;
    }

    private ScooterNode generateScooter() {
        Long id = random.nextLong();
        double lat = minLat + (maxLat - minLat) * random.nextDouble();
        double lon = minLon + (maxLon - minLon) * random.nextDouble();
        var chargeLevel = (int) (20 + (80 - 20) * random.nextDouble()); // Random charge between 20 and 80

        return new ScooterNode(id, lat, lon, chargeLevel, true);
    }

    private StationNode generateStation() {
        Long id = random.nextLong();
        double lat = minLat + (maxLat - minLat) * random.nextDouble();
        double lon = minLon + (maxLon - minLon) * random.nextDouble();
        int batteryCount = random.nextInt(100);

        return new StationNode(id, lat, lon, batteryCount, batteryCount, true);
    }

    @Override
    public List<Node> generateScooterList(int length) {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            nodeList.add(generateScooter());
        }

        return nodeList;
    }

    @Override
    public List<Node> generateStationList(int length) {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            nodeList.add(generateStation());
        }

        return nodeList;
    }

    @Override
    public List<Node> generateStationsAndScootersList(int stationsCount, int scootersCount) {
        List<Node> nodeList = new ArrayList<>();
        nodeList.addAll(generateStationList(stationsCount));
        nodeList.addAll(generateScooterList(scootersCount));

        return nodeList;
    }

}
