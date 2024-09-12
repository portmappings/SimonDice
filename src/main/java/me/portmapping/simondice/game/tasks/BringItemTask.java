package me.portmapping.simondice.game.tasks;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public class BringItemTask extends SimonTask {
    private String itemName;
    private Material itemToBring;


    public BringItemTask(String itemName, Material material) {
        super();
        this.itemName = itemName;
        this.itemToBring = material;
    }

    @Override
    public String getDescription(){
        return "Dame un@ " + this.itemName;
    }



}
