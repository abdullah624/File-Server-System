/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_server;

import client_server.Files;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abdullah
 */
public class ServerFiles extends javax.swing.JFrame {

    ///Variable declaration
    ArrayList<Files> myfiles;
    int id = -1;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static DefaultTableModel dModel;
    static int totalfile_row;
    int count = 0;

    /**
     * Creates new form ServerFiles
     */
    public ServerFiles() {
        initComponents();
    }

    ///Constructor to draw Server file list
    public ServerFiles(ArrayList<Files> myfiles, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        totalfile_row = myfiles.size();
        count = 1;
        this.myfiles = myfiles;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        dModel = (DefaultTableModel) serverFileList.getModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        serverFileList.setDefaultRenderer(String.class, centerRenderer);
        for (int columnIndex = 0; columnIndex < serverFileList.getColumnCount(); columnIndex++) {
            serverFileList.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }
        if (count > 1) {
            dModel.insertRow(5, new Object[]{myfiles.get(5).getId(), myfiles.get(5).getName()});
        }
        for (Files myfile : myfiles) {
            count++;
            dModel.addRow(new Object[]{myfile.getId(), myfile.getName()});
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        serverFileList = new javax.swing.JTable();
        downloadFile = new javax.swing.JButton();
        deleteFile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(700, 300));

        serverFileList.setAutoCreateRowSorter(true);
        serverFileList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "File Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        serverFileList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                serverFileListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(serverFileList);

        downloadFile.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        downloadFile.setText("Download");
        downloadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadFileActionPerformed(evt);
            }
        });

        deleteFile.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        deleteFile.setText("Delete");
        deleteFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFileActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("File List");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(downloadFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(deleteFile)
                .addGap(132, 132, 132))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downloadFile)
                    .addComponent(deleteFile))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ///Driver method for invoking Download option frame
    private void downloadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadFileActionPerformed
        if (serverFileList.getRowCount() != 0) {
            if (id != -1) {
                for (Files myFile : myfiles) {
                    if (myFile.getName() == dModel.getValueAt(id, 1)) {
                        JFrame jfPreview = createFrame(myFile.getName(), myFile.getData(), myFile.getFileExtension());
                        jfPreview.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File not selected!", "Alert", 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No files here to download.", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_downloadFileActionPerformed

     ///Driver method for invoking Deletion option frame
    private void deleteFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFileActionPerformed
        if (serverFileList.getRowCount() != 0) {
            if (id != -1) {
                for (Files myFile : myfiles) {
                    if (myFile.getName() == dModel.getValueAt(serverFileList.getSelectedRow(), 1)) {
                        JFrame jfRemove = frameToDelete(myFile.getName(), myFile.getData(), myFile.getFileExtension());
                        jfRemove.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File not selected!", "Alert", 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No files here to delete.", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_deleteFileActionPerformed

    ///File selection from server file list
    private void serverFileListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_serverFileListMouseClicked
        id = serverFileList.getSelectedRow();
    }//GEN-LAST:event_serverFileListMouseClicked

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFiles().setVisible(true);
            }
        });
    }
    //File Downloader Promt frame creation
    public static JFrame createFrame(final String fileName, final byte[] fileData, String fileExtension) {

        final JFrame jFrame = new JFrame("File Downloader");
        jFrame.setSize(600, 500);
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        
        JLabel jlPrompt = new JLabel("Are you sure you want to download '" + fileName + "'?");
        jlPrompt.setFont(new Font("Courgette", Font.BOLD, 15));
        jlPrompt.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Downloader confirmation buttons creation
        JButton jbDownload = new JButton("Download");
        jbDownload.setPreferredSize(new Dimension(110, 50));
        jbDownload.setFont(new Font("Courgette", Font.BOLD, 13));
        JButton jbCancel = new JButton("Cancel");
        jbCancel.setPreferredSize(new Dimension(100, 50));
        jbCancel.setFont(new Font("Courgette", Font.BOLD, 14));
        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(20, 0, 10, 0));
        jpButtons.add(jbDownload);
        jpButtons.add(jbCancel);
        
        //File preview for images while confirmation
        if (fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")
                || fileExtension.equalsIgnoreCase("gif") || fileExtension.equalsIgnoreCase("tiff")) {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }

        //Path selection for downloading
        jbDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Select File");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                File fileToDownload = null;
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fileToDownload = new File(chooser.getSelectedFile() + "/" + fileName);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                        fileOutputStream.write(fileData);
                        fileOutputStream.close();
                        jFrame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Directory not selected!", "Alert!", 2);
                }

            }
        });
        
        //Download cancelation
        jbCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });

        //Binding components with Panel and added to frame
        jPanel1.add(jlPrompt);
        jPanel2.add(jpButtons);
        jPanel3.add(jlFileContent);
        jPanel.add(jPanel1);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        jFrame.add(jPanel);
        return jFrame;

    }

    //File Remover Promt frame creation
    public static JFrame frameToDelete(final String fileName, final byte[] fileData, String fileExtension) {

        final JFrame jFrame = new JFrame("File Remover");
        jFrame.setSize(700, 500);
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jlPrompt = new JLabel("Are you sure you want to delete '" + fileName + "'?");
        jlPrompt.setFont(new Font("Courgette", Font.BOLD, 15));
        jlPrompt.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Remover confirmation buttons creation
        JButton jbDelete = new JButton("Delete");
        jbDelete.setPreferredSize(new Dimension(100, 50));
        jbDelete.setFont(new Font("Courgette", Font.BOLD, 15));
        JButton jbCancel = new JButton("Cancel");
        jbCancel.setPreferredSize(new Dimension(100, 50));
        jbCancel.setFont(new Font("Courgette", Font.BOLD, 15));
        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(20, 0, 10, 0));
        jpButtons.add(jbDelete);
        jpButtons.add(jbCancel);
        if (fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")
                || fileExtension.equalsIgnoreCase("gif") || fileExtension.equalsIgnoreCase("tiff")) {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }

        jbDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataOutputStream.writeUTF("remove");
                    dataOutputStream.writeUTF(fileName);
                    String back_message = dataInputStream.readUTF();
                    if (back_message.equals("file-deleted")) {
                        dModel.removeRow(serverFileList.getSelectedRow());
                    }
                    jFrame.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(ServerFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jbCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });

        jPanel1.add(jlPrompt);
        jPanel2.add(jpButtons);
        jPanel3.add(jlFileContent);
        jPanel.add(jPanel1);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        jFrame.add(jPanel);
        return jFrame;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteFile;
    private javax.swing.JButton downloadFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable serverFileList;
    // End of variables declaration//GEN-END:variables
}
