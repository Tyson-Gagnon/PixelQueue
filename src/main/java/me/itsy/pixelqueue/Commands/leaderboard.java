package me.itsy.pixelqueue.Commands;

import me.itsy.pixelqueue.Managers.Storage;
import me.itsy.pixelqueue.Objects.PlayerRanking;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayMode;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class leaderboard implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!(src instanceof Player)) {
            return CommandResult.success();
        }
        Player player = (Player) src;

        String input;
        int page;



        if (args.hasAny("page")) {
            input = args.<String>getOne("page").get();
            try{
               page = Integer.parseInt(input);
                page =  (page - 1) * 10;
                showLeaderBoard(page, player);
            }catch (Exception e){
                String userName = args.<String>getOne("page").get();
                Optional<User> optionalUser = Sponge.getServiceManager().provide(UserStorageService.class).get().get(userName);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    printUserInfo(user,player);
                } else {
                    player.sendMessage(Text.of(TextColors.RED, "User not found"));
                }
            }

        }else {
            printUserInfo(((User)player),player);
        }

        return CommandResult.success();
    }

    private void showLeaderBoard(int page, Player player) {

        List<PlayerRanking> playerRankingList = Storage.getPlayerRankings();
        playerRankingList.sort(Comparator.comparingInt(PlayerRanking::getOuelo).reversed());


        Scoreboard scoreboard = Scoreboard.builder().build();
        Objective obj = Sponge.getGame().getRegistry().createBuilder(Objective.Builder.class).criterion(Criteria.DUMMY).name("Rankings").displayName(Text.of(TextStyles.BOLD, TextColors.BLUE, "   PixelQueue Rankings   ")).build();
        obj.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "  ")).setScore(30);
        obj.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, " ")).setScore(1);
        obj.getOrCreateScore(Text.of(TextColors.YELLOW, TextStyles.BOLD, "Page " + ((page / 10 - 1) + 2))).setScore(0);


        int f = 29;
        for (int i = 0; i < 10; i++) {
            if (page == 0) {
                if (i < playerRankingList.size()) {
                    obj.getOrCreateScore(Text.of(
                            TextColors.YELLOW, "#" + (i + 1) + " ",
                            TextColors.GOLD, playerRankingList.get(i).getPlayer().getName(),
                            TextColors.GRAY, ">>",
                            TextColors.GOLD, playerRankingList.get(i).getOuelo()
                    )).setScore(f);
                    f--;
                }
            } else if (page <= -1) {
                player.sendMessage(Text.of(TextColors.RED, "There can't be a page 0 silly :)"));
                i = 5;
            } else {
                if (i + page < playerRankingList.size()) {
                    obj.getOrCreateScore(Text.of(
                            TextColors.YELLOW, "#" + (i + page + 1) + " ",
                            TextColors.GOLD, playerRankingList.get(i + page).getPlayer().getName(),
                            TextColors.GRAY, ">>",
                            TextColors.GOLD, playerRankingList.get(i + page).getOuelo()
                    )).setScore(f);
                    f--;
                }
            }
        }
        scoreboard.addObjective(obj);
        scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    private void printUserInfo(User userDude, Player player) {

            User user = userDude;
            int rank = 0;
            int elo = Storage.getELOOU(user.getUniqueId());
            int wins = Storage.getWins(user.getUniqueId());
            List<PlayerRanking> playerRankingList = Storage.getPlayerRankings();
            playerRankingList.sort(Comparator.comparingInt(PlayerRanking::getOuelo).reversed());
            for (int i = 0; i < playerRankingList.size(); i++) {
                if (playerRankingList.get(i).getPlayer().getName().equals(user.getName())) {
                    rank = i + 1;
                }
            }

            Scoreboard scoreboard = Scoreboard.builder().build();
            Objective obj = Sponge.getGame().getRegistry().createBuilder(Objective.Builder.class).criterion(Criteria.DUMMY).name("Rankings").displayName(Text.of(TextStyles.BOLD, TextColors.BLUE, "    " + user.getName() + "   ")).build();
            obj.getOrCreateScore(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "  ")).setScore(30);
            obj.getOrCreateScore(Text.of(TextColors.BLUE, TextStyles.BOLD, "Rank: ", TextColors.YELLOW, rank)).setScore(29);
            obj.getOrCreateScore(Text.of(TextColors.BLUE, TextStyles.BOLD, "ELO: ", TextColors.YELLOW, elo)).setScore(28);
            obj.getOrCreateScore(Text.of(TextColors.BLUE, TextStyles.BOLD, "Wins: ", TextColors.YELLOW, wins)).setScore(27);

            scoreboard.addObjective(obj);
            scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
            player.setScoreboard(scoreboard);


    }


}

