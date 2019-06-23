package me.itsy.pixelqueue.Managers;

import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import info.pixelmon.repack.ninja.leaping.configurate.loader.ConfigurationLoader;
import me.itsy.pixelqueue.PixelQueue;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static Path dir, config, storage, censor, lang, alliances;
    private static ConfigurationLoader<CommentedConfigurationNode> tierLoad;
    private static CommentedConfigurationNode tierNode;
    private static final String[] FILES = {"Tiers.conf"};

    public static void setup(Path folder) {
        dir = folder;
        config = dir.resolve(FILES[0]);
        load();
    }

    public static void load() {
        try {
            if(!Files.exists(dir))
                Files.createDirectory(dir);

            tierLoad = HoconConfigurationLoader.builder().setPath(config).build();
            tierNode = tierLoad.load();
            List<String> people = new ArrayList<>();
            List<String> BANNEDPokemon = new ArrayList<>();

            people.add("EEEEEE");
            BANNEDPokemon.add("LUGIA");
            BANNEDPokemon.add("GROUDON");
            BANNEDPokemon.add("KYOGRE");


            tierNode.getNode("PeopleWithELO").setValue(people);
            tierNode.getNode("OUBANNED","Pokemon").setValue(BANNEDPokemon);
            save();

        } catch(IOException e) {
            PixelQueue.getInstance().getLogger().error("Error loading up PokeTeams Configuration"); e.printStackTrace();
        }
    }

    public static void save() {
        try {
            tierLoad.save(tierNode);

        } catch (IOException e) {
            PixelQueue.getInstance().getLogger().error("Error saving PokeTeams Configuration"); e.printStackTrace();
        }
    }


    public static CommentedConfigurationNode getConfNode(Object... node) {
        return tierNode.getNode(node);
    }

}