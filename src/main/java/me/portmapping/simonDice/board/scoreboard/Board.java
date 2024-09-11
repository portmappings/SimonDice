package me.portmapping.simonDice.board.scoreboard;

import lombok.Getter;
import lombok.Setter;
import me.portmapping.simonDice.board.Aether;
import me.portmapping.simonDice.board.AetherOptions;

import me.portmapping.simonDice.board.scoreboard.cooldown.BoardCooldown;
import me.portmapping.simonDice.utils.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class Board {

	@Getter
	private static final Set<Board> boards = new ConcurrentSet<>();

	private final Aether aether;
	private final AetherOptions options;
	@Getter
	private final Player player;
	@Getter
	private final Set<String> keys;
	private final Set<BoardCooldown> cooldowns;
	@Getter
	private Scoreboard scoreboard;
	@Getter
	private Objective objective;
	@Getter
	@Setter
	private List<BoardEntry> entries;

	public Board(Player player, Aether aether, AetherOptions options) {
		this.player = player;
		this.aether = aether;
		this.options = options;

		this.keys = new ConcurrentSkipListSet<>();
		this.entries = new ArrayList<>();

		this.cooldowns = new ConcurrentSet<>();

		setup();
	}

	public static Board getByPlayer(Player player) {
		for (Board board : boards) {
			if (board.getPlayer().getName().equals(player.getName())) {
				return board;
			}
		}

		return null;
	}

	private void setup() {
		if (options.hook() && !player.getScoreboard()
				.equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
			scoreboard = player.getScoreboard();
		} else {
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		}

		objective = scoreboard.registerNewObjective("curl_objective", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		if (aether.getAdapter() != null) {
			objective.setDisplayName(
					ChatColor.translateAlternateColorCodes('&', aether.getAdapter().getTitle(player)));
		} else {
			objective.setDisplayName("Default Title");
		}

		boards.add(this);
	}

	public String getNewKey(BoardEntry entry) {
		for (ChatColor color : ChatColor.values()) {
			String colorText = color + String.valueOf(ChatColor.WHITE);
			if (entry.getText().length() > 16) {
				String sub = entry.getText().substring(0, 16);
				colorText = colorText + ChatColor.getLastColors(sub);
			}

			if (!keys.contains(colorText)) {
				keys.add(colorText);
				return colorText;
			}
		}

		throw new IndexOutOfBoundsException("No more keys available!");
	}

	public List<String> getBoardEntriesFormatted() {
		List<String> toReturn = new ArrayList<>();
		for (BoardEntry entry : new ArrayList<>(entries)) {
			toReturn.add(entry.getText());
		}

		return toReturn;
	}

	public BoardEntry getByPosition(int position) {
		int i = 0;

		for (BoardEntry board : entries) {
			if (i == position) {
				return board;
			}
			i++;
		}

		return null;
	}

	public BoardCooldown getCooldown(String id) {
		for (BoardCooldown cooldown : getCooldowns()) {
			if (cooldown.getId().equals(id)) {
				return cooldown;
			}
		}

		return null;
	}

	public Set<BoardCooldown> getCooldowns() {
		cooldowns.removeIf(cooldown -> System.currentTimeMillis() >= cooldown.getEnd());
		return cooldowns;
	}
}
