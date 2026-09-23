package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterUser {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();

            String sql = "INSERT INTO users (role,name,email,phone,shop_name,admin_id,password) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "BUYER");
            ps.setString(2, "Dharshini");
            ps.setString(3, "dharshini@gmail.com");
            ps.setString(4, "9876543210");
            ps.setString(5, "");
            ps.setString(6, "");
            ps.setString(7, "12345");

            ps.executeUpdate();

            System.out.println("Buyer registered successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
