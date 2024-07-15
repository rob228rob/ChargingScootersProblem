package org.example.AlgoGraphs.DataStructures.AreaDivider;

import lombok.AllArgsConstructor;
import org.example.AlgoGraphs.NodeGenerator.NodeGenerator;
import org.example.AlgoGraphs.DataStructures.Pair.Pair;
import org.example.AlgoGraphs.DataStructures.Area.Area;
import org.example.AlgoGraphs.DataStructures.Node.Node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class AreaDivider {

    private Pair<Double, Double> leftDownCorner;

    private Pair<Double, Double> rightUpperCorner;

    private NodeGenerator nodeGenerator;

    public Map<Area, List<Node>> divideAndMapScooters(List<Node> scooters)
    {
        return divideAndMapScooters(scooters, 3);
    }

    public Map<Area, List<Node>> divideAndMapScooters(List<Node> scooters, int gridSize)
    {
        Double latMin = leftDownCorner.getFirst();
        Double latMax = rightUpperCorner.getSecond();
        Double lonMin = leftDownCorner.getFirst();
        Double lonMax = rightUpperCorner.getSecond();

        Map<Area, List<Node>> regionsMap = new HashMap<>();

        double latStep = (latMax - latMin) / gridSize;
        double lonStep = (lonMax - lonMin) / gridSize;

        for (Node scooter : scooters)
        {
            var latIndex = (int) ((scooter.getLatitude() - latMin) / latStep);
            var lonIndex = (int) ((scooter.getLongitude() - lonMin) / lonStep);

            Area area = new Area();
            area.setAreaId(latIndex, lonIndex);
            area.setBoundaries(latMin + latIndex * latStep,
                    lonMin + lonIndex * lonStep,
                    latMin + (latIndex + 1) * latStep,
                    lonMin + (lonIndex + 1) * lonStep,
                    gridSize);

            /* SOURCE C++ CODE:

        TODO: temporary kostyl, need to segregate contexts scooter and stations to dividing
        node *insertion_node;

        if (scooter->get_typename() == "scooter")
        {
            insertion_node = new class scooter(scooter->get_latitude(), scooter->get_longitude(), scooter->get_charge_info());
        }
        else
        {
            insertion_node = new class charging_station(scooter->get_latitude(), scooter->get_longitude());
        }

         */

            regionsMap.putIfAbsent(area, new ArrayList<>());
            regionsMap.get(area).add(scooter);

        }



        return regionsMap;
    }
}
