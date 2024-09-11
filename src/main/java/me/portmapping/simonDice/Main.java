package me.portmapping.simonDice;

import lombok.Getter;
import me.portmapping.simonDice.commands.StartCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private Game game;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        this.game = new Game();


    }

    protected void registerCommands(){
        getCommand("start").setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {

    }
}
