package client.views;

import client.controller.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FindRoomFrm extends javax.swing.JFrame {
    private Timer timer;
    private boolean found;
    
    public FindRoomFrm() {
        initComponents();
        this.setTitle("BattleShip Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        jLabel5.setIcon(new ImageIcon("assets/icon/loading1.gif"));
        outButton.setIcon(new ImageIcon("assets/icon/door_exit.png"));
        jProgressBar1.setValue(70);
        found = false;
        startFind();
        sendFindRequest();
    }
    
    public void stopAllThread() {
        timer.stop();
    }

    public void startFind() {
        foundLabel.setVisible(false);
        jLabel5.setVisible(false);
        timer = new Timer(1000, new ActionListener() {
            int count = 20;

            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                if (count >= 0) {
                    if (count >= 10)
                        countDouwnTimeLabel.setText("00:" + count);
                    else
                        countDouwnTimeLabel.setText("00:0" + count);
                    jProgressBar1.setValue(Math.round((float) count / 20 * 100));
                } else {
                    ((Timer) (e.getSource())).stop();
                    try {
                        Client.socketHandle.write("cancel-room,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }
                    int res = JOptionPane.showConfirmDialog(rootPane, "Tìm kiếm thất bại, bạn muốn thử lại lần nữa chứ?");
                    if (res == JOptionPane.YES_OPTION) {
                        startFind();
                        sendFindRequest();
                    } else {
                        Client.closeView(Client.View.FIND_ROOM);
                        Client.openView(Client.View.HOMEPAGE);
                    }
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void sendFindRequest() {
        try {
            Client.socketHandle.write("quick-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void showFoundRoom() {
        found = true;
        timer.stop();
        foundLabel.setVisible(true);
        jLabel5.setVisible(true);
        findingLabel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        countDouwnTimeLabel = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        findingLabel = new javax.swing.JLabel();
        foundLabel = new javax.swing.JLabel();
        outButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        frameLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        countDouwnTimeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        countDouwnTimeLabel.setText("00:20");

        findingLabel.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        findingLabel.setText("Đang tìm đối thủ");

        foundLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foundLabel.setForeground(new java.awt.Color(0, 51, 204));
        foundLabel.setText("Đã tìm thấy đối thủ, đang vào phòng");

        outButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        frameLabel.setBackground(new java.awt.Color(255, 255, 255));
        frameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(255, 255, 255));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("Tìm phòng nhanh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(frameLabel)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(167, 167, 167)
                        .addComponent(outButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(countDouwnTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(188, 188, 188))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(findingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(149, 149, 149))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(89, 89, 89)
                                    .addComponent(foundLabel)
                                    .addGap(99, 99, 99))))
                        .addGap(42, 42, 42))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(countDouwnTimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(findingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(foundLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void outButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outButtonActionPerformed
        if (found) return;
        try {
            Client.socketHandle.write("cancel-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        timer.stop();
        Client.closeView(Client.View.FIND_ROOM);
        Client.openView(Client.View.HOMEPAGE);
    }//GEN-LAST:event_outButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel countDouwnTimeLabel;
    private javax.swing.JLabel findingLabel;
    private javax.swing.JLabel foundLabel;
    private javax.swing.JLabel frameLabel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton outButton;
    // End of variables declaration//GEN-END:variables
}