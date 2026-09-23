package backend;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8081), 0
        );

        server.createContext("/login", exchange -> {

            exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin", "*"
            );

            exchange.getResponseHeaders().add(
                "Access-Control-Allow-Methods", "POST, OPTIONS"
            );

            exchange.getResponseHeaders().add(
                "Access-Control-Allow-Headers", "Content-Type"
            );

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                try {

                    String data = new String(
                        exchange.getRequestBody().readAllBytes()
                    );

                    String[] values = data.split("&");

                    String role =
                        java.net.URLDecoder.decode(
                            values[0].split("=", 2)[1],
                            "UTF-8"
                        );

                    String email =
                        java.net.URLDecoder.decode(
                            values[1].split("=", 2)[1],
                            "UTF-8"
                        );

                    String password =
                        java.net.URLDecoder.decode(
                            values[2].split("=", 2)[1],
                            "UTF-8"
                        );

                    Connection con = DBC.getConnection();

                    String sql =
                        "SELECT name, role FROM users " +
                        "WHERE email=? AND password=? AND role=?";

                    PreparedStatement ps =
                        con.prepareStatement(sql);

                    ps.setString(1, email);
                    ps.setString(2, password);
                    ps.setString(3, role);

                    ResultSet rs = ps.executeQuery();

                    String response;

                    if (rs.next()) {

                        response =
                            "Login Successful!|" +
                            rs.getString("name") + "|" +
                            rs.getString("role");

                    } else {

                        response =
                            "Invalid email or password";
                    }

                    rs.close();
                    ps.close();
                    con.close();

                    int status =
                        response.startsWith("Login Successful")
                        ? 200 : 401;

                    exchange.sendResponseHeaders(
                        status,
                        response.length()
                    );

                    exchange.getResponseBody()
                        .write(response.getBytes());

                    exchange.close();

                } catch (Exception e) {

                    e.printStackTrace();

                    String response =
                        "Login Failed!";

                    exchange.sendResponseHeaders(
                        500,
                        response.length()
                    );

                    exchange.getResponseBody()
                        .write(response.getBytes());

                    exchange.close();
                }

            } else {

                String response =
                    "Only POST request allowed";

                exchange.sendResponseHeaders(
                    405,
                    response.length()
                );

                exchange.getResponseBody()
                    .write(response.getBytes());

                exchange.close();
            }
        });

        server.start();

        System.out.println("Login Server Started!");
        System.out.println("http://localhost:8081");
    }
}