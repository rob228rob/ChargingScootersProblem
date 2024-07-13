package org.example.DataStructures.graph;

import org.example.DataStructures.node.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    List<Node> nodeList;

    public Graph() {
        nodeList = new ArrayList<Node>();
    }

    public Graph(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public void addNodeWithLinks(Node node) {
        nodeList.add(node);

        for (Node n : nodeList) {
            n.addEdge(node);
        }
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
