package Test.GraphTests.Graph.GraphBuilder;

import Test.GraphTests.Graph.Graph;
import com.example.DataStructures.Node.Node.Node;

import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {

    private List<Node> nodeList;

    public GraphBuilder() {
        nodeList = new ArrayList<Node>();
    }

    public GraphBuilder(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public Graph buildGraphFromNodes() {

        Graph graph = new Graph();

        for (Node node : nodeList) {
            graph.addNodeWithLinks(node);
        }

        return graph;
    }

    public Graph buildGraphFromNodes(List<Node> nodes) {
        Graph graph = new Graph(nodes);

        for (Node node : nodes) {
            graph.addNodeWithLinks(node);
        }

        return graph;
    }

    public Graph build()
    {
        return new Graph(nodeList);
    }
}
