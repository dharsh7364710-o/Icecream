package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlaceOrder {

    public static boolean placeOrder(
        int userId,
        int productId,
        int quantity,
        double totalAmount
    ) {

        String sql =
            "INSERT INTO orders " +
            "(user_id, product_id, quantity, total_amount) " +
            "VALUES (?, ?, ?, ?)";

        try {

            Connection con = DBC.getConnection();

            if (con == null) {
                return false;
            }

            PreparedStatement ps =
                con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, totalAmount);

            ps.executeUpdate();

            ps.close();
            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public static void main(String[] args) {

        boolean result =
            placeOrder(1, 1, 1, 120.0);

        if (result) {
            System.out.println(
                "Order placed successfully!"
            );
        } else {
            System.out.println(
                "Order placement failed!"
            );
        }
    }
}
