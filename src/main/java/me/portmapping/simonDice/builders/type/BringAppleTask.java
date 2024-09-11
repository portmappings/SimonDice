package me.portmapping.simonDice.builders.type;

import me.portmapping.simonDice.builders.BringItemTask;
import org.bukkit.Material;

public class BringAppleTask extends BringItemTask {

    public BringAppleTask(Material material) {
        super(material);
    }

    @Override
    public String getDescription(){
        return "Traeme una manzana!";
    }

    @Override
    public Material getItemToBring(){
        return Material.APPLE;
    }

}
