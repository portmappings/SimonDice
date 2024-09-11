package me.portmapping.simonDice.listeners;

import me.portmapping.simonDice.Main;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class EntityListener implements Listener {
    public void onEntityHurt(EntityDamageEvent event){
        if(event.getEntity().getUniqueId() == Main.getInstance().getGame().getSimonEntityUUID()){
            event.setCancelled(true);
        }


    }
    public void onEntityMove(EntityDamageEvent event){
        if(event.getEntity().getUniqueId() == Main.getInstance().getGame().getSimonEntityUUID()){
            event.setCancelled(true);
        }
    }
}
