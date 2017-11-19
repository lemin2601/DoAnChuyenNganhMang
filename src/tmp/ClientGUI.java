package tmp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class ClientGUI extends javax.swing.JFrame {

    Matrix a, b;

    public ClientGUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtA = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtB = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtnA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtmA = new javax.swing.JTextField();
        btnCustomA = new javax.swing.JButton();
        btnRanA = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtnB = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtmB = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnRanB = new javax.swing.JButton();
        cBOpera = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtIpServer = new javax.swing.JTextField();
        btnRequest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1000));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(1050, 500));
        setSize(new java.awt.Dimension(1050, 500));

        jPanel2.setLayout(new java.awt.GridLayout(3, 1));

        txtA.setColumns(20);
        txtA.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        txtA.setRows(5);
        jScrollPane1.setViewportView(txtA);

        jPanel2.add(jScrollPane1);

        txtB.setColumns(20);
        txtB.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        txtB.setRows(5);
        jScrollPane3.setViewportView(txtB);

        jPanel2.add(jScrollPane3);

        txtResult.setColumns(20);
        txtResult.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        txtResult.setRows(5);
        jScrollPane2.setViewportView(txtResult);

        jPanel2.add(jScrollPane2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jLabel4.setText("Matrix A:");
        jPanel1.add(jLabel4);

        txtnA.setColumns(3);
        txtnA.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnA.setText("3");
        jPanel1.add(txtnA);

        jLabel2.setText("x");
        jPanel1.add(jLabel2);

        txtmA.setColumns(3);
        txtmA.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtmA.setText("3");
        jPanel1.add(txtmA);

        btnCustomA.setText("CustomA");
        btnCustomA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomAActionPerformed(evt);
            }
        });
        jPanel1.add(btnCustomA);

        btnRanA.setText("Random A");
        btnRanA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanAActionPerformed(evt);
            }
        });
        jPanel1.add(btnRanA);

        jLabel5.setText("Matrix B:");
        jPanel1.add(jLabel5);

        txtnB.setColumns(3);
        txtnB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnB.setText("3");
        jPanel1.add(txtnB);

        jLabel3.setText("x");
        jPanel1.add(jLabel3);

        txtmB.setColumns(3);
        txtmB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtmB.setText("3");
        jPanel1.add(txtmB);

        jButton2.setText("CustomB");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        btnRanB.setText("Random B");
        btnRanB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanBActionPerformed(evt);
            }
        });
        jPanel1.add(btnRanB);

        cBOpera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "x" }));
        jPanel1.add(cBOpera);

        jLabel1.setText("IP server");
        jPanel1.add(jLabel1);

        txtIpServer.setColumns(12);
        txtIpServer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIpServer.setText("192.168.2.100");
        txtIpServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIpServerActionPerformed(evt);
            }
        });
        jPanel1.add(txtIpServer);

        btnRequest.setText("Request");
        btnRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestActionPerformed(evt);
            }
        });
        jPanel1.add(btnRequest);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIpServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIpServerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIpServerActionPerformed

    private void btnRanAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanAActionPerformed
        int n = Integer.parseInt(txtnA.getText());
        int m = Integer.parseInt(txtmA.getText());
        this.a = Lib.RandomMatrix(n, m);
        txtA.setText(a.toString());
    }//GEN-LAST:event_btnRanAActionPerformed

    private void btnRanBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanBActionPerformed
        int n = Integer.parseInt(txtnB.getText());
        int m = Integer.parseInt(txtmB.getText());
        this.b = Lib.RandomMatrix(n, m);
        txtB.setText(b.toString());
    }//GEN-LAST:event_btnRanBActionPerformed

    private void btnRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestActionPerformed
        String opera = cBOpera.getSelectedItem().toString();
        String host = txtIpServer.getText();
        txtResult.setText("");
        try {
            // Connect to remote object
            System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
            Registry r = LocateRegistry.getRegistry(host, 8808);
            InterfServer server = (InterfServer) r.lookup("SERVER_FOR_CLIENT");
            Matrix result = server.execute(a, b, opera);
            if (result == null) {
                JOptionPane.showMessageDialog(this, "Server don't have any ClientWork");
                return;
            }
            txtResult.setText(result.toString());
        } catch (RemoteException | NotBoundException ex) {
            JOptionPane.showMessageDialog(this, "Have Error!!!- can't connect to server");
        }
    }//GEN-LAST:event_btnRequestActionPerformed

    private void btnCustomAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomAActionPerformed
        try {
            int hang = Integer.parseInt(txtnA.getText());
            int cot = Integer.parseInt(txtmA.getText());
            int i = 0, j = 0;
            Matrix matrix = new Matrix(hang, cot);
            for (String str : txtA.getText().split("\n")) {
                j = 0;
                for (String value : str.trim().replaceAll("\\s+", " ").split(" ")) {
                    matrix.setValue(i, j, Integer.parseInt(value));
                    j++;
                }
                i++;
            }
            this.a = matrix;
            JOptionPane.showMessageDialog(this, "Custom Matrix A success.");
            txtA.setText(a.toString());
        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Have Error!");
        }

    }//GEN-LAST:event_btnCustomAActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            int hang = Integer.parseInt(txtnB.getText());
            int cot = Integer.parseInt(txtmB.getText());
            int i = 0, j = 0;
            Matrix matrix = new Matrix(hang, cot);
            for (String str : txtB.getText().split("\n")) {
                j = 0;
                for (String value : str.trim().replaceAll("\\s+", " ").split(" ")) {
                    matrix.setValue(i, j, Integer.parseInt(value));
                    j++;
                }
                i++;
            }
            this.b = matrix;
            JOptionPane.showMessageDialog(this, "Custom Matrix B success.");
            txtB.setText(b.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Have Error!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomA;
    private javax.swing.JButton btnRanA;
    private javax.swing.JButton btnRanB;
    private javax.swing.JButton btnRequest;
    private javax.swing.JComboBox<String> cBOpera;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea txtA;
    private javax.swing.JTextArea txtB;
    private javax.swing.JTextField txtIpServer;
    private javax.swing.JTextArea txtResult;
    private javax.swing.JTextField txtmA;
    private javax.swing.JTextField txtmB;
    private javax.swing.JTextField txtnA;
    private javax.swing.JTextField txtnB;
    // End of variables declaration//GEN-END:variables
}
