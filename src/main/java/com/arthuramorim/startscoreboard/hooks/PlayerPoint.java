package com.arthuramorim.startscoreboard.hooks;

import com.arthuramorim.startscoreboard.Scoreboard;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.plugin.Plugin;

/**/
public class PlayerPoint {

    private PlayerPoints playerPoints;
    private Scoreboard plugin;

    public PlayerPoint(Scoreboard plugin) {
        this.plugin = plugin;
        this.hookPlayerPoints();
    }

    private boolean hookPlayerPoints() {
        final Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        return playerPoints != null;
    }

    public PlayerPoints getPlayerPoints() {
        return playerPoints;
    }

    public boolean hasPlayerPoint() {
        return playerPoints != null;
    }
}
