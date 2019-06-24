package me.itsy.pixelqueue.Events;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import me.itsy.pixelqueue.Managers.Storage;
import me.itsy.pixelqueue.Objects.BattlingPlayers;
import me.itsy.pixelqueue.PixelQueue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Map;

public class BattleEnd {

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent e) {

        List<EntityPlayerMP> list = e.getPlayers();

        EntityPlayerMP entityPlayerMP1, entityPlayerMP2;

        entityPlayerMP1 = e.getPlayers().get(0);
        entityPlayerMP2 = e.getPlayers().get(1);

        if (e.bc.getPlayers().size() == 2) {
            EntityPlayer loser;
            EntityPlayer winner;
            Player spongeLoser, spongeWinner;

            if (e.bc.getPlayers().get(0).isDefeated) {
                loser = (EntityPlayer) e.bc.getPlayers().get(0).getEntity();
                winner = (EntityPlayer) e.bc.getPlayers().get(1).getEntity();
            } else {
                loser = (EntityPlayer) e.bc.getPlayers().get(1).getEntity();
                winner = (EntityPlayer) e.bc.getPlayers().get(0).getEntity();
            }



            for(int i = 0; i < PixelQueue.battlingPlayers.size();i++){

                BattlingPlayers battlingPlayers = PixelQueue.battlingPlayers.get(i);

                if(battlingPlayers.getPlayer1().equals(winner) || battlingPlayers.getPlayer2().equals(winner)){
                    if(battlingPlayers.getPlayer1().equals(loser) || battlingPlayers.getPlayer2().equals(loser)){
                        PixelQueue.battlingPlayers.remove(battlingPlayers);

                        int winnerElo, loserElo;

                        winnerElo = Storage.getELOOU(((Player)winner).getUniqueId());
                        loserElo = Storage.getELOOU(((Player)loser).getUniqueId());

                        int winnerk, loserK;

                        winnerk = kValue(winnerElo,true);
                        loserK = kValue(loserElo,false);

                        winnerElo = EloRating(winnerElo,loserElo,winnerk,true);
                        loserElo = EloRating(loserElo,winnerElo,loserK,false);

                        Storage.setOUELO(((Player)winner).getUniqueId(),winnerElo);
                        Storage.setOUELO(((Player)winner).getUniqueId(),loserElo);

                    }
                }
            }

        }


    }

    public int kValue(int elo, boolean isWinner){
        int k = 80;

        if(elo >= 1100 && elo < 1300){
            k = 50;
        }else if(elo >= 1300 && elo < 1600 ){
            k = 40;
        }else if(elo >= 1600){
            k = 32;
        }else{
            if(isWinner){
                k = 80;
            }else{
                k = 20;
            }
        }

        return k;
    }

    // Function to calculate the Probability
    static double Probability(int rating1,
                             int rating2)
    {
        return 1.0f * 1.0f / (1 + 1.0f *
                (Math.pow(10, 1.0f *
                        (rating1 - rating2) / 400)));
    }

    // Function to calculate Elo rating
    // K is a constant.
    // d determines whether Player A wins
    // or Player B.
    static int EloRating(int Ra, int Rb,
                          int K, boolean d)
    {

        // To calculate the Winning
        // Probability of Player B
        int Pb = (int) Probability(Ra, Rb);

        // To calculate the Winning
        // Probability of Player A
        int Pa = (int) Probability(Rb, Ra);

        // Case -1 When Player A wins
        // Updating the Elo Ratings
        if (d == true) {
            Ra = Ra + K * (1 - Pa);
            Rb = Rb + K * (0 - Pb);
        }

        // Case -2 When Player B wins
        // Updating the Elo Ratings
        else {
            Ra = Ra + K * (0 - Pa);
            Rb = Rb + K * (1 - Pb);
        }

        return (int) Ra;


    }

}
