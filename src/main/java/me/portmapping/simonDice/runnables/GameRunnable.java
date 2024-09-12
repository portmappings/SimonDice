package me.portmapping.simonDice.runnables;

import me.portmapping.simonDice.Game;
import me.portmapping.simonDice.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {


    public GameRunnable(){
        this.runTaskTimer(Main.getInstance(),0L,20L);
    }
    @Override
    public void run() {
        //Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("AAA"));
        Game game = Main.getInstance().getGame();
        if(!game.isRunning()) return;

        if(game.getTimeToComplete() <= 0){
            game.updateTask();
        }


        game.setTimeToComplete(game.getTimeToComplete()-1);
    }
}
