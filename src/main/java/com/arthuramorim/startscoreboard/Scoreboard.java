package com.arthuramorim.startscoreboard;


import com.arthuramorim.startscoreboard.hooks.Factions;
import com.arthuramorim.startscoreboard.hooks.PlayerPoint;
import com.arthuramorim.startscoreboard.hooks.Vault;
import com.arthuramorim.startscoreboard.listener.JoinPlayer;
import com.arthuramorim.startscoreboard.listener.utils.ScoreUpdater;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Scoreboard extends JavaPlugin implements Listener {
    public static Vault Vault;
    public static com.arthuramorim.startscoreboard.hooks.Factions Factions;
    public static PlayerPoint playerPoint;
    private static Scoreboard plugin;

    public Scoreboard() {
    }

    public void onEnable() {
        plugin = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
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
        playerPoint = new PlayerPoint(this);
    }

    private void registerListeners() {
        PluginManager PM = Bukkit.getPluginManager();
        PM.registerEvents(new JoinPlayer(this), this);
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
