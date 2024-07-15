package org.example.AlgoGraphs.DataStructures.Node.Scooter;

import lombok.*;
import org.example.AlgoGraphs.DataStructures.Node.Node.Node;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScooterNode extends Node {
    private int chargeLevel;

    private boolean isAvailable;

    public ScooterNode(Long id, double lat, double lon, int chargeLevel, boolean isAvailable) {
        super(id, lat, lon);
        this.chargeLevel = chargeLevel;
        this.isAvailable = isAvailable;
    }


    @Override
    public int getChargeLevel() {
        return chargeLevel;
    }

    @Override
    public void setChargeLevel(int chargeLevel) {
        this.chargeLevel = chargeLevel;
    }

    @Override
    public String getTypename() {
        return "ScooterNode";
    }
}
