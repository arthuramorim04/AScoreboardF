//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.arthuramorim.startscoreboard.api;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreBoardAPI {
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private String title;
    private String objName = null;
    private Map<String, Integer> scores;
    private HashMap<Integer, Team> teams;
    private Objective obj = null;
    int nteams = 50;
    int nnn = 100;

    public ScoreBoardAPI(String title, String objName) {
        this.title = title;
        this.objName = objName;
        this.scores = Maps.newLinkedHashMap();
        this.teams = Maps.newLinkedHashMap();
    }

    public void blankLine() {
        this.add("§c ");
    }

    public void blankLine(int pos) {
        this.add("§c ", pos);
    }

    public HashMap<Integer, Team> getTeams() {
        return this.teams;
    }

    public void add(String text) {
        this.add(text, (Integer)null);
    }

    public void add(String text, Integer score) {
        Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");
        text = this.fixDuplicates(text);
        text = ChatColor.translateAlternateColorCodes('&', text);
        this.scores.put(text, score);
    }

    private String fixDuplicates(String text) {
        while(this.scores.containsKey(text)) {
            text = text + "§r";
        }

        return text;
    }

    private Entry<Team, String> createTeam(String text) {
        String result = "";
        Team team = this.scoreboard.registerNewTeam("" + this.nteams--);
        if (text.length() <= 16) {
            return new SimpleEntry(team, text);
        } else {
            Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
            team.setPrefix((String)iterator.next());
            result = (String)iterator.next();
            return new SimpleEntry(team, result);
        }
    }

    public void build() {
        this.obj = this.scoreboard.registerNewObjective(this.objName, "dummy");
        this.obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.title));
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        int next = this.scores.size() - 2;
        int index = next + 1;
        int par = 0;

        for(Iterator var5 = this.scores.entrySet().iterator(); var5.hasNext(); --index) {
            Entry<String, Integer> text = (Entry)var5.next();
            par += 2;
            Entry<Team, String> team = this.createTeam((String)text.getKey());
            int score = text.getValue() != null ? (Integer)text.getValue() : index;
            ScoreBoardAPI.OfflinePlayerv2 player = new ScoreBoardAPI.OfflinePlayerv2((String)team.getValue());
            ((Team)team.getKey()).addPlayer(player);
            this.obj.getScore(player).setScore(score);
            this.teams.put(score, (Team)team.getKey());
        }

    }

    public void setDisplayName(String displayName) {
        this.obj.setDisplayName(displayName);
    }

    public void reset() {
        this.title = null;
        this.scores.clear();
        Iterator var2 = this.teams.values().iterator();

        while(var2.hasNext()) {
            Team t = (Team)var2.next();
            t.unregister();
        }

        this.teams.clear();
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void send(Player... players) {
        try {
            Player[] var5 = players;
            int var4 = players.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                Player p = var5[var3];
                p.setScoreboard(this.scoreboard);
            }
        } catch (NullPointerException var6) {
        }

    }

    public void update(String text, int score) {
        if (this.teams.containsKey(score)) {
            text = ChatColor.translateAlternateColorCodes('&', text);
            Team team = (Team)this.teams.get(score);
            if (text.length() <= 16) {
                team.setPrefix("");
                team.setSuffix(text);
                return;
            }

            Entry<Team, String> team2 = this.makeTeam(text);
            if (score == 3) {
                team2 = this.makeTeam("  Coins: " + text);
            }

            ScoreBoardAPI.OfflinePlayerv2 player = new ScoreBoardAPI.OfflinePlayerv2((String)team2.getValue());
            ((Team)team2.getKey()).addPlayer(player);
            this.resetScore(score);
            this.obj.getScore(player).setScore(score);
        } else {
            text = ChatColor.translateAlternateColorCodes('&', text);
            this.add(text, score);
        }

    }

    public void resetScore(int score) {
        Iterator var3 = this.obj.getScoreboard().getEntries().iterator();

        while(var3.hasNext()) {
            String s = (String)var3.next();
            if (this.obj.getScore(s).getScore() == score) {
                this.getScoreboard().resetScores(s);
                return;
            }
        }

    }

    private Entry<Team, String> makeTeam(String text) {
        String result = "";
        Team team = this.scoreboard.registerNewTeam("" + this.nnn++);
        if (text.length() <= 16) {
            return new SimpleEntry(team, text);
        } else {
            Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
            team.setPrefix((String)iterator.next());
            result = (String)iterator.next();
            return new SimpleEntry(team, result);
        }
    }

    public class OfflinePlayerv2 implements OfflinePlayer {
        private String playerName;

        public OfflinePlayerv2(String playerName) {
            this.playerName = playerName;
        }

        public boolean isOnline() {
            return false;
        }

        public String getName() {
            return this.playerName;
        }

        public UUID getUniqueId() {
            return UUID.nameUUIDFromBytes(this.playerName.getBytes(Charsets.UTF_8));
        }

        public void setName(String s) {
            this.playerName = s;
        }

        public boolean isBanned() {
            return false;
        }

        public void setBanned(boolean banned) {
            throw new UnsupportedOperationException();
        }

        public boolean isWhitelisted() {
            return false;
        }

        public void setWhitelisted(boolean value) {
            throw new UnsupportedOperationException();
        }

        public Player getPlayer() {
            throw new UnsupportedOperationException();
        }

        public long getFirstPlayed() {
            return System.currentTimeMillis();
        }

        public long getLastPlayed() {
            return System.currentTimeMillis();
        }

        public boolean hasPlayedBefore() {
            return false;
        }

        public Location getBedSpawnLocation() {
            throw new UnsupportedOperationException();
        }

        public boolean isOp() {
            return false;
        }

        public void setOp(boolean value) {
            throw new UnsupportedOperationException();
        }

        public Map<String, Object> serialize() {
            throw new UnsupportedOperationException();
        }
    }
}
