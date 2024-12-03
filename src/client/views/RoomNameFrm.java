package client.views;

import client.controller.Client;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class RoomNameFrm extends javax.swing.JFrame {

    public RoomNameFrm() {
        initComponents();
        this.setTitle("BattleShip Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        findingRoomLabel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        frameTitle = new javax.swing.JLabel();
        roomIdLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        roomIdValue = new javax.swing.JTextField();
        passwordValue = new javax.swing.JTextField();
        passwordNotificationLabl = new javax.swing.JLabel();
        goToRoomButton = new javax.swing.JButton();
        findingRoomLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        frameTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        frameTitle.setForeground(new java.awt.Color(255, 255, 255));
        frameTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameTitle.setText("Vào phòng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(frameTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roomIdLabel.setText("Nhập mã phòng");

        passwordLabel.setText("Mật khẩu phòng");

        passwordNotificationLabl.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        passwordNotificationLabl.setText("Nếu phòng không có mật khẩu hãy để trống");

        goToRoomButton.setText("Vào phòng");
        goToRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToRoomButtonActionPerformed(evt);
            }
        });

        findingRoomLabel.setForeground(new java.awt.Color(0, 51, 255));
        findingRoomLabel.setText("Đang tìm kiếm phòng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roomIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(roomIdValue)
                            .addComponent(passwordValue, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(passwordNotificationLabl))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(goToRoomButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(findingRoomLabel)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomIdLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordNotificationLabl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goToRoomButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(findingRoomLabel)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void goToRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToRoomButtonActionPerformed
        String roomName = roomIdValue.getText();
        if (roomName.isEmpty())
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập mã phòng");
        try {
            String password = " ";
            if (!passwordValue.getText().isEmpty()) {
                password = passwordValue.getText();
            }
            Client.socketHandle.write("go-to-room," + roomName + "," + password);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_goToRoomButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel findingRoomLabel;
    private javax.swing.JLabel frameTitle;
    private javax.swing.JButton goToRoomButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel passwordNotificationLabl;
    private javax.swing.JTextField passwordValue;
    private javax.swing.JLabel roomIdLabel;
    private javax.swing.JTextField roomIdValue;
    // End of variables declaration//GEN-END:variables
}