package gb.Server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement statement;

    public static void connectDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:myDB.db");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void disconnectDB (){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getNickFromLogAndPass  (String login, String pass){
        try (PreparedStatement ps = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND pass = ?")) {
            ps.setString(1,login);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}

