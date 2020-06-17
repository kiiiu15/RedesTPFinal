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


    /**
     * To simulate a conversation log, we add '>' before typing a message as an input and a '<' before the response
     */
    @Override
    public void run() {
        try {

            boolean keepTalking = true;

            do {
                keepTalking = listenAndSpeak();
            }while (true == keepTalking);
            this.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

    }

    private boolean listenAndSpeak() throws IOException {
        this.speaker.writeUTF("> ");
        String inMessage = null;
        inMessage = this.reader.readLine();
        if ("X".equalsIgnoreCase(inMessage)) {
            return false;
        }
        System.out.print("< "+inMessage + "\n> ");
        String outMessage = new Scanner(System.in).nextLine();
        if ("X".equalsIgnoreCase(outMessage)){
            return false;
        }
        this.speaker.writeUTF("< "+outMessage+"\n");
        return true;
    }




    private void disconnect () throws IOException {
        this.socket.close();
    }
}
