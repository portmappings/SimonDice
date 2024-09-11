package me.portmapping.simonDice;

import lombok.Getter;
import me.portmapping.simonDice.board.provider.SimonDiceScoreboard;
import me.portmapping.simonDice.commands.StartCommand;
import me.portmapping.simonDice.board.Aether;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private Game game;
    private Aether aether;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        this.game = new Game();
        this.aether = new Aether(this,new SimonDiceScoreboard());


    }

    protected void registerCommands(){
        getCommand("start").setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {

    }
}
