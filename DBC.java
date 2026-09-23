package backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBC {

    public static Connection getConnection() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:hsqldb:file:database/deva_emart;shutdown=true",
                "SA",
                ""
            );

            System.out.println("HSQLDB Connected Successfully!");

            return con;

        } catch (Exception e) {
            System.out.println("HSQLDB Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection con = getConnection();

        if (con != null) {
            try {
                con.close();
                System.out.println("HSQLDB Connection Closed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}