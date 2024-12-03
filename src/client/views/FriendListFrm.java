
package client.views;

import client.controller.Client;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

import client.model.User;

public class FriendListFrm extends javax.swing.JFrame {
    private List<User> listFriend;
    private boolean isClicked;
    DefaultTableModel defaultTableModel;

    public FriendListFrm() {
        initComponents();
        defaultTableModel = (DefaultTableModel) friendTable.getModel();
        this.setTitle("BattleShip Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        isClicked = false;
        requestUpdate();
        startThread();
    }
    
    public void stopAllThread() {
        isClicked = true;
    }

    public void startThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (Client.friendListFrm.isDisplayable() && !isClicked) {
                    try {
                        System.out.println("Xem danh sách bạn bè đang chạy!");
                        requestUpdate();
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void requestUpdate() {
        try {
            Client.socketHandle.write("view-friend-list,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void updateFriendList(List<User> friends) {
        listFriend = friends;
        defaultTableModel.setRowCount(0);
        ImageIcon icon;
        for (User friend : listFriend) {
            if (!friend.isOnline()) {
                icon = new ImageIcon("assets/icon/offline.png");
            } else if (friend.isPlaying()) {
                icon = new ImageIcon("assets/icon/swords-mini.png");
            } else {
                icon = new ImageIcon("assets/icon/swords-1-mini.png");
            }
            defaultTableModel.addRow(new Object[]{
                    "" + friend.getID(),
                    friend.getNickname(),
                    icon
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        frameLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Object[][] rows = {
        };
        String[] columns = {"ID","Nickname",""};
        DefaultTableModel model = new DefaultTableModel(rows, columns){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };
        friendTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        frameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(255, 255, 255));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("Danh sách bạn bè");

        closeButton.setText("X");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        friendTable.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        friendTable.setModel(model);
        friendTable.setRowHeight(60);
        friendTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(friendTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(closeButton))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(closeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        Client.closeView(Client.View.FRIEND_LIST);
        Client.openView(Client.View.HOMEPAGE);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void friendTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendTableMouseClicked
        try {
            if (friendTable.getSelectedRow() == -1) return;
            User friend = listFriend.get(friendTable.getSelectedRow());
            if (!friend.isOnline()) {
                throw new Exception("Người chơi không online");
            }
            if (friend.isPlaying()) {
                throw new Exception("Người chơi đang trong trận đấu");
            }
            isClicked = true;
            int res = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn thách đấu người bạn này không", "Xác nhận thách đầu", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                Client.closeAllViews();
                Client.openView(Client.View.GAME_NOTICE, "Thách đấu", "Đang chờ phản hồi từ đối thủ");
                Client.socketHandle.write("duel-request," + friend.getID());
            } else {
                isClicked = false;
                startThread();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_friendTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel frameLabel;
    private javax.swing.JTable friendTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}