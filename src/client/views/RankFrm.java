package client.views;

import client.controller.Client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import client.model.User;

public class RankFrm extends javax.swing.JFrame {

    private final DefaultTableModel tableModel;
    private List<User> listUserStatics;
    private final List<String> rankSrc;

    public RankFrm() {
        initComponents();
        this.setTitle("BattleShip Game");
        tableModel = (DefaultTableModel) rankTextArea.getModel();
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        rankSrc = new ArrayList<>();
        rankSrc.add("rank-gold");
        rankSrc.add("rank-sliver");
        rankSrc.add("bronze-rank");
        for (int i = 0; i < 7; i++) {
            rankSrc.add("nomal-rank");
        }
        try {
            Client.socketHandle.write("get-rank-charts,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        // Lấy mô hình cột của bảng
        javax.swing.table.TableColumnModel columnModel = rankTextArea.getColumnModel();

        // Điều chỉnh độ rộng cho từng cột
        columnModel.getColumn(0).setPreferredWidth(50);  // Cột "STT"
        columnModel.getColumn(1).setPreferredWidth(150); // Cột "Nickname"
        columnModel.getColumn(2).setPreferredWidth(100); // Cột "Rank"
        columnModel.getColumn(3).setPreferredWidth(80);  // Cột "Điểm số"
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        frameLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Object[][] rows = {
        };
        String[] columns = {"STT","Nickname","Điểm số","Rank"};
        DefaultTableModel model = new DefaultTableModel(rows, columns){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return Integer.class;
                    case 3: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };
        rankTextArea = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        frameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        frameLabel.setForeground(new java.awt.Color(255, 255, 255));
        frameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        frameLabel.setText("Bảng xếp hạng");

        rankTextArea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rankTextArea.setModel(model);
        rankTextArea.setFillsViewportHeight(true);
        rankTextArea.setRowHeight(62);
        rankTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rankTextAreaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(rankTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(frameLabel)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
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

    private void rankTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rankTextAreaMouseClicked
        if (rankTextArea.getSelectedRow() == -1) {
            return;
        }
        if (listUserStatics.get(rankTextArea.getSelectedRow()).getID() == Client.user.getID()) {
            JOptionPane.showMessageDialog(rootPane, "Thứ hạng của bạn là " + (rankTextArea.getSelectedRow() + 1));
            return;
        }
        Client.openView(Client.View.COMPETITOR_INFO, listUserStatics.get(rankTextArea.getSelectedRow()));
    }//GEN-LAST:event_rankTextAreaMouseClicked

    public void setDataToTable(List<User> users) {
        this.listUserStatics = users;
        tableModel.setRowCount(0);
        int i = 0;
        for (User user : listUserStatics) {
            tableModel.addRow(new Object[]{
                i + 1,
                user.getNickname(),
                user.getNumberOfGame() * 3,
                new ImageIcon("assets/icon/" + rankSrc.get(i) + ".png")
            });
            i++;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frameLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable rankTextArea;
    // End of variables declaration//GEN-END:variables
}
