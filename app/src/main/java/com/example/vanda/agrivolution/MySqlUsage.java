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
    private static String url = "jdbc:mysql://localhost:3306/***Insert Table Name***?useSSL=false";
    private static String user = "";
    private static String password = "";
    private static Connection con;
    static void clearSQL() throws SQLException
    {
        con = DriverManager.getConnection(url, user, password);
        querySQL("TRUNCATE TABLE forecast;");
        querySQL("TRUNCATE TABLE currentForecast");
    }
    // TODO updateCurrentSQL needs updating
    static void updateCurrentSQL(String[] data) throws SQLException
    {
        con = DriverManager.getConnection(url, user, password);
        querySQL("INSERT INTO currentForecast " +
                "(temp, temp_min, temp_max, pressure, humidity, windSpeed) " +
                "VALUES (" +
                data[0] + ", "+data[1] + ", "+data[2] + ", "+data[3] + ", "+data[4] + ", "+data[5] + ");"
        );
    }
    // TODO updateSQL needs updating
    static void updateSQL(String[][] data, int address_ID) throws SQLException
    {
        con = DriverManager.getConnection(url, user, password);
        int columnCnt = 0;
        for (int i = 0; i < 1; i++) {
            querySQL("INSERT INTO forecast " +
                    "(address_ID, forecast_time, forecast_time_std, temp, temp_min, temp_max, pressure, humidity, " +
                    "windSpeed) VALUES (" +
                    address_ID + ", " + "from_unixtime(" + data[0][i] + "), '" + data[1][i] + "', " + data[2][i] + ", " + data[3][i]
                    + ", " + data[4][i] + ", " + data[5][i] + ", " + data[6][i] + ", " + data[7][i] + ");"
            );
        }

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

