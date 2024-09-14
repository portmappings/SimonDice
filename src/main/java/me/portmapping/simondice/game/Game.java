package me.portmapping.simondice.game;

import lombok.Getter;
import lombok.Setter;
import me.portmapping.simondice.Main;
import me.portmapping.simondice.game.tasks.SimonTask;
import me.portmapping.simondice.game.runnables.GameRunnable;
import me.portmapping.simondice.utils.CC;
import me.portmapping.simondice.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Game {


    private Map<UUID, Boolean> players = new ConcurrentHashMap<>();
    private EliminationType eliminationType;
    private String winner;
    private UUID simonEntityUUID;
    private SimonTask simonTask;
    private int timeToComplete = 3;
    private GameRunnable runnable;

    private boolean running = false;
    public Game(){
        EliminationType type = EliminationType.valueOf(Main.getInstance().getSettings().getConfig().getString("ELIMINATION-TYPE"));
        if(type == null){
            Bukkit.getLogger().severe("The elimination type you provided was invalid.");
            Bukkit.shutdown();
            return;
        }
        this.eliminationType = type;

        this.runnable = new GameRunnable();
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

        if(this.getEliminationType() == EliminationType.LAST_TO_COMPLETE){
            int completed = Math.toIntExact(this.players.values().stream().filter(((bool -> bool))).count());
            int incompleted = Math.toIntExact(this.players.values().stream().filter((bool -> !bool)).count());
            UUID lastToComplete = null;
            if(incompleted == 1){
                for(Map.Entry<UUID, Boolean> entry : this.players.entrySet()){
                    if(!entry.getValue()){
                        lastToComplete = entry.getKey();
                    }
                }
            }


            if(completed == this.getPlayers().size()-1){
                Player eliminatedPlayer = Bukkit.getPlayer(lastToComplete);

                //Nunca deberia ser null, pero solo por si acaso
                if(eliminatedPlayer != null) this.eliminatePlayer(eliminatedPlayer);
            }

            //Si queda uno en la lista significa que gano
            //Para spawnear fireworks lo manejare en el GameRunnable para poder mandar uno cada segundo
            if(this.getPlayers().size()==1){
                this.handleWin();
                return;
            }
        }





    }

    public void updateTask(){
        //Esto es en el caso de que ningun jugador complete la tarea, se repite nuevamente


        Map<UUID,Boolean> playersCopy = this.players;
        for(Map.Entry<UUID, Boolean> entry : playersCopy.entrySet()){
            if(this.getSimonTask() == null) continue;
            if(entry.getValue()){
                this.players.put(entry.getKey(),false);
            }else{
                Player eliminatedPlayer = Bukkit.getPlayer(entry.getKey());
                if(this.getEliminationType() == EliminationType.NOT_MADE_IN_TIME){
                    this.eliminatePlayer(eliminatedPlayer);
                }
            }
        }

        if(this.players.size() == 1){
            this.handleWin();
            return;
        }



        this.simonTask = Utils.getRandomSimonTask();
        this.timeToComplete = this.simonTask.getTimeToComplete();
        this.broadcastTitle(CC.GREEN+"Simon Dice", this.simonTask.getDescription());
        this.broadcastSound(Sound.ITEM_GOAT_HORN_SOUND_1);
    }

    public void handleWin(){
        UUID uniqueUUID = null;
        for (Map.Entry<UUID, Boolean> entry : this.getPlayers().entrySet()) {
            uniqueUUID = entry.getKey();
            break; // Exit the loop after the first entry
        }
        Player player = Bukkit.getPlayer(uniqueUUID);

        if(player == null) return;

        this.simonTask = null;
        this.timeToComplete = 6;
        this.winner = player.getName();
        this.broadcastTitle(CC.GREEN+player.getName()," ha ganado!");
        this.broadcastChat(CC.GREEN+player.getName() + " ha ganado el reto de Simon Dice!");
        player.playSound(player.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1F,1F);
    }
    public void eliminatePlayer(Player player){
        this.broadcastChat(CC.RED+player.getDisplayName()+" ha sido eliminado.");
        this.broadcastSound(Sound.ENTITY_GENERIC_EXPLODE);
        this.players.remove(player.getUniqueId());


    }
    public void start(UUID villagerUuid){
        this.simonEntityUUID = villagerUuid;
        Bukkit.getOnlinePlayers().forEach(player -> players.put(player.getUniqueId(),false));
        this.broadcastTitle("&aSimon Dice Ha Empezado!");
        this.running = true;
    }
    public void stop(){
        this.broadcastTitle(CC.RED+"Simon Dice", "el juego ha acabado");
        this.players.clear();

        //Se ejecuta para matar a la entidad de aldeano que se creo al inicio del juego
        Entity entity = Bukkit.getEntity(this.simonEntityUUID);
        if(entity == null) return;
        entity.remove();

        this.simonTask = null;
        this.winner = null;
        this.running = false;
        this.timeToComplete = 6;
    }

    public void broadcastChat(String text){
        Map<UUID,Boolean> playersCopy = this.players;
        for(Map.Entry<UUID, Boolean> entry : playersCopy.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendMessage(CC.t(text));
        }
    }
    public void broadcastTitle(String title, String subtitle){
        Map<UUID,Boolean> playersCopy = this.players;
        for(Map.Entry<UUID, Boolean> entry : playersCopy.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendTitle(CC.t(title),CC.t(subtitle), 5, 75, 5);
        }
    }

    public void broadcastSound(Sound sound){
        Map<UUID,Boolean> playersCopy = this.players;
        for(Map.Entry<UUID, Boolean> entry : playersCopy.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.playSound(player.getLocation(),sound,1f,1f);
        }
    }
    public void broadcastTitle(String title){
        Map<UUID,Boolean> playersCopy = this.players;
        for(Map.Entry<UUID, Boolean> entry : playersCopy.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null) continue;

            player.sendTitle(CC.t(title),"", 5, 75, 5);
        }
    }



}
