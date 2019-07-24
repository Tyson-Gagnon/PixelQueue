package me.itsy.pixelqueue.Managers;

import me.itsy.pixelqueue.PixelQueue;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    private static SqlService sql;
    private static final String URI = "jdbc:h2:" + PixelQueue.getDir().toString() + "/players.db";

    public static void load(){
        try{
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS PLAYERSELO ("
            +       "PLAYER UUID UNSIGNED NOT NULL," +
                    "PLAYERNAME VARCHAR(20)," +
                    "ELOOU INT NOT NULL DEFAULT 1000," +
                    "ELOAG INT NOT NULL DEFAULT 1000," +
                    "WINS INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (PLAYER))");

            stmt.close();
            connection.close();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected static Connection getConnection(){
        try {
            return sql.getDataSource(URI).getConnection();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
