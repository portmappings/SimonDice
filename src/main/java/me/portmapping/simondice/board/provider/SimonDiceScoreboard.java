package me.portmapping.simondice.board.provider;

import com.google.common.collect.Lists;
import me.portmapping.simondice.Game;
import me.portmapping.simondice.Main;
import me.portmapping.simondice.board.scoreboard.Board;
import me.portmapping.simondice.board.scoreboard.BoardAdapter;
import me.portmapping.simondice.board.scoreboard.cooldown.BoardCooldown;
import me.portmapping.simondice.utils.CC;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SimonDiceScoreboard implements BoardAdapter {
    @Override
    public String getTitle(Player player) {
        return CC.B_GREEN+"SIMON DICE";
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> set) {

        List<String> scoreboardList = Lists.newArrayList();
        Game game = Main.getInstance().getGame();

        if(!game.getPlayers().containsKey(player.getUniqueId())){
            scoreboardList.add("&cYou are not in the game :(");
            return scoreboardList;
        }

        int playersCompleted = 0;
        //Podria usar otra lista dentro de "Game" para poner los ganadores y usar .size() pero no estoy seguro de cual tiene mejor rendimiento.
        for(Map.Entry<UUID,Boolean> entry : game.getPlayers().entrySet()){
            if(entry.getValue()==true){
                playersCompleted++;
            }
        }

        String completado = "";
        if(game.getPlayers().get(player.getUniqueId()) == true){
            completado = CC.GREEN+"Si";
        }else {
            completado = CC.RED+"No";
        }

        scoreboardList.add("Completado: "+ completado);
        scoreboardList.add("Jugadores Finalizados: "+ playersCompleted + game.getPlayers().size());
        scoreboardList.add("");
        scoreboardList.add("Tiempo: "+ game.getTimeToComplete());

        return scoreboardList;
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {

    }
}
