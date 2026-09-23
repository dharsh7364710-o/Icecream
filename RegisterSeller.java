package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterSeller {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();

            String sql = "INSERT INTO users (role,name,email,phone,shop_name,admin_id,password) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "SELLER");
            ps.setString(2, "Seller Name");
            ps.setString(3, "seller@gmail.com");
            ps.setString(4, "9876543210");
            ps.setString(5, "Deva Ice Cream Shop");
            ps.setString(6, "");
            ps.setString(7, "12345");

            ps.executeUpdate();

            System.out.println("Seller registered successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
