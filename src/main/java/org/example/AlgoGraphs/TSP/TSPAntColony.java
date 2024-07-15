package org.example.AlgoGraphs.TSP;

import org.example.AlgoGraphs.DataStructures.Node.Node.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TSPAntColony implements TSP {

    @Override
    public List<Node> solve(List<Node> nodes) {

        int nodesCount = nodes.size();
        if (nodesCount < 2) {
            return Collections.emptyList();
        }
        int numAnts = 20;
        int numIterations = 50;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;
        double pheromoneIntensity = 1.0;
        double[][] pheromoneMatrix = new double[nodesCount][nodesCount];
        double[][] distanceMatrix = new double[nodesCount][nodesCount];

        for (int i = 0; i < nodesCount; ++i) {
            for (int j = 0; j < nodesCount; ++j) {
                pheromoneMatrix[i][j] = 1.0;
                if (i != j) {
                    distanceMatrix[i][j] = nodes.get(i).distanceTo(nodes.get(j));
                }
            }
        }

        List<List<Node>> antTours = new ArrayList<>(numAnts);
        for (int i = 0; i < numAnts; i++) {
            antTours.add(new ArrayList<>());
        }

        Random random = new Random();

        for (int iteration = 0; iteration < numIterations; ++iteration) {
            for (int ant = 0; ant < numAnts; ++ant) {
                boolean[] visited = new boolean[nodesCount];
                List<Node> tour = new ArrayList<>(nodesCount);

                Node currentNode = nodes.get(random.nextInt(nodesCount));
                tour.add(currentNode);
                visited[nodes.indexOf(currentNode)] = true;

                for (int step = 1; step < nodesCount; ++step) {
                    Node nextNode = selectNextNode(currentNode, visited, nodes, pheromoneMatrix, distanceMatrix, alpha, beta);

                    if (nextNode == null) {
                        break;
                    }

                    tour.add(nextNode);
                    visited[nodes.indexOf(nextNode)] = true;
                    currentNode = nextNode;
                }

                if (tour.size() == nodesCount) {
                    antTours.set(ant, tour);
                } else {
                    antTours.get(ant).clear();
                }
            }

            for (int i = 0; i < nodesCount; ++i) {
                for (int j = 0; j < nodesCount; ++j) {
                    pheromoneMatrix[i][j] *= (1 - evaporationRate);
                }
            }

            for (List<Node> tour : antTours) {
                if (tour.isEmpty()) {
                    continue;
                }
                double tourDistance = calculateTourDistance(tour);
                for (int i = 0; i < tour.size() - 1; ++i) {
                    int from = nodes.indexOf(tour.get(i));
                    int to = nodes.indexOf(tour.get(i + 1));
                    pheromoneMatrix[from][to] += pheromoneIntensity / tourDistance;
                    pheromoneMatrix[to][from] += pheromoneIntensity / tourDistance;
                }
                int from = nodes.indexOf(tour.get(tour.size() - 1));
                int to = nodes.indexOf(tour.get(0));
                pheromoneMatrix[from][to] += pheromoneIntensity / tourDistance;
                pheromoneMatrix[to][from] += pheromoneIntensity / tourDistance;
            }
        }

        List<Node> bestTour = null;
        double bestDistance = Double.MAX_VALUE;

        for (List<Node> tour : antTours) {
            if (tour.isEmpty()) {
                continue;
            }
            double distance = calculateTourDistance(tour);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestTour = tour;
            }
        }

        return bestTour;
    }

    private Node selectNextNode(Node currentNode, boolean[] visited, List<Node> nodes, double[][] pheromoneMatrix, double[][] distanceMatrix, double alpha, double beta) {
        List<Node> edges = currentNode.getEdges();
        List<Double> probabilities = new ArrayList<>();
        List<Node> candidates = new ArrayList<>();

        double sumProbabilities = 0.0;

        for (Node neighbor : edges) {
            int neighborIndex = nodes.indexOf(neighbor);
            if (!visited[neighborIndex]) {
                int currentIndex = nodes.indexOf(currentNode);
                double pheromone = Math.pow(pheromoneMatrix[currentIndex][neighborIndex], alpha);
                double heuristic = Math.pow(1.0 / distanceMatrix[currentIndex][neighborIndex], beta);
                double probability = pheromone * heuristic;
                probabilities.add(probability);
                candidates.add(neighbor);
                sumProbabilities += probability;
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        double randomValue = new Random().nextDouble() * sumProbabilities;
        double cumulativeProbability = 0.0;

        for (int i = 0; i < candidates.size(); ++i) {
            cumulativeProbability += probabilities.get(i);

            if (cumulativeProbability >= randomValue) {
                return candidates.get(i);
            }
        }

        return candidates.get(candidates.size() - 1);
    }

    private double calculateTourDistance(List<Node> tour) {
        double distance = 0.0;
        for (int i = 0; i < tour.size() - 1; ++i) {
            distance += tour.get(i).distanceTo(tour.get(i + 1));
        }

        distance += tour.get(tour.size() - 1).distanceTo(tour.get(0));

        return distance;
    }
}
