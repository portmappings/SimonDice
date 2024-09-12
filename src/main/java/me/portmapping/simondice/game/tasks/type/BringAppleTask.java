package me.portmapping.simondice.game.tasks.type;

import me.portmapping.simondice.game.tasks.BringItemTask;
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
