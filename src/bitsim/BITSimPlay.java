/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.StringContent;

/**
 *
 * @author Swapnesh
 */
public class BITSimPlay extends JFrame {
    private static String[] shops;
    private boolean isBackPressed;
    
    private JPanel panelTop;
    private JPanel panelMessage;
    private JLabel labelMessage1;
    private JPanel panelShop;
    private JLabel labelMessage2;
    private JComboBox boxShop;
    private JPanel imagePanel;
    private ImageIcon logo;
    private JLabel logoLabel;
    private String picPath;
    private String picName;
    private JPanel panelButton;
    private JButton buttonGo;
    private JButton buttonEWalletBalance;
    private JButton buttonInventory;
    private JButton buttonBack;
    
    /**
     *
     * @param title
     * @param user
     */
    public BITSimPlay(String title, final BITSimUser user) {
        super(title);
        setVisible(true);
        setSize(580,560);
        setLayout(new FlowLayout());
        addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        
        BITSim.positionFrame(this);
        
        shops = new String[]{"Borkars","Food King","Monginis","Pragati"};
        isBackPressed = false;
        
        panelTop = new JPanel();
        panelTop.setLayout(new GridLayout(2, 1));
        
        panelMessage = new JPanel();
        
        labelMessage1 = new JLabel("Playing as '" + user.getName() + "'", SwingUtilities.CENTER);
        panelMessage.add(labelMessage1);
        
        panelTop.add(panelMessage);
        
        panelShop = new JPanel();
        
        labelMessage2 = new JLabel("Status : Select where you want to go : ", SwingUtilities.CENTER);
        panelShop.add(labelMessage2);
        
        boxShop = new JComboBox(shops);
        boxShop.setEditable(false);
        panelShop.add(boxShop);
        
        panelTop.add(panelShop);
        
        add(panelTop);
        
        imagePanel = new JPanel();

        picPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/res/";
        picName = picPath + "BITSim Play.jpg";

        logo = new ImageIcon(picName);
        logoLabel = new JLabel(logo);
        imagePanel.add(logoLabel);
        
        add(imagePanel);
        
        panelButton = new JPanel();
        
        buttonGo = new JButton("Go to Shop");
        buttonGo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isBackPressed) {
                    new BITSimShopFrame("Shop", user, boxShop.getSelectedIndex());
                    dispose();
                }
                else {
                    new BITSimFrame("BITSim");
                    dispose();
                }
            }
        });
        panelButton.add(buttonGo);
        
        buttonEWalletBalance = new JButton("Check E-Wallet Balance");
        buttonEWalletBalance.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimEWalletBalance("e-Wallet Balance", BITSim.users.get(BITSim.getUserIndex(user)));
                dispose();
            }
        });
        panelButton.add(buttonEWalletBalance);
        
        buttonInventory = new JButton("View Your Inventory");
        buttonInventory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimInventory("Inventory", user);
                dispose();
            }
        });
        panelButton.add(buttonInventory);
        
        buttonBack = new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isBackPressed) {
                    labelMessage2.setText("Status : Are you sure you want to go back to main menu?");
                    boxShop.setEditable(false);
                    buttonGo.setText("Yes");
                    buttonEWalletBalance.setEnabled(false);
                    buttonInventory.setEnabled(false);
                    boxShop.setEnabled(false);
                    buttonBack.setText("No");
                    isBackPressed = true;
                }
                else {
                    labelMessage2.setText("Status : Select where you want to go.");
                    boxShop.setEditable(true);
                    buttonGo.setText("Go to Shop");
                    buttonEWalletBalance.setEnabled(true);
                    buttonInventory.setEnabled(true);
                    boxShop.setEnabled(true);
                    boxShop.setEditable(false);
                    buttonBack.setText("Back");
                    isBackPressed = false;
                }
            }
        });
        panelButton.add(buttonBack);
        
        add(panelButton);
    }
    
    public static String getShopName(int shopIndex) {
        return shops[shopIndex];
    }
}
