package server;


import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
/**
 * Created by Makhobey Oleh on 6/1/16.
 * email: tajcig@ya.ru
 */



public class Chat {

    public static Map<Session,String> userUserMap = new HashMap<>();
    public static int nextUserNumber;
    public static void main(String[] args) {
        ipAddress("192.168.1.3");
        webSocket("/chat",ChatWebsocketHandler.class);
        init();
    }

    public static void broadcastMessage(String sender, String message){

         userUserMap.keySet()
                 .stream()
                 .filter(Session::isOpen)
                 .forEach(session -> {


                     try {
                         session.getRemote().sendString(sender + " : " + message);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 });
    }


}
