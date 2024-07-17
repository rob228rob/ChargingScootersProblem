package com.example.AreaMapper;

import com.example.DataStructures.Area.Area;
import com.example.DataStructures.AreaDivider.AreaDivider;
import Test.GraphTests.Graph.Graph;
import com.example.DataStructures.Node.Node.Node;
import com.example.DataStructures.Node.Station.StationNode;
import com.example.DataStructures.Pair.Pair;
import com.example.TSP.TSP;
import lombok.Getter;
import com.example.DataStructures.Point.Point;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AreaMapper {
    private final TSP tsp;

    private final AreaDivider divider;

    @Getter
    private Map<Area, Graph> graphs;

    @Getter
    private final Pair<Point, Point> areaBounds;

    private Node firstStation;

    private final int batteryLimit;

    private AtomicBoolean running;

    private ScheduledExecutorService executorService;

    public AreaMapper(TSP tsp, AreaDivider divider, Map<Area, Graph> graphObjects, Point leftDownAngle, Point rightUpperAngle, int batteryLimit) {
        this.divider = divider;
        this.graphs = graphObjects;
        this.areaBounds = new Pair<>(leftDownAngle, rightUpperAngle);
        this.firstStation = null;
        this.batteryLimit = batteryLimit;
        this.tsp = tsp;
    }

    public AreaMapper(TSP tsp, int checkPeriodInSec, AreaDivider divider, int gridSize, int batteryLimit, List<Node> nodes, Point leftDownAngle, Point rightUpperAngle) {
        this.divider = divider;
        this.areaBounds = new Pair<>(leftDownAngle, rightUpperAngle);
        this.firstStation = null;
        this.batteryLimit = batteryLimit;
        this.running = new AtomicBoolean(false);
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.tsp = tsp;
        buildMapGraphByAreas(nodes, leftDownAngle, rightUpperAngle, gridSize);
        generateStationsInAreas();
        startUpdateThread(checkPeriodInSec);
    }

    private void startUpdateThread(int checkPeriodInSec) {
        running.set(true);
        executorService.scheduleAtFixedRate(this::updateStationInfo, 0, checkPeriodInSec, TimeUnit.SECONDS);
    }

    private void stopUpdateThread() {
        running.set(false);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private void updateStationInfo() {
        List<Node> updatedVector = new ArrayList<>(); //DatabaseHelper.getDataByChargeAndUpdate();
    /*

    TODO: REPOSITORY INJECTION!!!
     */
        if (updatedVector.isEmpty()) {
            System.out.println("There's no updated data");
            return;
        }

        relocateNodesIfNeed(updatedVector);
        System.out.println("Some info updated!!");

        for (Node node : updatedVector) {
            System.out.println(node.getTypename() + " ; charge: " + node.getChargeLevel() + " state: ");
        }
    }

    private void buildMapGraphByAreas(List<Node> vectorNodes, Point leftMinPoint, Point rightMaxPoint, int gridSize) {
        if (vectorNodes.isEmpty()) {
            throw new IllegalArgumentException("List of nodes is empty in buildMapGraphByAreas()");
        }

        var dividedMap = divider.divideAndMapScooters(vectorNodes, gridSize);

        for (var entry : dividedMap.entrySet()) {
            Area areaObj = entry.getKey();
            List<Node> nodes = entry.getValue();

            var areaGraph = Graph.builder(nodes).buildGraphFromNodes();
            graphs.put(areaObj, areaGraph);
        }
    }

    private void generateStationsInAreas() {
        if (graphs.isEmpty()) {
            throw new IllegalStateException("There's no areas. Graphs is empty");
        }

        Area firstEl = graphs.keySet().iterator().next();
        int gridSize = firstEl.getGridSize();
        var leftPoint = firstEl.getLeftMinPoint();
        var rightPoint = firstEl.getRightMaxPoint();

        double latStep = (rightPoint.first() - leftPoint.first()) / gridSize;
        double lonStep = (rightPoint.second() - leftPoint.second()) / gridSize;

        List<Pair<Area, Node>> insertedStations = new ArrayList<>();

        for (var entry : graphs.entrySet()) {
            Area areaObj = entry.getKey();
            Graph graph = entry.getValue();

            double stationLat = leftPoint.first() + areaObj.getAreaId().first() * latStep + latStep * 0.5;
            double stationLon = leftPoint.second() + areaObj.getAreaId().second() * lonStep + lonStep * 0.5;

            Node station = new StationNode(stationLat, stationLon);
            graph.addNodeWithLinks(station);
            insertedStations.add(new Pair<>(areaObj, station));
        }

        firstStation = insertedStations.get(0).second();
        int length = insertedStations.size();
        for (int i = 0; i < length - 1; ++i) {
            Node current = insertedStations.get(i).second();
            current.setNextNode(insertedStations.get(i + 1).second());
            insertedStations.get(i + 1).second().setPreviousNode(current);

            Graph currentGraph = graphs.get(insertedStations.get(i).first());
            currentGraph.addNodeWithLinks(insertedStations.get(i + 1).second());
        }

        Node endStation = new StationNode(rightPoint.first(), leftPoint.second());

        Pair<Area, Node> last = insertedStations.get(length - 1);
        last.second().setNextNode(endStation);
        graphs.get(last.first()).addNodeWithLinks(endStation);
    }

    public double getAvgChargingPercentageInfo() {
        int globalPercentage = 0;
        int nodeVisited = 0;

        for (Graph graph : graphs.values()) {
            for (Node node : graph.getNodeList()) {
                if (node.getTypename().equals("ScooterNode")) {
                    nodeVisited++;
                    globalPercentage += node.getChargeLevel();
                }
            }
        }

        return (double) globalPercentage / nodeVisited;
    }

    public Node findRandomNode() {
        if (graphs.isEmpty()) {
            throw new IllegalStateException("No graphs available.");
        }

        List<Graph> graphList = new ArrayList<>(graphs.values());
        Graph selectedGraph = graphList.get(new Random().nextInt(graphList.size()));

        List<Node> nodes = selectedGraph.getNodeList();
        if (nodes.isEmpty()) {
            throw new IllegalStateException("Selected graph has no nodes.");
        }

        return nodes.get(new Random().nextInt(nodes.size()));
    }

    public void reduceRandomScooterCharge(int amount) {
        Node scooterNode = findRandomNode();

        if (scooterNode.getTypename().equals("scooter")) {
            int newCharge = scooterNode.getChargeLevel() - amount;
            scooterNode.setChargeLevel(Math.max(newCharge, 0));
        } else {
            reduceRandomScooterCharge(amount);
        }
    }

    public StationIterator createStationIterator(Node startStation) {
        return new StationIterator(this, startStation == null ? firstStation : startStation, tsp);
    }

    public StationIterator createStationIterator() {
        return new StationIterator(this, null, tsp);
    }

    public static class StationIterator {
        private Area currArea;
        private final AreaMapper solver;
        private Node currentStation;
        private Set<Node> unvisitedStations;
        private final TSP tsp;

        public StationIterator(AreaMapper solver, Node currentStation, TSP tsp) {
            this.solver = solver;
            this.currentStation = currentStation;
            this.unvisitedStations = new HashSet<>();
            this.tsp = tsp;

            if (solver.graphs.isEmpty()) {
                throw new IllegalStateException("Object by areas is empty!!");
            }

            this.currArea = solver.graphs.keySet().iterator().next();
            for (Graph graph : solver.graphs.values()) {
                unvisitedStations.addAll(graph.getNodeList());
            }
        }

        public boolean hasNext() {
            return currentStation.getNextNode() != null && !unvisitedStations.isEmpty();
        }

        public List<Pair<Node, Double>> nextStep() {
            if (!hasNext()) {
                throw new NoSuchElementException("There's no more stations to visit");
            }

            Node currentStation = this.currentStation;
            Node targetStation = this.currentStation.getNextNode();
            this.currentStation = targetStation;

            Graph currGraph = solver.graphs.get(currArea);
            currArea = solver.graphs.keySet().iterator().next(); // Should be updated to the next area properly

            List<Node> mostLowBattery = new ArrayList<>(currGraph.getNodeList());
            mostLowBattery.removeIf(node -> !node.getTypename().equals("scooter"));
            mostLowBattery.sort(Comparator.comparingInt(Node::getChargeLevel));

            List<Node> path = tsp.solve(mostLowBattery);

            path.forEach(unvisitedStations::remove);

            List<Pair<Node, Double>> res = new ArrayList<>();
            for (int i = 0; i < path.size() - 1; ++i) {
                res.add(new Pair<>(path.get(i), path.get(i).distanceTo(path.get(i + 1))));
            }

            return res;
        }
    }

    private void relocateNodeIfNeed(Long id) {
        Pair<Node, Pair<Area, Graph>> nodeAreaGraph = findScooterNGraphById(id);
        Node node = nodeAreaGraph.first();
        Area currArea = nodeAreaGraph.second().first();
        Graph currGraph = nodeAreaGraph.second().second();
        boolean leftFromArea = currArea.containsNode(node);

        if (!leftFromArea) {
            return;
        }

        Pair<Area, Graph> newAreaGraph = findAreaNGraphByNodeCoords(node);
        newAreaGraph.second().addNodeWithLinks(node);
        currGraph.removeNode(node);
    }

    private Pair<Area, Graph> findAreaNGraphByNodeCoords(Node targetNode) {
        if (graphs.isEmpty()) {
            throw new IllegalStateException("Graphs is empty");
        }

        for (Map.Entry<Area, Graph> entry : graphs.entrySet()) {
            Area areaObj = entry.getKey();
            Graph graphObj = entry.getValue();

            if (areaObj.containsNode(targetNode)) {
                return new Pair<>(areaObj, graphObj);
            }
        }

        throw new NoSuchElementException("No matching area found for the node");
    }

    private Pair<Node, Pair<Area, Graph>> findScooterNGraphById(Long id) {
        for (Map.Entry<Area, Graph> entry : graphs.entrySet()) {
            Area areaObj = entry.getKey();
            Graph graphObj = entry.getValue();

            for (Node node : graphObj.getNodeList()) {
                if (node.getId().equals(id)) {
                    return new Pair<>(node, new Pair<>(areaObj, graphObj));
                }
            }
        }

        throw new NoSuchElementException("No matching node found with the given ID");
    }

    private void relocateNodesIfNeed(List<Node> nodes) {
        if (nodes.isEmpty()) {
            return;
        }

        for (Node node : nodes) {
            relocateNodeIfNeed(node.getId());
        }
    }
}