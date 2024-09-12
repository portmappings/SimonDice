package me.portmapping.simondice.listeners;

import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.Main;
import me.portmapping.simondice.game.tasks.BringItemTask;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        //Evitamos errores con jugadores cuyas UUIDS ya no seran reconocidas en el HashMap de Game
        Game game = Main.getInstance().getGame();
        Player player = event.getPlayer();
        if(!game.getPlayers().containsKey(player.getUniqueId())) return;

        game.getPlayers().remove(player);

    }
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event){
        Game game = Main.getInstance().getGame();
        Entity entity = event.getRightClicked();
        if(entity == null) return;

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        ItemStack item = playerInventory.getItemInMainHand();

        if(!(game.getSimonTask() instanceof BringItemTask bringItemTask)){
            return;
        }
        if(item == null || playerInventory.getItemInMainHand().getType() == Material.AIR) return;

        if(bringItemTask.getItemToBring() == item.getType()){
            game.completeTask(player);
        }
    }
}
