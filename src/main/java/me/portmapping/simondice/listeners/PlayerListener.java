package me.portmapping.simondice.listeners;

import com.google.common.collect.Sets;
import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.Main;
import me.portmapping.simondice.game.tasks.BringItemTask;
import me.portmapping.simondice.game.tasks.type.JumpTask;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.UUID;

public class PlayerListener implements Listener {

    private Set<UUID> prevPlayersOnGround = Sets.newHashSet();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        //Evitamos errores con jugadores cuyas UUIDS ya no seran reconocidas en el HashMap de Game
        Game game = Main.getInstance().getGame();
        Player player = event.getPlayer();
        if(!game.getPlayers().containsKey(player.getUniqueId())) return;

        game.getPlayers().remove(player);

    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        //Evitamos errores con jugadores cuyas UUIDS ya no seran reconocidas en el HashMap de Game
        Game game = Main.getInstance().getGame();
        if(game.getSimonTask() instanceof JumpTask){
            game.completeTask(event.getPlayer());
        }


    }


    @EventHandler
    public void onPlayerJump(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Game game = Main.getInstance().getGame();
        if(game.getSimonTask() instanceof JumpTask jumpTask){
            if (player.getVelocity().getY() > 0) {
                double jumpVelocity = (double) 0.42F;
                if (player.hasPotionEffect(PotionEffectType.JUMP_BOOST)) {
                    jumpVelocity += (double) ((float) (player.getPotionEffect(PotionEffectType.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
                }
                if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                    if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) {
                        game.completeTask(player);
                    }
                }
            }
            if (player.isOnGround()) {
                prevPlayersOnGround.add(player.getUniqueId());
            } else {
                prevPlayersOnGround.remove(player.getUniqueId());
            }
        }

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
