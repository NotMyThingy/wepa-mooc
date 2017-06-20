package wad.helloinsert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import org.h2.tools.RunScript;

public class HelloInsert {

    private static Scanner reader;

    public static void main(String[] args) throws Exception {
        // Open connection to a database
        Connection connection = DriverManager.getConnection("jdbc:h2:./database", "sa", "");

        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("schema.sql"));
            RunScript.execute(connection, new FileReader("data.sql"));
        } catch (FileNotFoundException | SQLException t) {
        }

        listAgents(connection);
        System.out.println();
        addNewAgent(connection);
        System.out.println();
        listAgents(connection);

        connection.close();
    }

    private static void listAgents(Connection connection) throws Exception {

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Agent");

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");

            System.out.println(id + " : [" + name + "]");
        }
        
        resultSet.close();
    }

    private static void addNewAgent(Connection connection) throws Exception {

        reader = new Scanner(System.in);

        System.out.println("Add one:");
        System.out.print("What id? ");
        String id = reader.nextLine().trim();
        System.out.print("What name: ");
        String name = reader.nextLine().trim();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Agent VALUES(?,?)");
        statement.setString(1, id);
        statement.setString(2, name);
        statement.execute();

    }
}
