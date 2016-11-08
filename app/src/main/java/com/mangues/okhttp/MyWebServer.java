package com.mangues.okhttp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by mangues on 16/11/3.
 */

class MyWebServer
{
    private int port;
    private String host;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public MyWebServer(int port, String host) {
        if (socket==null){
            synchronized (Socket.class){
                if (socket==null){
                    socket = new Socket();
                }
            }
        }
        socket = new Socket();
        this.port = port;
        this.host = host;
    }

    public  void sendGet() throws IOException
    {
        String path = "/zhigang/getDemo.php";
        SocketAddress dest = new InetSocketAddress(this.host, this.port);
        socket.connect(dest);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedWriter = new BufferedWriter(streamWriter);

        bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");
        bufferedWriter.write("Host: " + this.host + "\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
        String line = null;
        while((line = bufferedReader.readLine())!= null)
        {
            Log.i("1",line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }

}