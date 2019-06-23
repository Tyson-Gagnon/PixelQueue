package me.itsy.pixelqueue.Formats;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import me.itsy.pixelqueue.Commands.StartBattle;
import me.itsy.pixelqueue.Managers.Storage;
import me.itsy.pixelqueue.Objects.BattlingPlayers;
import me.itsy.pixelqueue.Objects.PlayerWithELO;
import me.itsy.pixelqueue.PixelQueue;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OverUsed {

    public static void matchOUPlayers(){

        List<PlayerWithELO> listOfPlayersInQueue = new ArrayList<>();
        Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 1..."));

        for(int i = 0; i < PixelQueue.playersInQueueOU.size();i++)
        {
            Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 2..."));
            Player spongePlayer = PixelQueue.playersInQueueOU.get(i);
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)spongePlayer;
            PartyStorage party = Pixelmon.storageManager.getParty(entityPlayerMP);
            boolean canPlay = true;
            for(int j = 0; j < 6; j++){
                Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 3..."));
                if(party.get(j) != null){
                    for(int k = 0; k < PixelQueue.bannedPokemon.size();k++){
                        Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 4..."));
                        if(party.get(j).getSpecies().equals(PixelQueue.bannedPokemon.get(k))){
                            Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 5..."));
                            canPlay = false;
                        }
                    }
                }
            }
            Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 6..."));
            if(canPlay){
                Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 7..."));
                PlayerWithELO playerWithELO = new PlayerWithELO(spongePlayer, Storage.getELOOU(spongePlayer.getUniqueId()),Storage.getELOAG(spongePlayer.getUniqueId()));
                listOfPlayersInQueue.add(playerWithELO);
//                listOfPlayersInQueue.sort(new Comparator<PlayerWithELO>() {
//
//                    @Override
//                    public int compare(PlayerWithELO p1, PlayerWithELO p2) {
//                        if(p1.getOu() == p2.getOu()){
//                            return 0;
//                        }
//                        return Integer.compare(p1.getOu(),p2.getOu());
//                    }
//                });


            }else{
                spongePlayer.sendMessage(Text.of(TextColors.GOLD,"[PixelQueue] ",TextColors.RED," You have been kicked from the queue. You have an illegal pokemon in your party!"));
                PixelQueue.playersInQueueOU.remove(spongePlayer);
            }
        }
        Sponge.getServer().getBroadcastChannel().send(Text.of("CHECK 8..."));


        for (int j = 0; j < listOfPlayersInQueue.size();j++){
            Sponge.getServer().getBroadcastChannel().send(Text.of(listOfPlayersInQueue.get(j).getPlayer().getName()));
            if(PixelQueue.playersInQueueOU.contains(listOfPlayersInQueue.get(j).getPlayer())){
                PixelQueue.playersInQueueOU.remove(listOfPlayersInQueue.get(j).getPlayer());
            }
        }


        boolean flag = false;

        if(listOfPlayersInQueue.size() % 2 == 0){
            Sponge.getServer().getBroadcastChannel().send(Text.of("Even amount of players"));
            flag = true;
        }

        if(flag){
            while(listOfPlayersInQueue.size() > 0){

                if(flag){

                    PixelQueue.battlingPlayers.add(new BattlingPlayers(listOfPlayersInQueue.get(0).getPlayer(),listOfPlayersInQueue.get(1).getPlayer()));
                    StartBattle.startBattle(listOfPlayersInQueue.get(0).getPlayer(),listOfPlayersInQueue.get(1).getPlayer());
                    listOfPlayersInQueue.remove(listOfPlayersInQueue.get(0));
                    listOfPlayersInQueue.remove(listOfPlayersInQueue.get(1));
                    StartBattle.startBattle(listOfPlayersInQueue.get(0).getPlayer(),listOfPlayersInQueue.get(1).getPlayer());

                    Sponge.getServer().getBroadcastChannel().send(Text.of("MAtching..."));
                }

            }
        }

    }


}
