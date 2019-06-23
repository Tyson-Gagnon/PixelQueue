package me.itsy.pixelqueue;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import me.itsy.pixelqueue.Commands.Join;
import me.itsy.pixelqueue.Commands.Queue;
import me.itsy.pixelqueue.Commands.SetEloCommand;
import me.itsy.pixelqueue.Commands.getEloCommand;
import me.itsy.pixelqueue.Managers.ConfigManager;
import me.itsy.pixelqueue.Managers.SQLManager;
import me.itsy.pixelqueue.Events.PlayerJoinForFirstTime;
import me.itsy.pixelqueue.Objects.BattlingPlayers;
import me.itsy.pixelqueue.Objects.TimerObject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Plugin(
        name = "me.itsy.pixelqueue.PixelQueue",
        version = "0.1",
        authors = "itsyxD",
        id = "pixelqueue"

)
public class PixelQueue {

    @Inject
    private static Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dir;

    public static List<Player> playersInQueueOU;
    public static List<Player> playersInQueueAG;
    public static List<String> playersWithELO;
    public static List<EnumSpecies> bannedPokemon;
    public static List<BattlingPlayers> battlingPlayers;

    public static int timer;

    @Inject
    Game game;

    private static PixelQueue instance;

    @Listener
    public void onPreInitialization(GamePreInitializationEvent e) {

        instance = this;

        //PlayerConfigManager.setup(dir);
        //PlayerConfigManager.load();
        ConfigManager.setup(dir);

        SQLManager.load();

    }

    Function<Object, String> stringTransformer = new Function<Object, String>() {
        public String apply(Object input) {
            if (input instanceof String) {
                return (String) input;
            } else {
                return null;
            }
        }
    };

    @Listener
    public void onEnable(GameInitializationEvent e) {

        registerCommands();
        registerListeners();

        playersInQueueOU = new ArrayList<>();
        playersInQueueAG = new ArrayList<>();
        TimerObject.startTimer();

        timer = 30;

        playersWithELO = ConfigManager.getConfNode("PlayersWithELO").getChildrenList().stream().map(CommentedConfigurationNode::getString).collect(Collectors.toList());

        List<String> bannedPokemonNames = ConfigManager.getConfNode("OUBANNED","Pokemon").getChildrenList().stream().map(CommentedConfigurationNode::getString).collect(Collectors.toList());
        for(int i = 0; i < bannedPokemonNames.size();i++){
            bannedPokemon.add(EnumSpecies.getFromNameAnyCase(bannedPokemonNames.get(i)));
        }
    }

    private void registerCommands() {

        CommandSpec set = CommandSpec.builder()
                .description(Text.of("Enters the queue for the specified format. </pq join OU>"))
                .executor(new SetEloCommand())
                .arguments(GenericArguments.integer(Text.of("elo")))
                .permission("pixelqueue.join")
                .build();

        CommandSpec get = CommandSpec.builder()
                .description(Text.of("Enters the queue for the specified format. </pq join OU>"))
                .executor(new getEloCommand())
                .arguments()
                .permission("pixelqueue.join")
                .build();

        CommandSpec QueueJoin = CommandSpec.builder()
                .description(Text.of("Enters the queue for the specified format. </pq join OU>"))
                .executor(new Join())
                .arguments(GenericArguments.string(Text.of("format")))
                .permission("pixelqueue.join")
                .build();

        CommandSpec PixelQueueCMD = CommandSpec.builder()
                .description(Text.of("Main Command for pixel queue"))
                .executor(new Queue())
                .child(QueueJoin, "join")
                .child(get,"getELO")
                .child(set,"set")
                .permission("pixel.queue")
                .build();

        game.getCommandManager().register(this, PixelQueueCMD, "pixelqueue", "pq");


    }

    private void registerListeners() {
        game.getEventManager().registerListeners(this, new PlayerJoinForFirstTime());

    }

    @Listener
    public void onDisable(GameStoppedServerEvent e) {

    }

    public static PixelQueue getInstance() {
        return instance;
    }

    public static Path getDir() {
        return instance.dir;
    }

    public static Logger getLogger() {
        return logger;
    }


}
