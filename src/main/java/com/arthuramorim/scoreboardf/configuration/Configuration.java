package com.arthuramorim.scoreboardf.configuration;


import com.arthuramorim.scoreboardf.Scoreboard;

public class Configuration {
    public static final String MINA_WORLD = Scoreboard.getPlugin().getConfig().getString("MundoMineracao");
    public static final String NORMAL_WORLD = Scoreboard.getPlugin().getConfig().getString("MundoNormal");
    public static final String SITE = Scoreboard.getPlugin().getConfig().getString("site");
    public static final String VIP_WORLD = Scoreboard.getPlugin().getConfig().getString("MundoVip");

    public Configuration() {
    }
}
