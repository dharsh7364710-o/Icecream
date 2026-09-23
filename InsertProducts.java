package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertProducts {

    public static void main(String[] args) {

        try {
            Connection con = DBC.getConnection();

            String sql = "INSERT INTO products (name, price, description, image) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            String[][] products = {
                {"Mango Ice Cream", "120", "Fresh mango ice cream", ""},
                {"Strawberry Ice Cream", "120", "Fresh strawberry ice cream", ""},
                {"Chocolate Ice Cream", "130", "Rich chocolate ice cream", ""},
                {"Vanilla Ice Cream", "100", "Classic vanilla ice cream", ""},
                {"Pistachio Ice Cream", "140", "Creamy pistachio ice cream", ""},
                {"Blackcurrant Ice Cream", "130", "Sweet blackcurrant ice cream", ""},
                {"Cookies & Cream", "140", "Cookies and cream ice cream", ""},
                {"Butterscotch Ice Cream", "130", "Creamy butterscotch ice cream", ""},
                {"Kulfi", "110", "Traditional creamy kulfi", ""},
                {"Blueberry Ice Cream", "140", "Fresh blueberry ice cream", ""},
                {"Pista Almond Ice Cream", "150", "Pista almond ice cream", ""},
                {"Tender Coconut Ice Cream", "140", "Tender coconut ice cream", ""},
                {"Coffee Ice Cream", "130", "Rich coffee ice cream", ""},
                {"Caramel Ice Cream", "140", "Creamy caramel ice cream", ""},
                {"Red Velvet Ice Cream", "150", "Red velvet ice cream", ""}
            };

            for (String[] product : products) {
                ps.setString(1, product[0]);
                ps.setDouble(2, Double.parseDouble(product[1]));
                ps.setString(3, product[2]);
                ps.setString(4, product[3]);
                ps.executeUpdate();
            }

            System.out.println("15 Products inserted successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
