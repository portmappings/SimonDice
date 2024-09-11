package me.portmapping.simonDice.listeners;

import me.portmapping.simonDice.Game;
import me.portmapping.simonDice.Main;
import me.portmapping.simonDice.builders.BringItemTask;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {
    private final Game game = Main.getInstance().getGame();
    public void onEntityInteract(PlayerInteractEntityEvent event){
        Entity entity = event.getRightClicked();
        if(entity == null) return;

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        if(game.getSimonTask() instanceof BringItemTask){

        }
        if(playerInventory.getItemInMainHand() == null || playerInventory.getItemInMainHand().getType() == Material.AIR) return;



    }
}
