package me.itsy.pixelqueue.Managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Storage extends SQLManager{

    public static int getELOOU(UUID player){
        int value = 1000;

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERSELO WHERE PLAYER=?");
            preparedStatement.setString(1,player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("ElOOU");

            preparedStatement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return value;
    }

    public static int getELOAG(UUID player){
        int value = 1000;

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERSELO WHERE PLAYER=?");
            preparedStatement.setString(1,player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("ElOAG");

            preparedStatement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return value;
    }

    //TODO: getEloAG

    public static void setOUELO(UUID player, int ELO){

        try{

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERSELO (PLAYER,ELOOU) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1,player.toString());
            preparedStatement.setInt(2,ELO);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void setAGELO(UUID player, int ELO){

        try{

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERSELO (PLAYER,AG) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1,player.toString());
            preparedStatement.setInt(2,ELO);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
