package me.itsy.pixelqueue.Commands;

import me.itsy.pixelqueue.Managers.Storage;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class getEloCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = (Player)src;

        src.sendMessage(Text.of(Storage.getELOOU(player.getUniqueId())));

        return CommandResult.success();
    }
}
