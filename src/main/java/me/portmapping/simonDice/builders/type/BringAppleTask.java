package me.portmapping.simonDice.builders.type;

import me.portmapping.simonDice.builders.BringItemTask;
import org.bukkit.Material;

public class BringAppleTask extends BringItemTask {

    @Override
    public String getName(){
        return "Traeme una manzana!";
    }

    @Override
    public Material getItemToBring(){
        return Material.APPLE;
    }

}
