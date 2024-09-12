package me.portmapping.simondice.commands;

import me.portmapping.simondice.Main;
import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.utils.CC;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SimonDiceCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!commandSender.hasPermission("simondice.admin")){
            commandSender.sendMessage(CC.t("&cNo permission."));
            return false;
        }
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(CC.translate("&cSolo para jugadores."));
            return false;
        }
        Game game = Main.getInstance().getGame();

        if(args.length == 1){
            switch (args[0]){
                case "start" -> {
                    if(!game.isRunning()){
                        World world = player.getWorld();
                        Villager villager = (Villager) world.spawnEntity(player.getLocation(), EntityType.VILLAGER);
                        villager.setCustomNameVisible(true);
                        villager.setCustomName(CC.t("&a&lSIMON"));
                        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,Integer.MAX_VALUE,100));
                        villager.setAI(false);
                        game.start(villager.getUniqueId());
                    }else{
                        player.sendMessage(CC.RED+"Ya hay un juego en curso. '/stop' para acabarlo.");
                    }
                    break;
                }
                case "stop" -> {
                    if(game.isRunning()){
                        game.stop();
                        player.sendMessage(CC.RED+"Acabo el juego.");
                    }else{
                        player.sendMessage(CC.RED+"No hay ningun juego en curso. '/start' para iniciar uno.");
                    }
                }


            }
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args){
        List<String> toReturn = new ArrayList<>();

            toReturn.add("stop");
            toReturn.add("start");


        return toReturn;
    }
}
