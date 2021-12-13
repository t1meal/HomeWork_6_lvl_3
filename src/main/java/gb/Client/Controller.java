package gb.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    Button sendButton;
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    HBox bottomPanel;
    @FXML
    HBox upperPanel;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    String nick;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8990;
    private boolean isAuthorized;

    public void tryToAuth(ActionEvent actionEvent) {              // метод первичной аунтификации
        if(socket == null || socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connect () {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            stageAuthorized(false);                     // начальное значение авторизации

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){                             // цикл проверки авторизации нового пользователя
                            String str = in.readUTF();
                            if (str.startsWith("/authOk")){
                                stageAuthorized(true);

                                String nick = in.readUTF();         // запрос ника для создания истории
                                loadHistory(nick);
                                break;

                            } else {
                                textArea.appendText(str + "\n");
                            }

                        }

                        while (true) {                          // основной цикл общения
                            String str = in.readUTF();
                            if(str.equals("/clientClosed")){
                                textArea.clear();
                                break;
                            }
                            textArea.appendText(str + "\n");
                            saveHistory();
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stageAuthorized(false);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHistory() {
        try (DataOutputStream fos = new DataOutputStream(new FileOutputStream ("history_" + nick + ".txt"))) {
            fos.writeUTF(textArea.getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory(String nick) {
        this.nick = nick;
        int length = 100;
        List<String> historyList = new ArrayList<>();
        File history = new File("history_" + nick + ".txt");
        if (!history.exists()){
            try {
                history.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileInputStream fis = new FileInputStream(history);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;

            while ((line = br.readLine()) != null){
                historyList.add(line);
            }

            if (historyList.size() >= length){
                for (int i = 0; i < length; i++) {
                    textArea.appendText(historyList.get(i) + "\n");
                }
            } else {
                for (int i = 0; i < historyList.size(); i++) {
                    textArea.appendText(historyList.get(i) + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stageAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if (!isAuthorized){
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        } else{
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    public void sendMsgApp() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
