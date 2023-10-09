package jm.task.core.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final Properties PROPERTIES = new Properties();

    public static Connection getConnection(){
        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(inputStream);
            return DriverManager.getConnection(
                    PROPERTIES.getProperty("jdbc.connection.url"),
                    PROPERTIES.getProperty("jdbc.connection.username"),
                    PROPERTIES.getProperty("jdbc.connection.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
