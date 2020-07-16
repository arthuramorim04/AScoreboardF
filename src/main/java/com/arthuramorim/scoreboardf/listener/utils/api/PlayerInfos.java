//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.arthuramorim.scoreboardf.listener.utils.api;

import com.arthuramorim.scoreboardf.Scoreboard;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Locale;
import java.util.UUID;

public class PlayerInfos {
    public static HashSet<UUID> CLAN_SCORE = new HashSet();
    private final DecimalFormatSymbols DFS = new DecimalFormatSymbols(new Locale("pt", "BR"));
    private DecimalFormat FORMATTER;
    private Player p;

    public PlayerInfos(Player p) {
        this.FORMATTER = new DecimalFormat("###,###,###", this.DFS);
        this.p = p;
    }

    public boolean hasFaction() {
        try {
            return !Scoreboard.Factions.getPlayerFaction(this.p).isNone();
        } catch (NullPointerException var2) {
            return false;
        }
    }

    public String getPlayerPower() {
        return Scoreboard.Factions.getPlayerPower(this.p);
    }

    public String getPlayerFactionName() {
        return Scoreboard.Factions.getPlayerFaction(this.p).getName();
    }

    public String getPlayerFactionID() {
        return Scoreboard.Factions.getPlayerFaction(this.p).getId();
    }

    public String getPlayerFactionPlayersOnlines() {
        return Scoreboard.Factions.getPlayerFaction(this.p).getOnlinePlayers().size() + "/" + Scoreboard.Factions.getPlayerFaction(this.p).getMPlayers().size();
    }

    public String getPlayerFactionPower() {
        return Scoreboard.Factions.getPlayerFaction(this.p).getPowerRounded() + "/" + Scoreboard.Factions.getPlayerFaction(this.p).getPowerMaxRounded();
    }

    public String getLocationNameFaction() {
        return Scoreboard.Factions.getLocationName(this.p);
    }

    public int getPlayerFactionLands() {
        return Scoreboard.Factions.getPlayerFaction(this.p).getLandCount();
    }

    public String getPlayerCoins() {
        return this.FORMATTER.format(Scoreboard.Vault.getPlayerBalance(this.p));
    }
}
