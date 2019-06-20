package me.itsy.pixelqueue.Events;

import me.itsy.pixelqueue.Managers.SQLManager;
import me.itsy.pixelqueue.Managers.TierConfigManager;
import me.itsy.pixelqueue.PixelQueue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinForFirstTime extends SQLManager {

    @Listener
    public void moduleExistCheck(ClientConnectionEvent.Join e) {

        Player player = e.getTargetEntity();


        if (!PixelQueue.playersWithElo.contains(player.getUniqueId().toString())) {
            try {
                Connection connection = getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO" +
                                " PLAYERSELO (PLAYER) " +
                                "VALUES" +
                                " (?)");
                preparedStatement.setString(1, player.getUniqueId().toString());

                preparedStatement.executeQuery();

                preparedStatement.close();
                connection.close();

                PixelQueue.playersWithElo.add(player.getUniqueId().toString());
                TierConfigManager.getTierConfNode("PlayersWithELO").setValue(PixelQueue.playersWithElo);
                TierConfigManager.save();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
}
