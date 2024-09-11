package me.portmapping.simonDice.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {
    public void onEntityInteract(PlayerInteractEntityEvent event){
        Entity entity = event.getRightClicked();
        if(entity == null) return;

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        if(playerInventory.getItemInMainHand() == null || playerInventory.getItemInMainHand().getType() == Material.AIR) return;



    }
}
