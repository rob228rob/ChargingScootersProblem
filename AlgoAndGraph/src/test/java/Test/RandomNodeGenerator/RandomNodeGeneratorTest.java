package Test.RandomNodeGenerator;

import com.example.DataStructures.Node.Node.Node;
import com.example.DataStructures.Pair.Pair;
import com.example.NodeGenerator.NodeGenerator;
import com.example.NodeGenerator.RandomNodeGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomNodeGeneratorTest {

    @Test
    void generateScooterList(){
        var pair1 = new Pair<>(1.0, 1.0);
        var pair2 = new Pair<>(2.0, 2.0);
        NodeGenerator ng = new RandomNodeGenerator(pair1.first(), pair2.first(), pair1.second(), pair2.second());

        int listSize = 20;

        List<Node> nodes = ng.generateScooterList(20);

        assertEquals(listSize, nodes.size());

        for (int i = 0; i < listSize; i++) {
            assertNotNull(nodes.get(i));
        }
    }

    @Test
    void generateStationList() {
        var pair1 = new Pair<>(1.0, 1.0);
        var pair2 = new Pair<>(2.0, 2.0);
        NodeGenerator ng = new RandomNodeGenerator(pair1.first(), pair2.first(), pair1.second(), pair2.second());

        int listSize = 20;

        List<Node> nodes = ng.generateStationList(20);

        assertEquals(listSize, nodes.size());

        for (int i = 0; i < listSize; i++) {
            assertNotNull(nodes.get(i));
        }
    }

    @Test
    void generateStationsAndScootersList() {
        var pair1 = new Pair<>(1.0, 1.0);
        var pair2 = new Pair<>(2.0, 2.0);
        NodeGenerator ng = new RandomNodeGenerator(pair1.first(), pair2.first(), pair1.second(), pair2.second());

        int listSize = 30;

        List<Node> nodes = ng.generateStationsAndScootersList(10,20);

        assertEquals(listSize, nodes.size());

        for (int i = 0; i < listSize; i++) {
            assertNotNull(nodes.get(i));
        }
    }
}