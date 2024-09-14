package me.portmapping.simondice.game.runnables;

import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.Main;
import me.portmapping.simondice.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {


    public GameRunnable(){
        this.runTaskTimer(Main.getInstance(),0L,20L);
    }
    @Override
    public void run() {
        Game game = Main.getInstance().getGame();
        if(!game.isRunning()) return;

        switch (game.getEliminationType()){
            case LAST_TO_COMPLETE -> {
                if(game.getTimeToComplete() <= 0){
                    game.updateTask();
                }
                if(game.getSimonTask() == null){
                    game.setTimeToComplete(game.getTimeToComplete()-1);
                }
                break;
            }
            case NOT_MADE_IN_TIME -> {
                if(game.getTimeToComplete() <= 0){
                    game.updateTask();
                }
                game.setTimeToComplete(game.getTimeToComplete()-1);
                break;
            }
        }

        if(game.getWinner() != null){
            Player player = Bukkit.getPlayer(game.getWinner());
            if(player == null) return;

            switch (game.getTimeToComplete()){
                case 6:
                    game.broadcastTitle(CC.GREEN+player.getName()," ha ganado!");
                    game.broadcastChat(CC.GREEN+player.getName() + " ha ganado el reto de Simon Dice!");
                    break;
                case 0:
                    game.stop();
                default:
                    Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK_ROCKET);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    fwm.setPower(2);
                    fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
                    fw.setFireworkMeta(fwm);
                    break;

            }
        }


    }
}
