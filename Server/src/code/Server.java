package code;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class will be one who open a socket server on a port and accepts conection from costumers
 * Each costumer is attended in a separated thread to handle multiple clients
 */
public class Server {

    private final ServerSocket serverSocket;
    private final Integer PORT = 3000; //port that was requested

    /**
     * We create a serverSocket in the designated port
     * @throws IOException if anythings goes wrong.
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Once the server was set up correctly we put it to run invocking these method
     * @throws IOException if there is a problem accepting conections.
     */
    public void run() throws IOException {

        while (true){
            System.out.println("Waiting conection");
            Socket socket = this.serverSocket.accept();
            System.out.println("Conection accepted");
        }



    }


    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (IOException e) {
            System.out.println("There was a problem setting the server up");
        }
    }

}
