package me.portmapping.simonDice.runnables;

import me.portmapping.simonDice.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {

    private Game game;
    public GameRunnable(Game game){
        this.game = game;
    }
    @Override
    public void run() {
        if(game.getSimonTask().getTimeToComplete() <= 0){
            game.updateTask();
        }

        game.getSimonTask().setTimeToComplete(game.getSimonTask().getTimeToComplete()-1);
    }
}
