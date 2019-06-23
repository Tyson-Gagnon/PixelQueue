package me.itsy.pixelqueue.Objects;

import org.spongepowered.api.entity.living.player.Player;

public class BattlingPlayers {

    Player player1,player2;

    public BattlingPlayers(Player player, Player player2){

        this.player1 = player;
        this.player2 = player2;

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
