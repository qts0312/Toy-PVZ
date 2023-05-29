package database;

import java.sql.*;
import java.util.Objects;

/*
 * To use this module, you need to create MySQL database locally.
 */
public class Info {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/pvz_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static Connection conn;

    private static void openDb() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeDb() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Entry {
        public String userId;
        public int level;
        public int money;
        public int peashooter;
        public int wallnut;
        public int sunflower;
        public int squash;
        public int cherrybomb;
        public int jalapeno;
        public int melonpult;

        public Entry(String userId,
                     int level,
                     int money,
                     int peashooter,
                     int wallnut,
                     int sunflower,
                     int squash,
                     int cherrybomb,
                     int jalapeno,
                     int melonpult) {
            this.userId = userId;
            this.level = level;
            this.money = money;
            this.peashooter = peashooter;
            this.wallnut = wallnut;
            this.sunflower = sunflower;
            this.squash = squash;
            this.cherrybomb = cherrybomb;
            this.jalapeno = jalapeno;
            this.melonpult = melonpult;
        }

        public Entry(String userId) {
            this(userId, 0, 0, 1, 1, 1, 0, 0, 0, 0);
        }
    }

    public static void createEntry(Entry entry) {
        openDb();

        try {
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO info VALUES ('" +
                    entry.userId + "', " +
                    entry.level + ", " +
                    entry.money + ", " +
                    entry.peashooter + ", " +
                    entry.wallnut + ", " +
                    entry.sunflower + ", " +
                    entry.squash + ", " +
                    entry.cherrybomb + ", " +
                    entry.jalapeno + ", " +
                    entry.melonpult + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }
    }

    public static Entry getEntry(String userId) {
        openDb();
        Entry ret = null;

        try {
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM info WHERE user_id = '" + userId + "';";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                ret = new Entry(
                        rs.getString("user_id"),
                        rs.getInt("level"),
                        rs.getInt("money"),
                        rs.getInt("peashooter"),
                        rs.getInt("wallnut"),
                        rs.getInt("sunflower"),
                        rs.getInt("squash"),
                        rs.getInt("cherrybomb"),
                        rs.getInt("jalapeno"),
                        rs.getInt("melonpult")
                );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }

        return ret;
    }

    public static void updateEntry(Entry entry) {
        if (Objects.equals(entry.userId, "default"))
            return;

        openDb();

        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE info SET " +
                    "level = " + entry.level + ", " +
                    "money = " + entry.money + ", " +
                    "peashooter = " + entry.peashooter + ", " +
                    "wallnut = " + entry.wallnut + ", " +
                    "sunflower = " + entry.sunflower + ", " +
                    "squash = " + entry.squash + ", " +
                    "cherrybomb = " + entry.cherrybomb + ", " +
                    "jalapeno = " + entry.jalapeno + ", " +
                    "melonpult = " + entry.melonpult + " " +
                    "WHERE user_id = '" + entry.userId + "';";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }
    }
}
