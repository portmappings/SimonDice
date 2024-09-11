package me.portmapping.simonDice.board.listener;


import me.portmapping.simonDice.Main;
import me.portmapping.simonDice.board.Aether;
import me.portmapping.simonDice.board.event.BoardCreateEvent;
import me.portmapping.simonDice.board.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AetherJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (Board.getByPlayer(event.getPlayer()) == null) {
            Aether board = Main.getInstance().getAether();
            Bukkit.getPluginManager().callEvent(new BoardCreateEvent(new Board(event.getPlayer(), board, board.getOptions()), event.getPlayer()));
        }
    }
}
