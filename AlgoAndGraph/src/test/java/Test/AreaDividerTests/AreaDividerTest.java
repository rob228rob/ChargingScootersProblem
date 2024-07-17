package Test.AreaDividerTests;

import com.example.DataStructures.AreaDivider.AreaDivider;
import com.example.DataStructures.Node.Node.Node;
import com.example.DataStructures.Pair.Pair;
import com.example.NodeGenerator.NodeGenerator;
import com.example.NodeGenerator.RandomNodeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AreaDividerTest {

    @Test
    void divideAndMapScootersTest() {
        var pair1 = new Pair<>(1.0, 1.0);
        var pair2 = new Pair<>(2.0, 2.0);
        var gridSize = 3;
        NodeGenerator mockGenerator = Mockito.mock(RandomNodeGenerator.class);

        AreaDivider areaDivider = new AreaDivider(pair1, pair2);
        List<Node> nodes = (new RandomNodeGenerator(pair1.first(), pair2.first(),pair1.second(), pair2.second())).generateScooterList(100);

        var resultMap = areaDivider.divideAndMapScooters(nodes, gridSize);

        Assertions.assertEquals(gridSize*gridSize,resultMap.size());
    }
}