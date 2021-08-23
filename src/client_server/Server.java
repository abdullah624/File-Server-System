/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdullah
 */
public class Server extends javax.swing.JFrame {
    
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static String[] filesList;

    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        startServer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        notificationArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(1350, 50));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Server");

        startServer.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        startServer.setText("Clear");
        startServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerActionPerformed(evt);
            }
        });

        notificationArea.setColumns(20);
        notificationArea.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        notificationArea.setRows(5);
        notificationArea.setText("  Waiting for Client...");
        jScrollPane1.setViewportView(notificationArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startServer, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startServer)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerActionPerformed
        notificationArea.setText("");
    }//GEN-LAST:event_startServerActionPerformed

    /**
     * @param args the command line arguments
     */
    
    //Main method starts here...
    public static void main(String args[]) {

        try {

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Server().setVisible(true);
                }
            });
            serverSocket = new ServerSocket(1111);
            socket = serverSocket.accept();
            notificationArea.setText("\n  Client connected!");
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String fileName = dataInputStream.readUTF();
                if (fileName.equals("files-on-server")) {
                    File fileDir = new File("/run/media/abdullah/Projects/NetBeansProjects/File Server System/src/UploadedFiles/");
                    File[] listOfFiles = fileDir.listFiles();
                    dataOutputStream.writeUTF(String.valueOf(listOfFiles.length));
                    FileInputStream fin;
                    for (int i = 0; i < listOfFiles.length; i++) {
                        fin = new FileInputStream(listOfFiles[i]);
                        String singleFileName = listOfFiles[i].getName();
                        byte[] fileNameBytes = singleFileName.getBytes();
                        byte[] fileBytes = new byte[(int) listOfFiles[i].length()];
                        fin.read(fileBytes);
                        dataOutputStream.writeInt(fileNameBytes.length);
                        dataOutputStream.write(fileNameBytes);
                        dataOutputStream.writeInt(fileBytes.length);
                        dataOutputStream.write(fileBytes);
                        fin.close();
                    }
                } else if (fileName.equals("remove")) {
                    String fileNameToDelete = dataInputStream.readUTF();
                    File removeFile = new File("/run/media/abdullah/Projects/NetBeansProjects/File Server System/src/UploadedFiles/" + fileNameToDelete);
                    if (removeFile.delete()) {
                        notificationArea.append("\n  '"+fileNameToDelete + "' successfully deleted.\n");
                        dataOutputStream.writeUTF("file-deleted");
                    } else {
                        notificationArea.append("\n  Something went wrong!\n");
                    }
                } else {
                    File f = new File("/run/media/abdullah/Projects/NetBeansProjects/File Server System/src/UploadedFiles/" + fileName);
                    FileOutputStream fout = new FileOutputStream(f);
                    if (!fileName.isEmpty()) {
                        int ch;
                        String temp;
                        do {
                            temp = dataInputStream.readUTF();
                            ch = Integer.parseInt(temp);
                            if (ch != -1) {
                                fout.write(ch);
                            }
                        } while (ch != -1);
                        fout.close();
                        if (ch == -1) {
                            notificationArea.append("\n  '" + fileName + "'  successfully received.\n");
                            }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            notificationArea.append("\n  Client disconnected!\n");
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea notificationArea;
    private javax.swing.JButton startServer;
    // End of variables declaration//GEN-END:variables
}
