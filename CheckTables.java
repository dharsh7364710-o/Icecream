package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class CheckTables {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();

            DatabaseMetaData meta = con.getMetaData();

            ResultSet rs = meta.getTables(null, "PUBLIC", "%", new String[]{"TABLE"});

            System.out.println("Tables in DevaMMart Database:");

            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }

            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}