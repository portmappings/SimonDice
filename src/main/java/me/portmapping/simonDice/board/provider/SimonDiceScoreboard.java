package me.portmapping.simonDice.board.provider;

import com.google.common.collect.Lists;
import me.portmapping.simonDice.Main;
import me.portmapping.simonDice.board.scoreboard.Board;
import me.portmapping.simonDice.board.scoreboard.BoardAdapter;
import me.portmapping.simonDice.board.scoreboard.cooldown.BoardCooldown;
import me.portmapping.simonDice.utils.CC;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Set;

public class SimonDiceScoreboard implements BoardAdapter {
    @Override
    public String getTitle(Player player) {
        return CC.B_GREEN+"SIMON DICE";
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> set) {

        List<String> scoreboardList = Lists.newArrayList();
        scoreboardList.add("Time to Complete: " + Main.getInstance().getGame().getTimeToComplete()+"s");

        return scoreboardList;
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {

    }
}
