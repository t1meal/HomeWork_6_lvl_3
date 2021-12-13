package gb.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServMain {
    private Vector <ClientHandler> clients;

    public ServMain() {
        Socket socket = null;
        ServerSocket serv = null;
        clients = new Vector<>();

        try  {
            AuthService.connectDB();
            serv = new ServerSocket(8990);
            System.out.println("Server is running!");

            while (true){
                socket = serv.accept();
                System.out.println("Client has connect!");

                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                serv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnectDB();


        }
    }

    public void subscribe (ClientHandler client){
        clients.add(client);
    }

    public void unsubscribe (ClientHandler client){
        clients.remove(client);
    }

    public void broadcastMsg(ClientHandler from, String msg){
        for (ClientHandler o : clients) {
            if (!o.checkBlackList(from.getNick())){
                o.sendMsg(msg);
            }
        }
    }
    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                o.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате");
    }

    public Vector<ClientHandler> getClients() {
        return clients;
    }

}
