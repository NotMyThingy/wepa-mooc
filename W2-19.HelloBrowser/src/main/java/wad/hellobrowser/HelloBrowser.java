package wad.hellobrowser;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class HelloBrowser {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("===============");
        System.out.println(" THE INTERNETS ");
        System.out.println("===============");

        System.out.print("Where to? ");
        String address = input.nextLine();
        input.close();

        Socket socket = new Socket(InetAddress.getByName(address), 80);

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println("GET / HTTP/1.1");
        writer.println("Host: " + address);
        writer.println();
        writer.flush();

        System.out.println("==========");
        System.out.println(" RESPONSE ");
        System.out.println("==========");

        Scanner reader = new Scanner(socket.getInputStream());
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
        }
        
        writer.close();
        reader.close();
        socket.close();
    }
}
