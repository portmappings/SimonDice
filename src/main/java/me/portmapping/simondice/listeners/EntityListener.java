package me.portmapping.simondice.listeners;

import me.portmapping.simondice.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityListener implements Listener {
    @EventHandler
    public void onEntityHurt(EntityDamageEvent event){
        if(event.getEntity().getUniqueId() == Main.getInstance().getGame().getSimonEntityUUID()){
            event.setCancelled(true);
        }


    }

    @EventHandler
    public void onEntityMove(EntityDamageEvent event){
        if(event.getEntity().getUniqueId() == Main.getInstance().getGame().getSimonEntityUUID()){
            event.setCancelled(true);
        }
    }
}
