package com.arthuramorim.scoreboardf.listener;


import com.arthuramorim.scoreboardf.Scoreboard;
import com.arthuramorim.scoreboardf.api.ScoreBoardAPI;
import com.arthuramorim.scoreboardf.configuration.Configuration;
import com.arthuramorim.scoreboardf.listener.utils.ScoreUpdater;
import com.arthuramorim.scoreboardf.listener.utils.api.PlayerInfos;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.WeakHashMap;

public class JoinPlayer implements Listener {
    public static WeakHashMap<Player, ScoreBoardAPI> boards = new WeakHashMap();

    private Scoreboard plugin;

    public JoinPlayer(Scoreboard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        createScoreBoard(p);
    }

    @EventHandler
    public void onExit(PlayerQuitEvent e) {
        boards.remove(e.getPlayer());
    }

    @EventHandler
    public void onEntry(final EventFactionsMembershipChange e) {
        if (e.getReason().equals(EventFactionsMembershipChange.MembershipChangeReason.JOIN) || e.getReason().equals(EventFactionsMembershipChange.MembershipChangeReason.LEAVE)) {
            boards.remove(e.getMPlayer().getPlayer());
            (new BukkitRunnable() {
                public void run() {
                   JoinPlayer.createScoreBoard(e.getMPlayer().getPlayer());
                    ScoreUpdater.updateScoreBoard(e.getMPlayer().getPlayer());
                }
            }).runTask(Scoreboard.getPlugin());
        }

    }

    @EventHandler
    public void onCreateFaction(final EventFactionsCreate e) {
        boards.remove(e.getMPlayer().getPlayer());
        (new BukkitRunnable() {
            public void run() {
                JoinPlayer.createScoreBoard(e.getMPlayer().getPlayer());
                ScoreUpdater.updateScoreBoard(e.getMPlayer().getPlayer());
            }
        }).runTask(Scoreboard.getPlugin());
    }

    @EventHandler
    public void onDisbandFaction(final EventFactionsDisband e) {
        Faction faction = e.getFaction();
        final List<Player> jogadores = faction.getOnlinePlayers();
        (new BukkitRunnable() {
            public void run() {
                jogadores.forEach((p) -> {
                    JoinPlayer.createScoreBoard(p);
                    ScoreUpdater.updateScoreBoard(p);
                });
                JoinPlayer.createScoreBoard(e.getMPlayer().getPlayer());
                ScoreUpdater.updateScoreBoard(e.getMPlayer().getPlayer());
            }
        }).runTask(Scoreboard.getPlugin());
    }

    public static void createScoreBoard(Player p) {
        PlayerInfos INFO = new PlayerInfos(p);
        String UUIDRandom = (new RandomUUID()).getUUID();
        ScoreBoardAPI sb = new ScoreBoardAPI(INFO.getLocationNameFaction(), UUIDRandom);
        sb.add(ChatColor.YELLOW + Configuration.SITE, 0);
        sb.blankLine(1);
        if (INFO.hasFaction()) {

            sb.add("    Terras: §e", 2);
            sb.add("    Poder: §e", 3);
            sb.add("    Online: §e", 4);
            sb.add("  §e" + INFO.getPlayerFactionName(), 5);
            sb.blankLine(6);
            if (Scoreboard.playerPoint.hasPlayerPoint()) {
                sb.add("  Cash: ", 7);
            }
            sb.add("  Coins: ", 8);
            sb.add("  Poder: §a", 9);
            sb.blankLine(10);
        } else {
            sb.add("  §7Sem Facção", 2);
            sb.blankLine(3);
            if (Scoreboard.playerPoint.hasPlayerPoint()) {
                sb.add("  Cash: ", 4);
            }
            sb.add("  Coins: ", 5);
            sb.add("  Poder: §a", 6);
            sb.blankLine(7);
        }

        sb.build();
        boards.remove(p);
        sb.send(new Player[]{p});
        boards.put(p, sb);
    }
}
