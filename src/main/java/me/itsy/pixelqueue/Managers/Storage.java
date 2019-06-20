package me.itsy.pixelqueue.Managers;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERELO WHERE PLAYER=?");
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERELO WHERE PLAYER=?");
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

    public static void setELO(UUID player, int ELO, String type){

        switch (type.toUpperCase()){
            case "OU":
                type = "ELOOU";
                break;
            case "AG":
                type = "ELOAG";
                break;
        }

        try{

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PLAYERSELO SET ? = ? WHERE PLAYER = ?");
            preparedStatement.setString(1,type);
            preparedStatement.setInt(2,ELO);
            preparedStatement.setString(3,player.toString());

            preparedStatement.executeQuery();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
