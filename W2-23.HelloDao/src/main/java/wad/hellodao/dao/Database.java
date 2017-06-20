package wad.hellodao.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.tools.RunScript;

public class Database<T> {

    private boolean debug;
    private String address;

    public Database(String address) throws Exception {
        this.address = address;

        Connection conn = getConnection();

        try {
            // If database has not yet been created, insert content
            RunScript.execute(conn, new FileReader("schema.sql"));
            RunScript.execute(conn, new FileReader("data.sql"));
        } catch (Throwable t) {
        }

        conn.close();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address, "sa", "");
    }

    public void setDebugMode(boolean d) {
        debug = d;
    }

    public int update(String updateQuery, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        if (debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }

        stmt.close();
        conn.close();

        return changes;
    }

    public List<T> queryAndCollect(String query, Collector<T> col, Object... params) throws SQLException {
        Connection conn = getConnection();
        if (debug) {
            System.out.println("---");
            System.out.println("Executing: " + query);
            System.out.println("---");
        }

        List<T> rows = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            if (debug) {
                System.out.println("---");
                System.out.println(query);
                debug(rs);
                System.out.println("---");
            }

            rows.add(col.collect(rs));
        }

        rs.close();
        stmt.close();
        conn.close();

        return rows;
    }

    private void debug(ResultSet rs) throws SQLException {
        int columns = rs.getMetaData().getColumnCount();
        for (int i = 0; i < columns; i++) {
            System.out.print(
                    rs.getObject(i + 1) + ":"
                    + rs.getMetaData().getColumnName(i + 1) + "  ");
        }

        System.out.println();
    }
}
