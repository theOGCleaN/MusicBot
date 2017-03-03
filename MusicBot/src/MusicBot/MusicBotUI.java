
package MusicBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JFileChooser;


public class MusicBotUI extends javax.swing.JFrame {
    
    public static String path = null;


    public MusicBotUI() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        browseBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        browseBtn.setText("Browse");
        browseBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browseBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(browseBtn)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(212, Short.MAX_VALUE)
                .addComponent(browseBtn)
                .addGap(63, 63, 63))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browseBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseBtnMouseClicked
        browseBtnClick();
    }//GEN-LAST:event_browseBtnMouseClicked
 
    public static boolean browseBtnClick(){
        try{
            JFileChooser fileBrowser = new JFileChooser();
            fileBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileBrowser.showOpenDialog(fileBrowser);
            if (result == JFileChooser.APPROVE_OPTION){
                path = fileBrowser.getSelectedFile().getAbsolutePath();
                return true;
            }
        }catch(Exception e){
            System.out.println("You must select a folder as music library!");
        }
        return false;
    }
    
    public static void writePath(File pathFile){
        try{
            PrintWriter pw = new PrintWriter(pathFile);
            pw.write(path);
            pw.close();
        }catch(Exception e){
            
        }
    }
    
    public static String readPath(File pathFile){
        try{
            Scanner key = new Scanner(pathFile);
            path = key.nextLine();
            key.close();
        }catch(Exception e){
            
        }
        return path;
    }
    
    // Load music library into linkedlist
    public static LinkedList<File> getMusic(LinkedList<File> library, String path){
        File libDir = new File(path);
        File[] tempArray = libDir.listFiles();
        library = new LinkedList<>();
        for (File x : tempArray){
            if (x.isFile() && x.getName().toLowerCase().contains(".mp3")){
                library.add(x);
            }
        }

        return library;
    }

    // Copy music library into a file
    public static void createMusicIndex(LinkedList<File> library, File indexFile){
        try{
            PrintWriter pw = new PrintWriter(indexFile.getAbsolutePath());
            for (Object file : library){
                pw.write(file.toString());
            }
            pw.close();
        }catch(FileNotFoundException ex){
            System.out.println("File could not be located!");
        }
    }

    public static LinkedList<File> readMusicIndex(LinkedList<File> library, File indexFile){
        library = new LinkedList<>();
        try{
            Scanner key = new Scanner(indexFile.getName());
            while(key.hasNextLine()){
                library.add(new File(key.nextLine()));
            }
            key.close();
        }catch(Exception e){
            System.out.println(e + "Index file does not exist!");
        }
        return library;
    }

    // List all elements of linkedlist filesystem
    public static void listFiles(LinkedList<File> library){
        for (File file : library){
            System.out.println(file);
        }
    }


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
            java.util.logging.Logger.getLogger(MusicBotUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MusicBotUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MusicBotUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MusicBotUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicBotUI().setVisible(true);
            }
        });
        
        LinkedList<File> library = null;
        File indexFile = new File("musicIndex.dat");
        File pathFile = new File("path.dat");
        
        while(path == null){
            if (pathFile.exists()){
                path = readPath(pathFile);
            }else{
                if (indexFile.exists()){
                    indexFile.delete();
                }
                if (browseBtnClick()){
                    writePath(pathFile);
                }
            }
        }

        if (indexFile.exists()){
            library = readMusicIndex(library, indexFile);
            listFiles(library);
        }else{
            library = getMusic(library, path);
            createMusicIndex(library, indexFile);
            listFiles(library);
        }
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton browseBtn;
    // End of variables declaration//GEN-END:variables
}
