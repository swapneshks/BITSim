/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Swapnesh
 */
public class BITSimFrame extends JFrame{

    /**
     *
     */
    public static boolean isAccountCreating;

    /**
     *
     */
    public static boolean isLogin;
    private String picPath;
    private String picName;
    
    private JPanel imagePanel;
    private ImageIcon logo;
    private JLabel logoLabel;
    private JPanel buttonPanel;
    private JButton buttonCreateAcc;
    private JButton buttonLogin;
    private JButton buttonQuit;
    
    /**
     *
     * @param title
     */
    public BITSimFrame(final String title){
        super(title);
        setLayout(new FlowLayout());
        setSize(750, 550);
        setVisible(true);
        addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        
        BITSim.positionFrame(this);
        
        isAccountCreating = false;
        isLogin = false;

        picPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/res/";
        picName = picPath + "BITSim Cover.jpg";

        imagePanel = new JPanel();
        
        logo = new ImageIcon(picName);
        logoLabel = new JLabel(logo);
        imagePanel.add(logoLabel);
        
        add(imagePanel);
        
        buttonPanel = new JPanel();
        
        buttonCreateAcc = new JButton("Create an account");
        buttonCreateAcc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimAccount("Create account");
                dispose();
            }
        });
        buttonPanel.add(buttonCreateAcc);
        
        buttonLogin = new JButton("Login");
        buttonLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimLogin("Login");
                dispose();
            }
        });
        buttonPanel.add(buttonLogin);
        
        buttonQuit = new JButton("Quit");
        buttonQuit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isAccountCreating && !isLogin)
                    System.exit(0);
            }
        });
        buttonPanel.add(buttonQuit);
        
        add(buttonPanel);
    }    
}
