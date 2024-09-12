package me.portmapping.simondice.utils;

import me.portmapping.simondice.game.tasks.BringItemTask;
import me.portmapping.simondice.game.tasks.SimonTask;
import me.portmapping.simondice.game.tasks.type.JumpTask;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private static List<SimonTask> simonTask = new ArrayList<>();
    static {

        simonTask.add(new BringItemTask(Material.APPLE));
        simonTask.add(new BringItemTask(Material.DIAMOND_SWORD));
        simonTask.add(new BringItemTask(Material.JUNGLE_PLANKS));
        simonTask.add(new BringItemTask(Material.GOLDEN_APPLE));
        simonTask.add(new BringItemTask(Material.MILK_BUCKET));
        simonTask.add(new JumpTask());
    }
    public static SimonTask getRandomSimonTask(){
        List<SimonTask> simonTaskList = simonTask;
        Collections.shuffle(simonTaskList);
        int random = ThreadLocalRandom.current().nextInt(simonTask.size());
        return simonTaskList.get(random);
    }
}
