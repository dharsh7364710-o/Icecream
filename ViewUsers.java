package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewUsers {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT id,role,name,email,phone,shop_name,admin_id,password FROM users"
            );

            System.out.println("USERS");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("role") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("phone") + " | " +
                    rs.getString("shop_name") + " | " +
                    rs.getString("admin_id") + " | " +
                    rs.getString("password")
                );
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
