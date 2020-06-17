package code;

import java.net.Socket;

/**
 * This class is made to represent the server treatment to the costumer conection, it extends from a thread to free the server
 * so it can accept others conections and manage them in separated threads
 */
public class Conection extends Thread {

    private  final Socket socket;

    public Conection(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        System.out.println("Conexion: " + super.getId() + "Running");
        try {
            Thread.sleep(10000); // this was to check if i could run multiple clients at once
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Conexion: " + super.getId() + "Closing");
    }
}
