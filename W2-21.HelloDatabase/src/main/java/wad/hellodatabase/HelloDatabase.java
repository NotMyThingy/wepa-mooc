package wad.hellodatabase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public class HelloDatabase {

    public static void main(String[] args) throws Exception {
        // Open connection to a database
        Connection connection = DriverManager.getConnection("jdbc:h2:./database", "sa", "");

        try {
            // If database has not yet been created,  insert content
            RunScript.execute(connection, new FileReader("schema.sql"));
            RunScript.execute(connection, new FileReader("data.sql"));
        } catch (FileNotFoundException | SQLException t) {
            System.out.println(t.getMessage());
        }
        
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Agent");
        
        while(resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            
            System.out.println(id + " : [" + name + "]");
        }
        
        resultSet.close();
        connection.close();

    }
}
