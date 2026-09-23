package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterAdmin {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();

            String sql = "INSERT INTO users (role,name,email,phone,shop_name,admin_id,password) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "ADMIN");
            ps.setString(2, "Admin");
            ps.setString(3, "admin@gmail.com");
            ps.setString(4, "");
            ps.setString(5, "");
            ps.setString(6, "ADM001");
            ps.setString(7, "12345");

            ps.executeUpdate();

            System.out.println("Admin registered successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
