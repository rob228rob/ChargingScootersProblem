package Test.GraphTests.Graph;

import com.example.DataStructures.Node.Node.Node;
import com.example.DataStructures.Node.Scooter.ScooterNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class GraphTest {

    @Test
    void addNodeToGraphTest() {
        Graph graph = new Graph();
        int graphSize = 10;

        for (int i = 0; i < graphSize; i++) {
            Node node = Mockito.mock(ScooterNode.class);
            graph.addNode(node);
        }

        List<Node> nodeList = graph.getNodeList();
        Assertions.assertEquals(graphSize, nodeList.size());
    }

    @Test
    void addNodeWithLinksTest() {
        Graph graph = new Graph();

        int graphSize = 10;
        for (int i = 0; i < graphSize; i++) {
            Node node = Mockito.mock(ScooterNode.class);
            graph.addNodeWithLinks(node);
        }

        Assertions.assertEquals(graphSize, graph.getNodeList().size());
    }

    @Test
    void removeNode() {
        Graph graph = new Graph();
        int graphSize = 10;
        for (int i = 0; i < graphSize; i++) {
            Node node = Mockito.mock(ScooterNode.class);
            graph.addNode(node);
        }

        graph.removeNode(graph.getNodeList().get(0));
        Assertions.assertEquals(graphSize - 1, graph.getNodeList().size());

        graph.removeNode(graph.getNodeList().get(0));
        Assertions.assertEquals(graphSize - 2, graph.getNodeList().size());
    }
}