package me.portmapping.simonDice.commands;

import me.portmapping.simonDice.Game;
import me.portmapping.simonDice.Main;
import me.portmapping.simonDice.utils.CC;
import org.bukkit.Bukkit;
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

        World world = player.getWorld();

        Villager villager = (Villager) world.spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setCustomNameVisible(true);
        villager.setCustomName(CC.t("&a&lSIMON"));
        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,Integer.MAX_VALUE,100));


        Game game = Main.getInstance().getGame();
        game.start();



        return false;
    }
}
