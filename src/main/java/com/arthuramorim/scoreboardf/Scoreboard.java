package com.arthuramorim.scoreboardf;



import com.arthuramorim.scoreboardf.hooks.Factions;
import com.arthuramorim.scoreboardf.hooks.Vault;
import com.arthuramorim.scoreboardf.listener.JoinPlayer;
import com.arthuramorim.scoreboardf.listener.utils.ScoreUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Scoreboard extends JavaPlugin {
    public static Vault Vault;
    public static com.arthuramorim.scoreboardf.hooks.Factions Factions;
    private static Scoreboard plugin;

    public Scoreboard() {
    }

    public void onEnable() {
        plugin = this;
        this.setupHooks();
        this.registerListeners();
        this.setupConfiguration();
        this.loadScoreTask();
    }

    public void onDisable() {
    }

    private void setupHooks() {
        Vault = new Vault();
        Factions = new Factions();
    }

    private void registerListeners() {
        PluginManager PM = Bukkit.getPluginManager();
        PM.registerEvents(new JoinPlayer(), this);
    }

    private void loadScoreTask() {
        Bukkit.getOnlinePlayers().forEach((p) -> {
            JoinPlayer.createScoreBoard(p);
        });
        (new ScoreUpdater()).runTaskTimerAsynchronously(this, 40L, 120L);
    }

    private void setupConfiguration() {
        this.saveDefaultConfig();
        this.saveConfig();
    }

    public static Scoreboard getPlugin() {
        return plugin;
    }
}
