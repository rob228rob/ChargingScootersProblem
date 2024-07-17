package com.example.TSP;

import com.example.DataStructures.Node.Node.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSPGreedy implements TSP {

    @Override
    public List<Node> solve(List<Node> nodes) {
        if (nodes.isEmpty()) {
            return Collections.emptyList();
        }

        List<Node> path = new ArrayList<>();
        boolean[] visited = new boolean[nodes.size()];
        Node current = nodes.get(0);

        path.add(current);
        visited[0] = true;

        for (int count = 1; count < nodes.size(); ++count) {
            double minDist = Double.MAX_VALUE;
            Node nextNode = null;
            int nextIndex = -1;

            for (int i = 0; i < nodes.size(); ++i) {
                if (!visited[i] && current.distanceTo(nodes.get(i)) < minDist) {
                    minDist = current.distanceTo(nodes.get(i));
                    nextNode = nodes.get(i);
                    nextIndex = i;
                }
            }

            if (current.getTypename().equals(nextNode.getTypename())
                    && current.getTypename().equals("StationNode")) {
                current.setNextNode(nextNode);
                nextNode.setPreviousNode(current);
            }

            path.add(nextNode);
            visited[nextIndex] = true;
            current = nextNode;
        }

        if (path.get(path.size() - 1).getTypename().equals(path.get(0).getTypename())
                && path.get(0).getTypename().equals("StationNode")) {
            path.get(path.size() - 1).setNextNode(path.get(0));
            path.get(0).setPreviousNode(path.get(path.size() - 1));
        }

        return path;
    }
}