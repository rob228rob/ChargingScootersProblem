package org.example.DataStructures.node.scooter;

import lombok.*;
import org.example.DataStructures.node.node.Node;

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

}
