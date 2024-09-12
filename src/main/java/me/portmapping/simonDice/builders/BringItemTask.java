package me.portmapping.simonDice.builders;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
public class BringItemTask extends SimonTask {
    private Material itemToBring;

    public BringItemTask(Material material) {
        super();
        this.itemToBring = material;
    }

    @Override
    public String getDescription(){
        return "Dame un/a " + itemToBring.name();
    }



}
