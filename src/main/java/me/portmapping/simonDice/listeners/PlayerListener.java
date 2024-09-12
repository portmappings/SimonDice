package me.portmapping.simonDice.listeners;

import me.portmapping.simonDice.Game;
import me.portmapping.simonDice.Main;
import me.portmapping.simonDice.builders.BringItemTask;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {


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
