package plu.blue.reversi.client.gui;

import com.google.firebase.database.DatabaseReference;
import plu.blue.reversi.client.firebase.FirebaseConnection;
import plu.blue.reversi.client.firebase.handlers.FirebaseChatHandler;
import plu.blue.reversi.client.firebase.handlers.FirebasePlayersHandler;
import plu.blue.reversi.client.firebase.listeners.FirebaseChatListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by kyleb on 5/7/2017.
 */
public class ChatPanel extends JPanel implements FirebaseChatListener{
    private static JTextArea chatArea;
    private static JTextField text;
    private static JPanel panel;
    private String gameName, playerName;

    //Creates a chat area for users to read messages
    public ChatPanel(){
        //panel
        panel = new JPanel();
        //Text field
        text = new JTextField(20);
        text.setBorder(new LineBorder(Color.BLACK));
        text.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textActionPerformed(e);
            }
        });

        //Chat Area
        chatArea = new JTextArea(10,100);
        chatArea.setBorder(new LineBorder(Color.BLACK));

        //Scroll
        JScrollPane scroll = new JScrollPane((chatArea));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatArea.setBackground(Color.WHITE);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scroll);
        panel.add(text);

        this.add(panel);

    }

    private void textActionPerformed(ActionEvent e){

        String msg = text.getText();
        chatArea.append(msg + "\n");
        text.setText("");

        onMessageAdded("player",msg);
    }

    public void setGameName(String gName){
        gameName = gName;
    }

    public void setPlayerName(String pName){
        playerName = pName;
    }


    @Override
    public void onMessageAdded(String playerName, String message) {
        FirebaseChatHandler firebasechat = new FirebaseChatHandler();

        firebasechat.addMessage(gameName, playerName, message);

    }



}
