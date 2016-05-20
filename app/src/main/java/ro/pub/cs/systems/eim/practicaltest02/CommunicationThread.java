package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    BufferedReader bufferedReader = Utilities.getReader(socket);
    PrintWriter printWriter = Utilities.getWriter(socket);

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");
                    HttpResponse httpGetResponse = httpClient.execute(httpGet);

                    if (httpGetResponse != null) {
                        // do something with the response

                        printWriter.flush();


                        Log.i(Constants.TAG, "Eroare");
                    }

                } catch (Exception exception) {
                    Log.e(Constants.TAG, exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }

                socket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            } catch (JSONException jsonException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + jsonException.getMessage());
                if (Constants.DEBUG) {
                    jsonException.printStackTrace();
                }
            }
        } else {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }

}