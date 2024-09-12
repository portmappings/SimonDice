package me.portmapping.simondice.builders;

import lombok.Getter;
import org.bukkit.Material;

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
