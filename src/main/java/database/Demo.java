package database;

import java.sql.*;

public class Demo {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/pvz_db";

        String user = "root";

        String password = "pku170730";

        try {
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, user, password);

            if (!conn.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }

            Statement statement = conn.createStatement();

            String sql = "SELECT * FROM info WHERE user_id = 'test';";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("user_id") + " " + rs.getString("level"));
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, can't find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
