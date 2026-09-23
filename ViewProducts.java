package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewProducts {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT id, name, price FROM products"
            );

            System.out.println("PRODUCTS");
            System.out.println("--------------------------------");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | ₹" +
                    rs.getDouble("price")
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
