package me.itsy.pixelqueue.Commands;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.BattleQuery;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.EnumBattleQueryResponse;
import me.itsy.pixelqueue.Managers.Storage;
import me.itsy.pixelqueue.Objects.Rules;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.entity.living.player.Player;

public class StartBattle {



    public static void startBattle(Player p1, Player p2){

        EntityPlayerMP emplayer1, emplayer2;

        emplayer1 = (EntityPlayerMP)p1;
       emplayer2 = (EntityPlayerMP)p2;


        Rules rulesClass = new Rules();

        rulesClass.addfullHeal(true);
        rulesClass.enaboeOu();

        BattleRules battleRules = rulesClass.getRules();

        BattleQuery battlequery = new BattleQuery(emplayer1,Pixelmon.storageManager.getParty(emplayer1).getAndSendOutFirstAblePokemon(emplayer1),
                emplayer2,Pixelmon.storageManager.getParty(emplayer2).getAndSendOutFirstAblePokemon(emplayer2));

        battlequery.battleRules = battleRules;

        battlequery.acceptQuery(emplayer1, EnumBattleQueryResponse.Accept);
        battlequery.acceptQuery(emplayer2, EnumBattleQueryResponse.Accept);


    }







}
