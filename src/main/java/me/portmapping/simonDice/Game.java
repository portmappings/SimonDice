package me.portmapping.simonDice;

import lombok.Getter;
import lombok.Setter;
import me.portmapping.simonDice.builders.SimonTask;
import me.portmapping.simonDice.runnables.GameRunnable;
import me.portmapping.simonDice.utils.CC;
import me.portmapping.simonDice.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Game {


    private Map<UUID, Boolean> players = new HashMap<>();
    private UUID simonEntityUUID;
    private SimonTask simonTask;
    private int timeToComplete = 3;

    private boolean running;

    private GameRunnable runnable;

    public Game(){
    }

    public void updateTask(){
        this.simonTask = Utils.getRandomSimonTask();
        this.timeToComplete = this.simonTask.getTimeToComplete();
        this.broadcastTitle(CC.GREEN+"Simon Dice", this.simonTask.getDescription());

    }
    public void start(UUID villagerUuid){
        this.simonEntityUUID = villagerUuid;
        this.simonTask = Utils.getRandomSimonTask();
        Bukkit.getOnlinePlayers().forEach(player -> players.put(player.getUniqueId(),false));
        this.runnable = new GameRunnable(this);
        this.broadcastTitle("&aSimon Dice Ha Empezado!");
    }
    public void stop(){
        this.players.clear();
        this.broadcastTitle("Simon Dice Ha Acabado!");
    }

    public void broadcastChat(String text){
        for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendMessage(CC.t(text));
        }
    }
    public void broadcastTitle(String title, String subtitle){
        for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendTitle(title,subtitle, 5, 30, 5);
        }
    }
    public void broadcastTitle(String title){
        for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendTitle(title,"", 5, 30, 5);
        }
    }



}
