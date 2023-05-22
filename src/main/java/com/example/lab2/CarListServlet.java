package com.example.lab2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;


@WebServlet(name = "CarListServlet", value = "/car-list")
public class CarListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve user-entered year from the form
        String year = request.getParameter("year");

        // Validate the year
        if (!isValidYear(year)) {
            request.setAttribute("errorMessage", "Помилка! Рік має бути в межах від 1900 до 2023");
            // Forward the request to the error.jsp page
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        // Connect to the database and retrieve car data
        List<Car> carList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1111");
            String query = "SELECT * FROM cars WHERE number REGEXP '^[56]+$' AND year = ? ORDER BY year";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String number = rs.getString("number");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int carYear = rs.getInt("year");
                String ownerName = rs.getString("owner_name");
                String ownerAddress = rs.getString("owner_address");

                Car car = new Car(number, brand, model, carYear, ownerName, ownerAddress);
                carList.add(car);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            // Handle database connection error
            request.setAttribute("errorMessage", "500 - помилка на стороні сервера");
            // Forward the request to the error.jsp page
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Generate HTML response
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Результати</title></head>");
        out.println("<body>");
        out.println("<h1>Результати</h1>");
        out.println("<table>");
        out.println("<tr><th>Number</th><th>Brand</th><th>Model</th><th>Year</th><th>Owner Name</th><th>Owner Address</th></tr>");

        for (Car car : carList) {
            out.println("<tr>");
            out.println("<td>" + car.getNumber() + "</td>");
            out.println("<td>" + car.getBrand() + "</td>");
            out.println("<td>" + car.getModel() + "</td>");
            out.println("<td>" + car.getYear() + "</td>");
            out.println("<td>" + car.getOwnerName() + "</td>");
            out.println("<td>" + car.getOwnerAddress() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='index.jsp'>Назад</a>");
        out.println("</body>");
        out.println("</html>");
    }
    private boolean isValidYear(String year) {
        // Perform year validation logic here
        // For example, check if the year is a valid integer and falls within a specific range
        // Return true if the year is valid, false otherwise
        try {
            int yearValue = Integer.parseInt(year);
            return yearValue >= 1900 && yearValue <= 2023;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
