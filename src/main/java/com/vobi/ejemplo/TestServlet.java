package com.vobi.ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class TestServlet extends HttpServlet {

    private DataSource dataSource;

    public void init() throws ServletException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("java:jboss/datasources/saludmp-oracleDS-SAO");
        } catch (NamingException e) {
            throw new ServletException("Cannot retrieve java:/comp/env/jdbc/PostgresDS", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT NOW()");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.println("<html><body><h1>Current Time: " + rs.getString(1) + "</h1></body></html>");
            }
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain connection from database", e);
        } finally {
            out.close();
        }
    }
}
