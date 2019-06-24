package me.itsy.pixelqueue.Events;

import com.google.common.base.Optional;
import me.itsy.pixelqueue.Formats.OverUsed;
import me.itsy.pixelqueue.Managers.Storage;
import me.itsy.pixelqueue.Objects.PlayerWithELO;
import me.itsy.pixelqueue.PixelQueue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class DisconnectEvent {


    @Listener
    public void onDisconnect(ClientConnectionEvent.Disconnect e){
        Player player = e.getCause().first(Player.class).get();

        if(PixelQueue.playersInQueueOU.contains(player)){
            PixelQueue.playersInQueueOU.remove(player);
        }

        PlayerWithELO playerWithELO = new PlayerWithELO(player, Storage.getELOOU(player.getUniqueId()), Storage.getELOAG(player.getUniqueId()));

        if(OverUsed.listOfPlayersInQueue.contains(playerWithELO)){
            OverUsed.listOfPlayersInQueue.remove(playerWithELO);
        }

    }
}
