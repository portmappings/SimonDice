package me.portmapping.simondice.commands;

import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.Main;
import me.portmapping.simondice.utils.CC;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.hasPermission("simondice.admin")){
            commandSender.sendMessage(CC.t("&cNo permission."));
            return false;
        }

        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(CC.translate("&cSolo para jugadores."));
            return false;
        }


        Game game = Main.getInstance().getGame();
        if(!game.isRunning()){
            World world = player.getWorld();
            Villager villager = (Villager) world.spawnEntity(player.getLocation(), EntityType.VILLAGER);
            villager.setCustomNameVisible(true);
            villager.setCustomName(CC.t("&a&lSIMON"));
            villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,Integer.MAX_VALUE,100));
            villager.setAI(false);
            game.start(villager.getUniqueId());
        }else{
            player.sendMessage(CC.RED+"Ya hay un juego en curso. '/stop' para acabarlo");
        }





        return false;
    }
}
