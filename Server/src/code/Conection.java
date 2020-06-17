package code;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is made to represent the server treatment to the costumer conection, it extends from a thread to free the server
 * so it can accept others conections and manage them in separated threads
 */
public class Conection extends Thread {

    private  final Socket socket;
    private final DataOutputStream speaker;
    private final DataInputStream reader;

    public Conection(Socket socket) throws IOException {
        this.socket = socket;
        this.speaker = new DataOutputStream(this.socket.getOutputStream());
        this.reader = new DataInputStream(this.socket.getInputStream());
    }


    @Override
    public void run() {
        try {
            String inMessage = null;
            inMessage = this.reader.readUTF();
            System.out.println(inMessage);
            String outMessage = new Scanner(System.in).nextLine();
            this.speaker.writeUTF(outMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
