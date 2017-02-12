package main;

import myHttp.server.HttpServer;

import java.io.IOException;

/**
 * Created by wyy on 17-2-9.
 */
public class Main {
    public static  void main (String args []){
        try {
            new HttpServer(1208);
        }catch (IOException e ) {
            e.printStackTrace();
        }



    }
}
