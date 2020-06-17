package code;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is made to represent the server treatment to the costumer conection, it extends from a thread to free the server
 * so it can accept others conections and manage them in separated threads
 */
public class Conection extends Thread {

    private  final Socket socket;
    private final DataOutputStream speaker;
    private final BufferedReader reader;

    public Conection(Socket socket) throws IOException {
        this.socket = socket;
        this.speaker = new DataOutputStream(this.socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }


    @Override
    public void run() {
        try {
            String inMessage = null;
            inMessage = this.reader.readLine();
            System.out.println(inMessage);
            String outMessage = new Scanner(System.in).nextLine();
            this.speaker.writeUTF(outMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
