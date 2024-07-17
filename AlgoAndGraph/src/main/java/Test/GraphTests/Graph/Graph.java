package Test.GraphTests.Graph;

import Test.GraphTests.Graph.GraphBuilder.GraphBuilder;
import lombok.Getter;
import lombok.Setter;
import com.example.DataStructures.Node.Node.Node;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

    public void removeNode(Node node) {
        nodeList.remove(node);

        for (Node n : nodeList) {
            n.getEdges().remove(node);
        }
    }

    static public GraphBuilder builder(List<Node> nodes)
    {
        return new GraphBuilder(nodes);
    }

    static public GraphBuilder builder()
    {
        return new GraphBuilder();
    }
}
