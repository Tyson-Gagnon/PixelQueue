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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

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

                        winnerk = determineK(winnerElo);
                        loserK = determineK(loserElo);

                        winnerElo = calculate2PlayersRating(winnerElo,loserElo,winnerk,"+");
                        loserElo = calculate2PlayersRating(loserElo,winnerElo,loserK,"-");

                        ((Player) winner).sendMessage(Text.of(TextColors.GOLD,"[Pixel Queue] ",TextColors.BLUE,"Congratulations on winning! Your new ELO rating is " ,TextColors.GREEN, winnerElo));
                        ((Player) loser).sendMessage(Text.of(TextColors.GOLD,"[Pixel Queue] ",TextColors.BLUE,"Oh no! You lost :( Your new ELO is " ,TextColors.GREEN, loserElo));

                        Storage.addWin(winner.getUniqueID());
                        ((Player) winner).sendMessage(Text.of(TextColors.GOLD,"[Pixel Queue] ",TextColors.BLUE,"You are now at " ,TextColors.GREEN, Storage.getWins(winner.getUniqueID()),
                                TextColors.BLUE," wins"));

                        Storage.setOUELO(((Player)winner).getUniqueId(),winnerElo);
                        Storage.setOUELO(((Player)loser).getUniqueId(),loserElo);



                    }
                }
            }

        }


    }

    public int determineK(int rating) {
        int K;
        if (rating < 2000) {
            K = 32;
        } else if (rating >= 2000 && rating < 2400) {
            K = 24;
        } else {
            K = 16;
        }
        return K;
    }

    private int calculate2PlayersRating(int player1Rating, int player2Rating,int k, String outcome) {

        double actualScore;

        // winner
        if (outcome.equals("+")) {
            actualScore = 1.0;
            // draw
        } else if (outcome.equals("=")) {
            actualScore = 0.5;
            // lose
        } else if (outcome.equals("-")) {
            actualScore = 0;
            // invalid outcome
        } else {
            return player1Rating;
        }

        // calculate expected outcome
        double exponent = (double) (player2Rating - player1Rating) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        return (int) Math.round(player1Rating + k * (actualScore - expectedOutcome));
    }


}
