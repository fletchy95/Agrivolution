package com.example.vanda.agrivolution;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
class MySqlUsage
{
    // Connection info for my personal SQL server.  You will need to adjust this to your settings.
    // TODO setup mysql database, make sure it fits your table, user, and password
    // TODO setup MySql backend.
    private static String url = "jdbc:mysql://localhost:3306/***Insert Table Name***?useSSL=false";
    private static String user = "";
    private static String password = "";
    private static Connection con;
    static void submitTicket(String[] data) throws SQLException
    {
        con = DriverManager.getConnection(url, user, password);
        for (int i = 0; i < 1; i++) {
            querySQL("INSERT INTO ticket " +
                    "(farmName, farmAddress, locationDetail, ticketTitle, date, email, optionalContact, description) VALUES (" +
                    data[0] + ", " + data[1] + "', " + data[2] + ", " + data[3] + ", " + "from_unixtime(" + data[4] + ")" + data[5] + ", " + data[6] + ", " + data[7] + ");"
            );
        }

    }
    // TODO create a get ticket method.  This should load already existing tickets in to ticket page.
    private static void getTickets()
    {

    }
    private static void querySQL (String query) throws SQLException
    // Allows for Inserts and Deletes from the database.
    {
        Statement st = con.createStatement();
        st.executeUpdate(query);
        st.close();
    }
    private static String selectSQL(String query) throws SQLException
    // Allows select statements to be run and their value returned neatly.
    {
        String result = "";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next())
        {
            result = rs.getString(1);
        }
        return result;
    }
}

