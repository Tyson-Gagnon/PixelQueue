package me.itsy.pixelqueue.Events;

import me.itsy.pixelqueue.PixelQueue;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerJoinForFirstTime {

    @Listener
    public void moduleExistCheck(ClientConnectionEvent.Join e){

        Player player = e.getTargetEntity();

        ConfigurationNode playerConfigNode = PixelQueue.getInstance().getPlayerConfigNode();

        if(playerConfigNode.getNode("players",player.getUniqueId().toString()).isVirtual()){
            playerConfigNode.getNode("players",player.getUniqueId().toString(),"ELO","OU").setValue(1000);
            playerConfigNode.getNode("players",player.getUniqueId().toString(),"ELO","AG").setValue(1000);
        }
    }
}
