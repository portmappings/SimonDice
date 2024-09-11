package me.portmapping.simonDice.board;

import lombok.Getter;
import me.portmapping.simonDice.Main;
import me.portmapping.simonDice.board.event.BoardCreateEvent;
import me.portmapping.simonDice.board.listener.AetherJoinListener;
import me.portmapping.simonDice.board.scoreboard.Board;
import me.portmapping.simonDice.board.scoreboard.BoardAdapter;
import me.portmapping.simonDice.board.scoreboard.BoardEntry;
import me.portmapping.simonDice.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class Aether {

	private final Main plugin;
	private final AetherOptions options;
	private BoardAdapter adapter;


	public Aether(Main plugin, BoardAdapter adapter, AetherOptions options) {
		this.options = options;
		this.plugin = plugin;


		Bukkit.getServer().getPluginManager().registerEvents(new AetherJoinListener(),plugin);
		setAdapter(adapter);
		run();
	}

	public Aether(Main plugin, BoardAdapter adapter) {
		this(plugin, adapter, AetherOptions.defaultOptions());
	}

	private void run() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (adapter == null) {
					return;
				}



				for (Player player : Bukkit.getOnlinePlayers()) {

					Board board = Board.getByPlayer(player);
					if (board != null) {

						List<String> scores = adapter.getScoreboard(player, board, board.getCooldowns());
						List<String> translatedScores = new ArrayList<>();
						if (scores == null) {
							if (!board.getEntries().isEmpty()) {
								for (BoardEntry boardEntry : board.getEntries()) {
									boardEntry.remove();
								}
								board.getEntries().clear();
							}
							continue;
						}

						for (String line : scores) {
							translatedScores.add(CC.translate(line));
						}
						if (!options.scoreDirectionDown()) {
							Collections.reverse(scores);
						}

						Scoreboard scoreboard = board.getScoreboard();
						Objective objective = board.getObjective();
						if (!(objective.getDisplayName().equals(adapter.getTitle(player)))) {
							objective.setDisplayName(CC.translate(adapter.getTitle(player)));
						}

						outer:
						for (int i = 0; i < scores.size(); i++) {
							String text = scores.get(i);
							int position;
							if (options.scoreDirectionDown()) {
								position = 15 - i;
							} else {
								position = i + 1;
							}

							Iterator<BoardEntry> iterator = new ArrayList<>(board.getEntries()).iterator();
							while (iterator.hasNext()) {
								BoardEntry boardEntry = iterator.next();
								Score score = objective.getScore(boardEntry.getKey());
								if (score != null && boardEntry.getText().equals(CC.translate(text)) && score.getScore() == position) {
									continue outer;
								}
							}

							int positionToSearch = options.scoreDirectionDown() ? 15 - position : position - 1;

							iterator = new ArrayList<>(board.getEntries()).iterator();
							while (iterator.hasNext()) {
								BoardEntry boardEntry = iterator.next();
								int entryPosition = scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(boardEntry.getKey()).getScore();
								if (!options.scoreDirectionDown() && entryPosition > scores.size()) {
									iterator.remove();
									// boardEntry.remove(); // Comment this line if the CME continues...
								}
							}

							BoardEntry entry = board.getByPosition(positionToSearch);
							if (entry == null) {
								new BoardEntry(board, text).send(position);
							} else {
								entry.setText(text).setup().send(position);
							}

							List<BoardEntry> toRemove = new ArrayList<>();
							if (board.getEntries().size() > scores.size()) {
								for (BoardEntry boardEntry : board.getEntries()) {
									if ((!translatedScores.contains(boardEntry.getText())) || Collections.frequency(board.getBoardEntriesFormatted(), boardEntry.getText()) > 1) {
										toRemove.add(boardEntry);
									}
								}
							}
							for (BoardEntry boardEntry : toRemove) {
								board.getEntries().remove(boardEntry);
								boardEntry.remove();
							}
						}



						adapter.onScoreboardCreate(player, scoreboard);
						player.setScoreboard(scoreboard);
					}
				}
			}
		}.runTaskTimerAsynchronously(plugin, 20L, 20L);
	}

	public void setAdapter(BoardAdapter adapter) {
		this.adapter = adapter;
		for (Player player : Bukkit.getOnlinePlayers()) {
			Board board = Board.getByPlayer(player);
			if (board != null) {
				Board.getBoards().remove(board);
			}
			Bukkit.getPluginManager().callEvent(new BoardCreateEvent(new Board(player, this, options), player));
		}
	}
}
