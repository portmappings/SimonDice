package me.portmapping.simonDice.board.scoreboard.cooldown;

import lombok.Getter;
import me.portmapping.simonDice.board.scoreboard.Board;

@Getter
public class BoardCooldown {

	private final Board board;
	private final String id;
	private final double duration;
	private final long end;

	public BoardCooldown(Board board, String id, double duration) {
		this.board = board;
		this.id = id;
		this.duration = duration;
		this.end = (long) (System.currentTimeMillis() + (duration * 1000));

		board.getCooldowns().add(this);
	}

	public void cancel() {
		board.getCooldowns().remove(this);
	}
}