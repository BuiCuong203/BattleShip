package client.views;

import client.controller.Client;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import client.model.User;

public class CompetitorInfoFrm extends javax.swing.JFrame {
    private boolean isFriend;
    private User user;

    public CompetitorInfoFrm(User user) {
        try {
            initComponents();
            this.user = user;
            this.setTitle("BattleShip Game");
            this.setResizable(false);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            jLabel6.setIcon(new ImageIcon("assets/icon/user.png"));
            int NumberOfGame = Client.user.getNumberOfGame();
            int NumberOfWin = Client.user.getNumberOfWin();
            int NumberOfDraw = Client.user.getNumberOfDraw();
            int NumberOfLose = NumberOfGame - NumberOfWin - NumberOfDraw;
            nicknameValue.setText(user.getNickname());
            numberOfGameValue.setText(Integer.toString(NumberOfGame));
            numberOfWinValue.setText(Integer.toString(NumberOfWin));
            numberOfDrawValue.setText(Integer.toString(NumberOfDraw));
            numberOfLoseValue.setText(Integer.toString(NumberOfLose));
            rankValue.setText("" + user.getRank());
            if (user.getNumberOfGame() == 0) {
                winRatioValue.setText("-");
            } else {
                winRatioValue.setText(String.format("%.2f", (float) NumberOfWin / NumberOfGame * 100) + "%");
            }
            markValue.setText(Integer.toString(NumberOfWin * 3 + NumberOfDraw));
            Client.socketHandle.write("check-friend," + user.getID());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        frameLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        nicknameLabel = new javax.swing.JLabel();
        nicknameValue = new javax.swing.JLabel();
        numberOfGameValue = new javax.swing.JLabel();
        numberOfGameLabel = new javax.swing.JLabel();
        numberOfWinLabel = new javax.swing.JLabel();
        numberOfWinValue = new javax.swing.JLabel();
        numberOfDrawLabel = new javax.swing.JLabel();
        numberOfDrawValue = new javax.swing.JLabel();
        winRatioLabel = new javax.swing.JLabel();
        winRatioValue = new javax.swing.JLabel();
        markLabel = new javax.swing.JLabel();
        markValue = new javax.swing.JLabel();
        rankLabel = new javax.swing.JLabel();
        rankValue = new javax.swing.JLabel();
        isFriendLabel = new javax.swing.JLabel();
        numberOfLoseLabel = new javax.swing.JLabel();
        numberOfLoseValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));

        frameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(255, 255, 255));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("Thông tin đối thủ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(frameLabel)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        nicknameLabel.setText("Tên người chơi");

        nicknameValue.setText("{nickname}");

        numberOfGameValue.setText("{sovachoi}");

        numberOfGameLabel.setText("Số ván chơi");

        numberOfWinLabel.setText("Số ván thắng");

        numberOfWinValue.setText("{sovanthang}");

        numberOfDrawLabel.setText("Số ván hòa");

        numberOfDrawValue.setText("{sovanhoa}");

        winRatioLabel.setText("Tỉ lệ thắng");

        winRatioValue.setText("{tillethang}");

        markLabel.setText("Điểm");

        markValue.setText("{diem}");

        rankLabel.setText("Thứ hạng");

        rankValue.setText("{thuhang}");

        isFriendLabel.setForeground(new java.awt.Color(0, 51, 255));
        isFriendLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        isFriendLabel.setText("Các bạn hiện đang là bạn bè");

        numberOfLoseLabel.setText("Số ván thua");

        numberOfLoseValue.setText("{sovanthua}");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isFriendLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(winRatioLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rankLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numberOfWinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(markLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numberOfDrawLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nicknameLabel)
                                    .addComponent(numberOfLoseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(numberOfWinValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(numberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(rankValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(markValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(nicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(numberOfDrawValue, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(numberOfLoseValue, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(winRatioValue, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nicknameLabel)
                    .addComponent(nicknameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfGameLabel)
                    .addComponent(numberOfGameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfWinLabel)
                    .addComponent(numberOfWinValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfDrawLabel)
                    .addComponent(numberOfDrawValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfLoseLabel)
                    .addComponent(numberOfLoseValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winRatioLabel)
                    .addComponent(winRatioValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(markLabel)
                    .addComponent(markValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rankLabel)
                    .addComponent(rankValue))
                .addGap(28, 28, 28)
                .addComponent(isFriendLabel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (isFriend) {
            JOptionPane.showMessageDialog(rootPane, "Bạn và đối thủ đang là bạn bè");
        } else {
            int res = JOptionPane.showConfirmDialog(rootPane, "Bạn đồng ý gửi lời mời kết bạn tới đối thủ chứ", "Xác nhận yêu cầu kết bạn", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                try {
                    Client.socketHandle.write("make-friend," + user.getID());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void checkFriend(boolean isFriend) {
        this.isFriend = isFriend;
        if (isFriend) {
            jButton1.setIcon(new ImageIcon("assets/icon/friendship.png"));
            jButton1.setToolTipText("Bạn bè");
            isFriendLabel.setText("Bạn bè");
        } else {
            jButton1.setIcon(new ImageIcon("assets/icon/add-friend.png"));
            jButton1.setToolTipText("Click để gửi yêu cầu kết bạn");
            isFriendLabel.setText("Kết bạn để chơi cùng nhau dễ dàng hơn");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frameLabel;
    private javax.swing.JLabel isFriendLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel markLabel;
    private javax.swing.JLabel markValue;
    private javax.swing.JLabel nicknameLabel;
    private javax.swing.JLabel nicknameValue;
    private javax.swing.JLabel numberOfDrawLabel;
    private javax.swing.JLabel numberOfDrawValue;
    private javax.swing.JLabel numberOfGameLabel;
    private javax.swing.JLabel numberOfGameValue;
    private javax.swing.JLabel numberOfLoseLabel;
    private javax.swing.JLabel numberOfLoseValue;
    private javax.swing.JLabel numberOfWinLabel;
    private javax.swing.JLabel numberOfWinValue;
    private javax.swing.JLabel rankLabel;
    private javax.swing.JLabel rankValue;
    private javax.swing.JLabel winRatioLabel;
    private javax.swing.JLabel winRatioValue;
    // End of variables declaration//GEN-END:variables
}