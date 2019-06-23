package me.itsy.pixelqueue.Objects;

import org.spongepowered.api.entity.living.player.Player;

public class PlayerWithELO {

     Player player;
     int ou,ag;

    public PlayerWithELO(Player player, int OUELO, int AGElo){
        this.player = player;
        this.ou = OUELO;
        this.ag = AGElo;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOu() {
        return ou;
    }

    public int getAg() {
        return ag;
    }
}
