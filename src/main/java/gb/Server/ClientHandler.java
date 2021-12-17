package gb.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ClientHandler {
    private ServMain serv;
    private String nick;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    File history;
    private List<String> blacklist;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Logger logger;


    public ClientHandler(ServMain serv, Socket socket) {

        try {
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.blacklist = new ArrayList<>();
            this.history = new File("history_" + this.nick + ".txt");
            this.logger = LogManager.getLogger();



            executorService.execute(() -> {
                try {
                    while (true){                                   // цикл взаимодействия с авторизацией клиента
                        String str = in.readUTF();
                        if (str.startsWith("/auth")){
                            String[] tokens = str.split(" ");
                            String currentNick = AuthService.getNickFromLogAndPass(tokens[1], tokens[2]);
                            if (currentNick != null){
                                if (!nickIsBusy(currentNick)){
                                    sendMsg("/authOk");
                                    nick = currentNick;
                                    serv.subscribe(ClientHandler.this);

                                    sendMsg(nick);          // отправка ника клиенту
                                    break;

                                } else {
                                    sendMsg("Login is busy!");
                                }
                            } else {
                                sendMsg("Login and/or Pass is incorrect!");
                            }
                        }
                    }

                    while (true) {
                        try {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    out.writeUTF("/clientIsClosed");
//                                    System.out.println("Client is disconnect!");
                                    logger.info("Client is disconnect!");
                                    break;
                                }
                                if (str.startsWith("/w ")) { // /w nick3 lsdfhldf sdkfjhsdf wkerhwr
                                    String[] tokens = str.split(" ", 3);
                                    String m = str.substring(tokens[1].length() + 4);
                                    serv.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                                if (str.startsWith("/blacklist ")) { // /blacklist nick3
                                    String[] tokens = str.split(" ");
                                    blacklist.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                }
                            } else {
                                serv.broadcastMsg(ClientHandler.this, nick + ": " + str);
                            }
//                            System.out.println("Client: " + str);
                            logger.info("Client: " + str);
                        } catch (IOException e) {
//                            System.out.println("Client is disconnected!");
                            logger.info("Client is disconnect!");
                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    serv.unsubscribe(ClientHandler.this);

                    executorService.shutdown();
                    try {
                        if (!executorService.awaitTermination(500, TimeUnit.MILLISECONDS)){
                            executorService.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        executorService.shutdownNow();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(){
        try {
            out.writeUTF("/end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public boolean nickIsBusy (String currantNick){
        for (ClientHandler e: serv.getClients() ) {
            if (e.getNick().equals(currantNick)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkBlackList (String nick){
        return blacklist.contains(nick);
    }
}
