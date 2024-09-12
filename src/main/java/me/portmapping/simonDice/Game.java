package me.portmapping.simonDice;

import lombok.Getter;
import lombok.Setter;
import me.portmapping.simonDice.builders.SimonTask;
import me.portmapping.simonDice.runnables.GameRunnable;
import me.portmapping.simonDice.utils.CC;
import me.portmapping.simonDice.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
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
    private GameRunnable runnable;

    private boolean running = false;
    public Game(){
    }

    public void completeTask(Player player){
        if(!this.players.containsKey(player.getUniqueId())){
            return;
        }
        if(this.players.get(player.getUniqueId())){
            player.sendMessage(CC.RED+"Ya completaste esta tarea.");
            return;
        }

        player.sendTitle(CC.t("&aDone"),"",5,30,5);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1F,1F);
        this.players.put(player.getUniqueId(),true);

    }

    public void updateTask(){
        this.simonTask = Utils.getRandomSimonTask();
        this.timeToComplete = this.simonTask.getTimeToComplete();
        this.broadcastTitle(CC.GREEN+"Simon Dice", this.simonTask.getDescription());
        this.broadcastSound(Sound.ITEM_GOAT_HORN_SOUND_1);

        for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
            this.players.put(entry.getKey(),false);
        }

    }
    public void start(UUID villagerUuid){

        this.simonEntityUUID = villagerUuid;
        this.simonTask = Utils.getRandomSimonTask();
        Bukkit.getOnlinePlayers().forEach(player -> players.put(player.getUniqueId(),false));
        this.runnable = new GameRunnable();
        this.broadcastTitle("&aSimon Dice Ha Empezado!");
        this.running = true;
    }
    public void stop(){
        this.players.clear();
        this.broadcastTitle("Simon Dice Ha Acabado!");

        //Se ejecuta para matar a la entidad de aldeano que se creo al inicio del juego
        Entity entity = Bukkit.getEntity(this.simonEntityUUID);
        if(entity == null) return;
        entity.remove();

        this.running = false;
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

            player.sendTitle(CC.t(title),CC.t(subtitle), 5, 75, 5);
        }
    }

    public void broadcastSound(Sound sound){
        for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.playSound(player.getLocation(),sound,1f,1f);
        }
    }
    public void broadcastTitle(String title){
        for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendTitle(CC.t(title),"", 5, 75, 5);
        }
    }



}
