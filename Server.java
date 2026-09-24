package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Server {

    static Map<String,String> parseForm(String data) throws Exception {
        Map<String,String> map = new HashMap<>();

        for (String pair : data.split("&")) {
            String[] parts = pair.split("=", 2);

            if (parts.length == 2) {
                map.put(
                    URLDecoder.decode(parts[0], "UTF-8"),
                    URLDecoder.decode(parts[1], "UTF-8")
                );
            }
        }

        return map;
    }

    static void cors(HttpExchange exchange) {
        exchange.getResponseHeaders().add(
            "Access-Control-Allow-Origin",
            "*"
        );

        exchange.getResponseHeaders().add(
            "Access-Control-Allow-Methods",
            "POST, OPTIONS"
        );

        exchange.getResponseHeaders().add(
            "Access-Control-Allow-Headers",
            "Content-Type"
        );
    }

    static void send(
        HttpExchange exchange,
        int status,
        String response
    ) throws IOException {

        byte[] bytes =
            response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add(
            "Content-Type",
            "text/plain; charset=UTF-8"
        );

        exchange.sendResponseHeaders(
            status,
            bytes.length
        );

        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    public static void main(String[] args) throws IOException {

        HttpServer server =
            HttpServer.create(
                new InetSocketAddress(8080),
                0
            );

        server.createContext("/register", exchange -> {

            cors(exchange);

            if ("OPTIONS".equalsIgnoreCase(
                exchange.getRequestMethod())) {

                send(exchange, 204, "");
                return;
            }

            if (!"POST".equalsIgnoreCase(
                exchange.getRequestMethod())) {

                send(
                    exchange,
                    405,
                    "Only POST request allowed"
                );

                return;
            }

            try {

                String data =
                    new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                    );

                Map<String,String> form =
                    parseForm(data);

                String role =
                    form.get("role");

                String name =
                    form.get("name");

                String email =
                    form.get("email");

                String phone =
                    form.get("phone");

                String shopName =
                    form.get("shopName");

                String adminId =
                    form.get("adminId");

                String password =
                    form.get("password");

                if (role == null ||
                    name == null ||
                    email == null ||
                    password == null) {

                    send(
                        exchange,
                        400,
                        "Required details missing"
                    );

                    return;
                }

                Connection con =
                    DBC.getConnection();

                if (con == null) {

                    send(
                        exchange,
                        500,
                        "Database connection failed"
                    );

                    return;
                }

                String sql =
                    "INSERT INTO users " +
                    "(role,name,email,phone,shop_name,admin_id,password) " +
                    "VALUES (?,?,?,?,?,?,?)";

                PreparedStatement ps =
                    con.prepareStatement(sql);

                ps.setString(1, role);
                ps.setString(2, name);
                ps.setString(3, email);
                ps.setString(4, phone);
                ps.setString(5, shopName);
                ps.setString(6, adminId);
                ps.setString(7, password);

                ps.executeUpdate();

                ps.close();
                con.close();

                send(
                    exchange,
                    200,
                    "Registration Successful!|" + role
                );

            } catch (Exception e) {

                e.printStackTrace();

                send(
                    exchange,
                    500,
                    "Registration Failed!"
                );
            }
        });

        server.createContext("/login", exchange -> {

            cors(exchange);

            if ("OPTIONS".equalsIgnoreCase(
                exchange.getRequestMethod())) {

                send(exchange, 204, "");
                return;
            }

            if (!"POST".equalsIgnoreCase(
                exchange.getRequestMethod())) {

                send(
                    exchange,
                    405,
                    "Only POST request allowed"
                );

                return;
            }

            try {

                String data =
                    new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                    );

                Map<String,String> form =
                    parseForm(data);

                String role =
                    form.get("role");

                String email =
                    form.get("email");

                String password =
                    form.get("password");

                if (role == null ||
                    email == null ||
                    password == null) {

                    send(
                        exchange,
                        400,
                        "Login details missing"
                    );

                    return;
                }

                Connection con =
                    DBC.getConnection();

                if (con == null) {

                    send(
                        exchange,
                        500,
                        "Database connection failed"
                    );

                    return;
                }

                String sql =
                    "SELECT name,role " +
                    "FROM users " +
                    "WHERE LOWER(email)=LOWER(?) " +
                    "AND password=? " +
                    "AND UPPER(role)=UPPER(?)";

                PreparedStatement ps =
                    con.prepareStatement(sql);

                ps.setString(1, email.trim());
                ps.setString(2, password);
                ps.setString(3, role.trim());

                ResultSet rs =
                    ps.executeQuery();

                if (rs.next()) {

                    String name =
                        rs.getString("name");

                    String databaseRole =
                        rs.getString("role");

                    send(
                        exchange,
                        200,
                        "Login Successful!" +
                        "|" +
                        name +
                        "|" +
                        databaseRole.toUpperCase()
                    );

                } else {

                    send(
                        exchange,
                        401,
                        "Invalid email or password"
                    );
                }

                rs.close();
                ps.close();
                con.close();

            } catch (Exception e) {

                e.printStackTrace();

                send(
                    exchange,
                    500,
                    "Login Failed!"
                );
            }
        });

        server.start();

        System.out.println(
            "DevaMMart Server Started!"
        );

        System.out.println(
            "Register + Login Server: http://localhost:8080"
        );
    }
}