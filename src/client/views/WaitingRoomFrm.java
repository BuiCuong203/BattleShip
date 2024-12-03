package client.views;

import client.controller.Client;
import java.awt.Color;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class WaitingRoomFrm extends javax.swing.JFrame {
    private boolean isOpenning;

    public WaitingRoomFrm() {
        initComponents();
        isOpenning = false;
        this.setTitle("BattleShip Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        loadingIcon.setIcon(new ImageIcon("assets/icon/loading2.gif"));
        outRoomButton.setIcon(new ImageIcon("assets/icon/door_exit.png"));
        roomPasswordLabel.setVisible(false);
    }
    
    public void setRoomName(String roomName) {
        roomNameLabel.setText("Phòng " + roomName);
    }

    public void setRoomPassword(String password) {
        roomPasswordLabel.setText("Mật khẩu phòng: " + password);
        roomPasswordLabel.setVisible(true);
    }

    public void showFoundCompetitor() {
        isOpenning = true;
        pendingMessageLabel.setText("Đã tìm thấy đối thủ, đang vào phòng");
        pendingMessageLabel.setForeground(Color.BLUE);
        outRoomButton.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        roomNameLabel = new javax.swing.JLabel();
        roomPasswordLabel = new javax.swing.JLabel();
        loadingIcon = new javax.swing.JLabel();
        pendingMessageLabel = new javax.swing.JLabel();
        outRoomButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        roomNameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        roomNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roomNameLabel.setText("Phòng {}");

        roomPasswordLabel.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        roomPasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomPasswordLabel.setText("Mật khẩu:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roomNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(roomPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(roomNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roomPasswordLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pendingMessageLabel.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        pendingMessageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pendingMessageLabel.setText("Đang chờ người chơi khác vào phòng");

        outRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outRoomButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pendingMessageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(outRoomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(loadingIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loadingIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(pendingMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outRoomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void outRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outRoomButtonActionPerformed
        if (isOpenning) return;
        try {
            Client.closeView(Client.View.WAITING_ROOM);
            Client.openView(Client.View.HOMEPAGE);
            Client.socketHandle.write("cancel-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_outRoomButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel loadingIcon;
    private javax.swing.JButton outRoomButton;
    private javax.swing.JLabel pendingMessageLabel;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JLabel roomPasswordLabel;
    // End of variables declaration//GEN-END:variables
}