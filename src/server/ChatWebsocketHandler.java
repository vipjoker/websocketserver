package server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Created by Makhobey Oleh on 6/2/16.
 * email: tajcig@ya.ru
 */

@WebSocket
public class ChatWebsocketHandler {
    private String sender,msg;

    @OnWebSocketConnect
    public void onConnect(Session user)throws Exception{
        String username = "User" + Chat.nextUserNumber++;
        Chat.userUserMap.put(user,username);
        Chat.broadcastMessage(username,"Hello");


        System.out.println("connected");
    }


    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason){
        String userName = Chat.userUserMap.get(user);
        Chat.userUserMap.remove(user);
        Chat.broadcastMessage(sender="Server",msg=userName);
        System.out.println("Closed");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message){
        Chat.broadcastMessage(sender=Chat.userUserMap.get(user),msg = message);

        System.out.println("message");
    }
}
