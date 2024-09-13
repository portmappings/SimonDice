package me.portmapping.simondice.game.runnables;

import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {


    public GameRunnable(){
        this.runTaskTimer(Main.getInstance(),0L,20L);
    }
    @Override
    public void run() {
        Game game = Main.getInstance().getGame();
        if(!game.isRunning()) this.cancel();

        switch (game.getEliminationType()){
            case LAST_TO_COMPLETE -> {
                break;
            }
            case NOT_MADE_IN_TIME -> {
                if(game.getTimeToComplete() <= 0){
                    game.updateTask();
                    return;
                }
                game.setTimeToComplete(game.getTimeToComplete()-1);
                break;
            }
        }


    }
}
