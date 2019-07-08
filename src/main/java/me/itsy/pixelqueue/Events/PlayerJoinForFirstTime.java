package me.itsy.pixelqueue.Events;

import me.itsy.pixelqueue.Managers.ConfigManager;
import me.itsy.pixelqueue.Managers.SQLManager;
import me.itsy.pixelqueue.PixelQueue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerJoinForFirstTime extends SQLManager {

    @Listener
    public void moduleExistCheck(ClientConnectionEvent.Join e) {

        Player player = e.getTargetEntity();


        if (!PixelQueue.playersWithELO.contains(player.getUniqueId().toString())) {
            try {
                Connection connection = getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO " +
                                "PLAYERSELO (PLAYER,PLAYERNAME) " +
                                "VALUES " +
                                "(?,?)");
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.setString(2, player.getName());

                preparedStatement.execute();

                preparedStatement.close();
                connection.close();

                PixelQueue.playersWithELO.add(player.getUniqueId().toString());

                ConfigManager.getConfNode("PlayersWithELO").setValue(PixelQueue.playersWithELO);
                ConfigManager.save();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
}
