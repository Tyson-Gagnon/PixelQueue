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

public class Leave implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(TextColors.RED, "NO WORK CUZ YOU NO PLAYER!"));
            return CommandResult.success();
        }
        Player player = (Player) src;

        if (PixelQueue.playersInQueueOU.contains(player)) {
            PixelQueue.playersInQueueOU.remove(player);

            player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, "You have been removed from the queue!"));

        } else {
            player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, "You are not in a Queue!"));
        }

        PlayerWithELO playerWithELO = new PlayerWithELO(player, Storage.getELOOU(player.getUniqueId()), Storage.getELOAG(player.getUniqueId()));

        if (OverUsed.listOfPlayersInQueue.contains(playerWithELO)) {
            OverUsed.listOfPlayersInQueue.remove(player);

            player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, "You have been removed from the queue!"));

        } else {
            player.sendMessage(Text.of(TextColors.GOLD, "[PixelQueue] ", TextColors.BLUE, "You are not in a Queue!"));
        }


        return CommandResult.success();
    }
}
