package me.portmapping.simondice.board.listener;


import me.portmapping.simondice.Main;
import me.portmapping.simondice.board.Aether;
import me.portmapping.simondice.board.event.BoardCreateEvent;
import me.portmapping.simondice.board.scoreboard.Board;
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
