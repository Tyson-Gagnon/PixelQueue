package me.itsy.pixelqueue.Commands;

import me.itsy.pixelqueue.Formats.OverUsed;
import me.itsy.pixelqueue.Managers.Storage;
import me.itsy.pixelqueue.Objects.PlayerWithELO;
import me.itsy.pixelqueue.PixelQueue;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Join implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(TextColors.RED, "NO WORK CUZ YOU NO PLAYER!"));
            return CommandResult.success();
        }
        Player player = (Player) src;

        String format = args.<String>getOne("format").get();

        PlayerWithELO playerWithELO = new PlayerWithELO(player, Storage.getELOOU(player.getUniqueId()),Storage.getELOAG(player.getUniqueId()));



            if (!OverUsed.listOfPlayersInQueue.contains(playerWithELO) && !PixelQueue.playersInQueueOU.contains(player)) {
                if (format.equalsIgnoreCase("ou") || format.equalsIgnoreCase("overused")) {
                    PixelQueue.playersInQueueOU.add(player);
                    if (PixelQueue.playersInQueueOU.size() > 1) {
                        player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, " You have been added to the queue! The estimated wait time is ",
                                PixelQueue.timer, " seconds! There are currently ", PixelQueue.playersInQueueOU.size() - 1, " other players in the queue!"));
                    } else {
                        player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, " You have been added to the queue! The estimated wait time is", "unknown! There are currently ", "0", " other players in the queue!"));
                    }
                } else if (format.equalsIgnoreCase("AG") || format.equalsIgnoreCase("AnythingGoes")) {
                    PixelQueue.playersInQueueAG.add(player);
                    if (PixelQueue.playersInQueueAG.size() > 2) {
                        player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, " You have been added to the queue! The estimated wait time is ",
                                PixelQueue.timer, " seconds! There are currently ", PixelQueue.playersInQueueOU.size() - 1, " other players in the queue!"));
                    } else {
                        player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, " You have been added to the queue! The estimated wait time is", "unknown! There are currently ", "0", " other players in the queue!"));
                    }

                } else {
                    player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue]", TextColors.BLUE, " Format not found! The current formats supported are OU(Over Used) and AG(Anything Goes)"));
                }

            } else {
                player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.RED, "You are already in the Queue"));
            }
        return CommandResult.success();
    }
}
