package MyHttp.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wyy on 17-2-9.
 */
public class HttpServer {
    public HttpServer(int port) throws IOException{
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String line = null;
        int index = 0 ;
        line = input.readLine();
        while(line!=null)
        {
            System.out.print(index);
            System.out.println(line);
            if(line.length() == 0 )
                break;
            index ++ ;
            line = input.readLine();
        }
        output.write("hello world");
        output.flush();
        input.close();
        output.close();

    }
}
