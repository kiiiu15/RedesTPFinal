package code;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is made to represent the server treatment to the costumer conection, it extends from a thread to free the server
 * so it can accept others conections and manage them in separated threads
 */
public class Conection extends Thread {

    private final Socket socket;
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


        boolean keepTalking = true;

        do {
            try {
                keepTalking = listenAndSpeak();
            } catch (IOException e) {
                System.out.println("There were problems during the comunication. Conection lost");
                keepTalking = false;
            }
        } while (true == keepTalking);
        try {
            this.disconnect(); //we don't know if we got here because there was an error or someone wanted to end comunication
            //so we try to close the socket anyways
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();//stop the thread.
        }


    }

    private boolean listenAndSpeak() throws IOException {
        this.speaker.writeUTF("> ");
        String inMessage = null;
        inMessage = this.reader.readLine();
        if ("X".equalsIgnoreCase(inMessage)) {
            System.out.println("The client ended the conection");
            this.speaker.writeUTF("\n You ended the conection");
            return false;
        }
        System.out.print("< " + inMessage + "\n> ");
        String outMessage = new Scanner(System.in).nextLine();
        if ("X".equalsIgnoreCase(outMessage)) {
            System.out.println("You ended the conection");
            this.speaker.writeUTF("The server ended the conection");
            return false;
        }
        this.speaker.writeUTF("\n< " + outMessage + "\n");
        return true;
    }


    private void disconnect() throws IOException {
        this.socket.close();
    }
}
