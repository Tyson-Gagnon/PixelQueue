package me.itsy.pixelqueue.Managers;

import me.itsy.pixelqueue.Objects.PlayerRanking;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.service.user.UserStorageService;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Storage extends SQLManager {

    public static int getELOOU(UUID player) {
        int value = 1000;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERSELO WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("ElOOU");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getELOAG(UUID player) {
        int value = 1000;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERSELO WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("ElOAG");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getWins(UUID player) {
        int value = 1000;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERSELO WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("WINS");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    //TODO: getEloAG

    public static void setOUELO(UUID player, int ELO) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERSELO (PLAYER,ELOOU) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, ELO);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setAGELO(UUID player, int ELO) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERSELO (PLAYER,AG) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, ELO);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addWin(UUID player) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERSELO (PLAYER,WINS) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, getWins(player) + 1);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<PlayerRanking> getPlayerRankings() {
        try {
            Connection conn = getConnection();
            Statement stm;
            stm = conn.createStatement();
            String sql = "Select * From PLAYERSELO";
            ResultSet rst;
            rst = stm.executeQuery(sql);
            ArrayList<PlayerRanking> playerRankingArrayList = new ArrayList<>();
            while (rst.next()) {
                UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
                User user = userStorageService.get(UUID.fromString(rst.getString("PLAYER"))).get();
               // if (rst.getInt("ELOOU") > 1000) {
                    PlayerRanking playerRanking = new PlayerRanking(user,
                            rst.getInt("ELOOU"),
                            rst.getInt("ELOAG"),
                            rst.getInt("WINS"));
                    playerRankingArrayList.add(playerRanking);
                //}
            }
            return playerRankingArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
