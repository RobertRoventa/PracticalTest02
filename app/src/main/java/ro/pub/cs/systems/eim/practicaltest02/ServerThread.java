package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class ServerThread extends Thread {

    private int port = 0;
    private ServerSocket serverSocket = null;

    private String data = null;

    public ServerThread(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e("ASD", "An exception has occurred: " + ioException.getMessage());

        }
//        this.data = new HashMap<>();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocker(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public synchronized void setData(String city) {
        this.data = "asd";
    }

    public synchronized String getData() {
        return data;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("ASD", "[SERVER] Waiting for a connection...");
                Socket socket = serverSocket.accept();
                Log.i("ASD", "[SERVER] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }

        } catch (IOException ioException) {
            Log.e("ASD", "An exception has occurred: " + ioException.getMessage());

        }
    }

    public void stopThread() {
        if (serverSocket != null) {
            interrupt();
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e("asd", "An exception has occurred: " + ioException.getMessage());

            }
        }
    }

}