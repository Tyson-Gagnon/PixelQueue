package me.itsy.pixelqueue.Objects;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

public class PlayerRanking {

    private User player;
    private int ouelo,agelo,wins;

    public PlayerRanking(User player, int ouelo, int agelo, int wins) {
        this.player = player;
        this.ouelo = ouelo;
        this.agelo = agelo;
        this.wins = wins;
    }

    public User getPlayer() {
        return player;
    }

    public int getOuelo() {
        return ouelo;
    }

    public int getAgelo() {
        return agelo;
    }

    public int getWins() {
        return wins;
    }
}
