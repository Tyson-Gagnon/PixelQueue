package me.itsy.pixelqueue;

import com.google.inject.Inject;
import me.itsy.pixelqueue.Commands.Join;
import me.itsy.pixelqueue.Commands.Queue;
import me.itsy.pixelqueue.Managers.SQLManager;
import me.itsy.pixelqueue.Managers.TierConfigManager;
import me.itsy.pixelqueue.Events.PlayerJoinForFirstTime;
import net.minecraftforge.common.util.INBTSerializable;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import javax.security.auth.login.Configuration;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Plugin(
        name = "me.itsy.pixelqueue.PixelQueue",
        version = "0.1",
        authors = "itsyxD",
        id = "pixelqueue"

)
public class PixelQueue {

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dir;

    public static List<String> playersInQueueOU;
    public static List<String> playersInQueueAG;
    public static List<String> playersWithElo;

    public static int timer;

    @Inject
    Game game;

    private static PixelQueue instance;

    @Listener
    public void onPreInitialization(GamePreInitializationEvent e) {

        instance = this;

       //PlayerConfigManager.setup(dir);
       //PlayerConfigManager.load();

        TierConfigManager.setup(dir);
        TierConfigManager.load();

        SQLManager.load();

    }

    @Listener
    public void onEnable(GameInitializationEvent e){

        registerCommands();
        registerListeners();

        playersInQueueOU = new ArrayList<>();
        playersInQueueAG= new ArrayList<>();
        timer = 30;

        //playersWithElo = TierConfigManager.getTierConfNode("PlayersWithELO").getChildrenList().stream().map(CommentedConfigurationNode::getString).collect(Collectors.toList());

    }
    private void registerCommands() {

        CommandSpec QueueJoin = CommandSpec.builder()
                .description(Text.of("Enters the queue for the specified format. </pq join OU>"))
                .executor(new Join())
                .arguments(GenericArguments.string(Text.of("format")))
                .permission("pixelqueue.join")
                .build();

        CommandSpec PixelQueueCMD = CommandSpec.builder()
                .description(Text.of("Main Command for pixel queue"))
                .executor(new Queue())
                .child(QueueJoin,"join")
                .permission("pixel.queue")
                .build();

        game.getCommandManager().register(this, PixelQueueCMD,"pixelqueue","pq");
    }

    private void registerListeners() {
        game.getEventManager().registerListeners(this, new PlayerJoinForFirstTime());

    }

    @Listener
    public void onDisable(GameStoppedServerEvent e){

    }

    public static PixelQueue getInstance(){return instance;}

    public static Path getDir(){
        return instance.dir;
    }


}
