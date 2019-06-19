package me.itsy.pixelqueue;

import com.google.inject.Inject;
import me.itsy.pixelqueue.Commands.Join;
import me.itsy.pixelqueue.Commands.Queue;
import me.itsy.pixelqueue.Events.PlayerJoinForFirstTime;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        name = "me.itsy.pixelqueue.PixelQueue",
        version = "0.1",
        authors = "itsyxD",
        id = "pixelqueuelol"

)
public class PixelQueue {

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

    private File config = new File(configDir+"tiers.conf");
    private ConfigurationLoader<CommentedConfigurationNode> configManager = HoconConfigurationLoader.builder().setFile(config).build();
    private CommentedConfigurationNode configNode;

    private File playerConfig = new File(configDir+"players.conf");
    private ConfigurationLoader<CommentedConfigurationNode> playerConfigManager = HoconConfigurationLoader.builder().setFile(playerConfig).build();
    private CommentedConfigurationNode playersConfigNode;

    private ConfigurationLoader<CommentedConfigurationNode> getConfigManager(){return configManager;}
    private ConfigurationLoader<CommentedConfigurationNode> getplayerConfigManager(){return playerConfigManager;}

    public static List<String> playersInQueueOU = new ArrayList<>();
    public static List<String> playersInQueueAG = new ArrayList<>();

    public static int timer = 30;

    @Inject
    Game game;


    private static PixelQueue instance;

    @Listener
    public void onPreInitialization(GamePreInitializationEvent e) {
        if (!configDir.exists()) {
            configDir.mkdir();
        }

        try{
            if(!config.exists()){
                config.createNewFile();
                configNode = configManager.load();

                configNode.getNode("ConfigVersion").setValue(1);
                configManager.save(configNode);
                logger.info("created tier config");
            }
            if(!playerConfig.exists()){
                playerConfig.createNewFile();
                playersConfigNode = playerConfigManager.load();


                playersConfigNode.getNode("ConfigVersion").setValue(1);
                playerConfigManager.save(configNode);
                logger.info("created player config");
            }

            configNode = configManager.load();
            playersConfigNode = playerConfigManager.load();

        }catch (IOException Exception){
            logger.info("Couldnt create default config");
        }
    }

    @Listener
    public void onEnable(GameInitializationEvent e){
        instance = this;
        registerCommands();
        registerListeners();

    }
    private void registerCommands() {

        CommandSpec QueueJoin = CommandSpec.builder()
                .description(Text.of("Enters the queue for the specified format. </me.itsy.pixelqueue.PixelQueue join OU>"))
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

    public ConfigurationNode getConfigNode() {
        return configNode;
    }

    public ConfigurationNode getPlayerConfigNode() {
        return playersConfigNode;
    }


}
