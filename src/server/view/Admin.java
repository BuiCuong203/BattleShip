package server.view;

import server.controller.Room;
import server.controller.Server;
import server.controller.ServerThread;
import server.dao.UserDAO;

public class Admin extends javax.swing.JFrame implements Runnable{
    private final UserDAO userDAO; 

    public Admin() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        threadRoomListView.setEditable(false);
        messageView.setEditable(false);
        userDAO = new UserDAO();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewThreadButton = new javax.swing.JButton();
        viewRoomListButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        threadRoomListView = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        messageView = new javax.swing.JTextArea();
        publishMessageButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        noticeTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        viewThreadButton.setText("Xem danh sách luồng");
        viewThreadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewThreadButtonActionPerformed(evt);
            }
        });

        viewRoomListButton.setText("Xem danh sách phòng");
        viewRoomListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewRoomListButtonActionPerformed(evt);
            }
        });

        threadRoomListView.setColumns(20);
        threadRoomListView.setRows(5);
        jScrollPane1.setViewportView(threadRoomListView);

        messageView.setColumns(20);
        messageView.setRows(5);
        jScrollPane2.setViewportView(messageView);

        publishMessageButton.setText("Phát thông báo");
        publishMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publishMessageButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 204, 255));

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ADMIN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        noticeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noticeTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(viewThreadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(viewRoomListButton)
                .addGap(93, 93, 93))
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(noticeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(publishMessageButton))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewThreadButton)
                    .addComponent(viewRoomListButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(publishMessageButton)
                    .addComponent(noticeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void viewThreadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewThreadButtonActionPerformed
        // TODO add your handling code here:
        StringBuilder res = new StringBuilder();
        String room;
        int i = 1;
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            if (serverThread.getRoom() == null)
                room = null;
            else room = "" + serverThread.getRoom().getId();
            if (serverThread.getUser() != null) {
                res.append(i).append(". Client-number: ").append(serverThread.getClientNumber()).append(", User-ID: ").append(serverThread.getUser().getID()).append(", Room: ").append(room).append("\n");
            } else {
                res.append(i).append(". Client-number: ").append(serverThread.getClientNumber()).append(", User-ID: null, Room: ").append(room).append("\n");
            } 
            i++;
        }
        threadRoomListView.setText(res.toString());        
    }//GEN-LAST:event_viewThreadButtonActionPerformed

    private void viewRoomListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewRoomListButtonActionPerformed
        // TODO add your handling code here:
        StringBuilder res = new StringBuilder();
        int i = 1;
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            Room room1 = serverThread.getRoom();
            String listUser = "List user ID: ";
            if (room1 != null) {
                if (room1.getNumberOfUser() == 1) {
                    listUser += room1.getUser1().getUser().getID();
                } else {
                    listUser += room1.getUser1().getUser().getID() + ", " + room1.getUser2().getUser().getID();
                }
                res.append(i).append(". Room_ID: ").append(room1.getId()).append(", Number of player: ").append(room1.getNumberOfUser()).append(", ").append(listUser).append("\n");
                i++;
            }
        }
        threadRoomListView.setText(res.toString());        
    }//GEN-LAST:event_viewRoomListButtonActionPerformed

    private void noticeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noticeTextFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10) {
            sendMessage();
        }
    }//GEN-LAST:event_noticeTextFieldKeyPressed

    private void publishMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishMessageButtonActionPerformed
        // TODO add your handling code here:
        sendMessage();
    }//GEN-LAST:event_publishMessageButtonActionPerformed

    private void sendMessage() {
        String message = noticeTextField.getText();
        if (message.isEmpty()) return;
        String temp = messageView.getText();
        temp += "Thông báo từ máy chủ : " + message + "\n";
        messageView.setText(temp);
        messageView.setCaretPosition(messageView.getDocument().getLength());
        Server.serverThreadBus.boardCast(-1, "chat-server,Thông báo từ máy chủ : " + message);
        noticeTextField.setText("");
    }

    public void addMessage(String message) {
        String tmp = messageView.getText();
        tmp = tmp + message + "\n";
        messageView.setText(tmp);
        messageView.setCaretPosition(threadRoomListView.getDocument().getLength());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea messageView;
    private javax.swing.JTextField noticeTextField;
    private javax.swing.JButton publishMessageButton;
    private javax.swing.JTextArea threadRoomListView;
    private javax.swing.JButton viewRoomListButton;
    private javax.swing.JButton viewThreadButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        new Admin().setVisible(true);
    }
}