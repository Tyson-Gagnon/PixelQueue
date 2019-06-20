package me.itsy.pixelqueue.Managers;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TierConfigManager {
    private static Path dir, tierConfig;
    private static CommentedConfigurationNode tierConfNode;
    private static ConfigurationLoader<CommentedConfigurationNode> tierConfLoad;

    public static void setup(Path folder) {
        dir = folder;
        tierConfig = dir.resolve("Tiers.conf");
    }

    public static void load() {

        try{
            if(!Files.exists(dir)){
                Files.createDirectory(dir);

                tierConfLoad = HoconConfigurationLoader.builder().setPath(tierConfig).build();
                tierConfNode = tierConfLoad.load();
                tierConfNode.getNode("ConfigVersion").setValue(1);
                List<String> list = new ArrayList<>();
                list.add("eeeeeeeeee");
                tierConfNode.getNode("PlayersWithELO").setValue(list);
                save();
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static void save() {
        try {
            tierConfLoad.save(tierConfNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CommentedConfigurationNode getTierConfNode(Object... node){
        return tierConfNode.getNode(node);
    }
}
