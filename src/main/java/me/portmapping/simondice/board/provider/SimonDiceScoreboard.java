package me.portmapping.simondice.board.provider;

import com.google.common.collect.Lists;
import me.portmapping.simondice.game.EliminationType;
import me.portmapping.simondice.game.Game;
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

        if(game.getWinner() != null && game.getWinner().equalsIgnoreCase(player.getName())){
            scoreboardList.add("&aYOU WIN!");
            return scoreboardList;
        }

        int playersCompleted = 0;
        //Podria usar otra lista dentro de "Game" para poner los que completaron la tarea y usar .size() pero no estoy seguro de cual tiene mejor rendimiento.
        Map<UUID,Boolean> playersCopy = game.getPlayers();
        for(Map.Entry<UUID,Boolean> entry : playersCopy.entrySet()){
            if(entry.getValue()){
                playersCompleted++;
            }
        }

        String completado = "";
        if(game.getPlayers().get(player.getUniqueId())){
            completado = CC.GREEN+"Si";
        }else {
            completado = CC.RED+"No";
        }

        scoreboardList.add("Completado: "+ completado);

        if(game.getEliminationType() == EliminationType.NOT_MADE_IN_TIME){
            scoreboardList.add("Jugadores Finalizados: "+ playersCompleted + "/" + game.getPlayers().size());
        }else{
            if(game.getPlayers().size()==1){
                scoreboardList.add("Jugadores Finalizados: "+ playersCompleted + "/" + game.getPlayers().size());
            }else{
                scoreboardList.add("Jugadores Finalizados: "+ playersCompleted + "/" + (game.getPlayers().size()-1));
            }
        }

        if(game.getEliminationType() == EliminationType.NOT_MADE_IN_TIME){
            scoreboardList.add("Tiempo: "+ game.getTimeToComplete());
        }

        return scoreboardList;
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {

    }
}
