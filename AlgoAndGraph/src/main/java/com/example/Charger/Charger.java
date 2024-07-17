package com.example.Charger;

import com.example.AreaMapper.AreaMapper;
import com.example.DataStructures.AreaDivider.AreaDivider;
import com.example.DataStructures.Node.Node.Node;
import com.example.DataStructures.Pair.Pair;
import com.example.NodeGenerator.NodeGenerator;
import com.example.NodeGenerator.RandomNodeGenerator;
import com.example.TSP.TSPAntColony;
import lombok.Getter;
import com.example.DataStructures.Point.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Charger {
    private final AreaMapper areaMapper;

    @Getter
    private int batteryQuantity;

    @Getter
    private final int maxBatteryLimit;

    private final int criticalChargeValue;

    private final Logger logger;

    private Node targetStation;

    private final NodeGenerator generator;

    private final AreaMapper.StationIterator it;

    public Charger(List<Node> nodes) {
        var leftPoint = new Pair<>(10.0, 10.1) ;
        var rightPoint = new Pair<>(20.0, 20.1);
        this.generator = new RandomNodeGenerator(leftPoint.first(), rightPoint.first(), leftPoint.second(), rightPoint.second());
        this.areaMapper = new AreaMapper(new TSPAntColony(), 5 ,new AreaDivider(leftPoint, rightPoint), 4, 20, nodes, new Point(leftPoint.first(), leftPoint.second()), new Point(rightPoint.first(), rightPoint.second()));
        this.targetStation = null;
        this.batteryQuantity = 20;
        this.maxBatteryLimit = 20;
        this.criticalChargeValue = 20;
        this.logger = LoggerFactory.getLogger(this.getClass());

        this.it = areaMapper.createStationIterator();
    }

    public Charger(AreaMapper solver, String loggerFilename, int criticalChargeValue, int batteryLimit) {
        this.areaMapper = solver;
        this.it = solver.createStationIterator();
        this.batteryQuantity = batteryLimit;
        this.maxBatteryLimit = batteryLimit;
        this.criticalChargeValue = criticalChargeValue;
        this.logger = LoggerFactory.getLogger(loggerFilename);
        var leftPoint = areaMapper.getAreaBounds().first();
        var rightPoint = areaMapper.getAreaBounds().second();
        this.generator = new RandomNodeGenerator(leftPoint.latitude(), rightPoint.latitude(), leftPoint.longitude(), rightPoint.longitude()); // assuming NodeGenerator has a default constructor
    }

    public void reloadBatteryQuantity() {
        logger.info("Battery was reloaded at the station. Amount of battery before reloading: {}", batteryQuantity);
        batteryQuantity = maxBatteryLimit;
        targetStation = Optional.ofNullable(targetStation).map(Node::getNextNode).orElse(null);
    }

    public boolean chargeScooterTo100(Node scooter) {
        if (!"scooter".equals(scooter.getTypename())) {
            return false;
        }
        logger.info("Scooter charged to 100%. Previous charge: {}{}", scooter.getChargeLevel(), scooter.toString());
        scooter.setChargeLevel(100);
        return true;
    }

    public boolean decreaseBatteryQuantity(Node scooter) {
        if (batteryQuantity == 0) {
            return false;
        }
        batteryQuantity--;
        logger.info("Battery quantity decreased, current value: [{}]", batteryQuantity);
        return true;
    }

    public void startRouteFollowing() {
        logger.info("Route handler started.");

        while (it.hasNext()) {
            List<Pair<Node, Double>> path = it.nextStep();
            logger.info("Path to next station found. Number of nodes: {}", path.size());

            for (Pair<Node, Double> pair : path) {
                Node node = pair.first();
                double dist = pair.second();

                if ("scooter".equals(node.getTypename())) {
                    if (node.getChargeLevel() < criticalChargeValue) {
                        boolean isBatteryEnough = decreaseBatteryQuantity(node);

                        if (!isBatteryEnough) {
                            reloadBatteryQuantity();
                            break;
                        }

                        chargeScooterTo100(node);
                        logger.info("{} id: {} | distance to next station: {} | coords: {}", node.getTypename(), node.getId(), dist, node.toString());
                    }
                }
            }

            double avgPercentageVal = areaMapper.getAvgChargingPercentageInfo();
            reloadBatteryQuantity();
            logger.info("Average global area percentage: [{}%]\n", avgPercentageVal);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Pair<Boolean, List<Pair<Node, Double>>> goNextStation() {
        if (!it.hasNext()) {
            return new Pair<>(false, List.of());
        }
        List<Pair<Node, Double>> path = it.nextStep();
        return new Pair<>(true, path);
    }

    public void increaseBatteryQuantity() {
        batteryQuantity = Math.min(batteryQuantity + 1, maxBatteryLimit);
    }
}