package me.itsy.pixelqueue.Objects;

import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.rules.clauses.BattleClause;
import com.pixelmonmod.pixelmon.battles.rules.clauses.BattleClauseRegistry;

import java.util.ArrayList;
import java.util.List;

public class Rules {

    private boolean fullHeal;
    public BattleRules rules = new BattleRules();
    public List<BattleClause> battleClauses = BattleClauseRegistry.getClauseRegistry().getClauseList();

    public ArrayList<BattleClause> clause = new ArrayList<>();


    public BattleRules getRules(){

        return rules;
    }

    public void addfullHeal(boolean enable){
        rules.fullHeal = enable;
    }

    public void enaboeOu(){

        clause.add(battleClauses.get(0));
        clause.add(battleClauses.get(1));
        clause.add(battleClauses.get(7));
        clause.add(battleClauses.get(8));
        clause.add(battleClauses.get(9));
        clause.add(battleClauses.get(15));
        clause.add(battleClauses.get(18));
        clause.add(battleClauses.get(19));
        clause.add(battleClauses.get(21));
        clause.add(battleClauses.get(26));
        rules.setNewClauses(clause);

        rules.levelCap = 100;
        rules.teamPreview = true;
        rules.raiseToCap = true;
        rules.teamSelectTime = 60;
        rules.turnTime = 100;
    }






}