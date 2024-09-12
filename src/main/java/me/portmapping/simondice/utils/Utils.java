package me.portmapping.simondice.utils;

import me.portmapping.simondice.game.tasks.BringItemTask;
import me.portmapping.simondice.game.tasks.SimonTask;
import me.portmapping.simondice.game.tasks.type.JumpTask;
import me.portmapping.simondice.game.tasks.type.SneakTask;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private static List<SimonTask> simonTask = new ArrayList<>();
    static {

        simonTask.add(new BringItemTask("manzana",Material.APPLE));
        simonTask.add(new BringItemTask("espada de diamante",Material.DIAMOND_SWORD));
        simonTask.add(new BringItemTask("tablones de jungla",Material.JUNGLE_PLANKS));
        simonTask.add(new BringItemTask("manzana dorada",Material.GOLDEN_APPLE));
        simonTask.add(new BringItemTask("cubo de leche", Material.MILK_BUCKET));
        simonTask.add(new BringItemTask("pluma",Material.FEATHER));
        simonTask.add(new BringItemTask("enderpearl", Material.ENDER_PEARL));

        //Pongo mas Jump y Sneak task para añadirle mas probabilidades de ser elegida
        simonTask.add(new JumpTask());
        simonTask.add(new JumpTask());
        simonTask.add(new SneakTask());
        simonTask.add(new JumpTask());

    }
    public static SimonTask getRandomSimonTask(){
        List<SimonTask> simonTaskList = simonTask;
        Collections.shuffle(simonTaskList);
        int random = ThreadLocalRandom.current().nextInt(simonTask.size());
        return simonTaskList.get(random);
    }
}
