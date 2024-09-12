package me.portmapping.simondice.builders.type;

import me.portmapping.simondice.builders.BringItemTask;
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
