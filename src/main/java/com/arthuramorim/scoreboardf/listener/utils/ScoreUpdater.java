package com.arthuramorim.scoreboardf.listener.utils;



import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.arthuramorim.scoreboardf.api.ScoreBoardAPI;
import com.arthuramorim.scoreboardf.listener.JoinPlayer;
import com.arthuramorim.scoreboardf.listener.utils.api.PlayerInfos;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreUpdater extends BukkitRunnable {
    public ScoreUpdater() {
    }

    public void run() {
        Iterator iter = JoinPlayer.boards.keySet().iterator();

        try {
            while(iter.hasNext()) {
                Player p = (Player)iter.next();
                updateScoreBoard(p);
            }
        } catch (ConcurrentModificationException var3) {
        }

    }

    public static void updateScoreBoard(Player p) {
        PlayerInfos INFO = new PlayerInfos(p);
        ScoreBoardAPI sb = (ScoreBoardAPI)JoinPlayer.boards.get(p);
        if (sb != null) {
            sb.setDisplayName(INFO.getLocationNameFaction());
            if (INFO.hasFaction()) {
                sb.update(Integer.toString(INFO.getPlayerFactionLands()), 2);
                sb.update(INFO.getPlayerFactionPower(), 3);
                sb.update(INFO.getPlayerFactionPlayersOnlines(), 4);
                sb.update(ChatColor.GREEN + INFO.getPlayerCoins(), 7);
                sb.update(INFO.getPlayerPower(), 8);


            } else {
                sb.getScoreboard().getTeam("45").setPrefix("");
                sb.getScoreboard().getTeam("45").setSuffix("");
                sb.getScoreboard().getTeam("46").setPrefix("");
                sb.getScoreboard().getTeam("46").setPrefix("");
                sb.update(INFO.getPlayerPower(), 5);
                sb.update(ChatColor.GREEN + INFO.getPlayerCoins(), 4);
            }

        }
    }
}

