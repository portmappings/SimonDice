package me.portmapping.simondice;

import lombok.Getter;
import me.portmapping.simondice.board.provider.SimonDiceScoreboard;
import me.portmapping.simondice.board.Aether;
import me.portmapping.simondice.commands.SimonDiceCommand;
import me.portmapping.simondice.game.Game;
import me.portmapping.simondice.listeners.BasicPreventionListener;
import me.portmapping.simondice.listeners.EntityListener;
import me.portmapping.simondice.listeners.PlayerListener;
import me.portmapping.simondice.utils.file.FileConfig;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private Game game;
    private Aether aether;
    private FileConfig settings;

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
        registerListeners();

        this.settings = new FileConfig(this,"settings.yml");
        this.game = new Game();

        this.aether = new Aether(this,new SimonDiceScoreboard());

    }

    protected void registerCommands(){
        getCommand("simondice").setExecutor(new SimonDiceCommand());
        getCommand("simondice").setTabCompleter(new SimonDiceCommand());

    }
    protected void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new BasicPreventionListener(), this);
    }

    @Override
    public void onDisable() {
        if(this.game.isRunning()) this.game.stop();
    }
}
