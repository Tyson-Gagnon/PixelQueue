package me.itsy.pixelqueue.Commands;

import me.itsy.pixelqueue.Managers.Storage;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class SetEloCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = (Player)src;

        int elotoset = args.<Integer>getOne("elo").get();

        Storage.setOUELO(player.getUniqueId(), elotoset);


        return CommandResult.success();
    }
}
