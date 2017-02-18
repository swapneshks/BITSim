/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Swapnesh
 */
public class BITSimInventory extends JFrame {
    private JPanel panelMessage;
    private JLabel labelMessage;
    private JPanel panelInventory;
    private JLabel labelInventory;
    private JTextArea areaInventory;
    private JPanel panelButtons;
    private JButton buttonBack;
    
    public BITSimInventory(String title, final BITSimUser user) {
        super(title);
        setVisible(true);
        setSize(500, 540);
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
        
        panelMessage = new JPanel();
        
        labelMessage = new JLabel("Status : Press Back to go to previous screen.");
        panelMessage.add(labelMessage);
        
        add(panelMessage);
        
        panelInventory = new JPanel();
        
        labelInventory = new JLabel("Inventory : ");
        panelInventory.add(labelInventory);
        
        areaInventory = new JTextArea(25, 30);
        areaInventory.setWrapStyleWord(true);
        areaInventory.setLineWrap(true);
        areaInventory.setEditable(false);
        ArrayList<BITSimProduct> uProducts = user.getBoughtProducts();
        BITSimProduct rProduct = null;
        Iterator<BITSimProduct> it = uProducts.iterator();
        while(it.hasNext()) {
            rProduct = it.next();
            areaInventory.append(rProduct.getProductName() + " - " + rProduct.getQuantityAvailable() + "\n");
        }
        panelInventory.add(new JScrollPane(areaInventory));
        
        add(panelInventory);
        
        panelButtons = new JPanel();
        
        buttonBack = new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimPlay("Play", user);
                dispose();
            }
        });
        panelButtons.add(buttonBack);
        
        add(panelButtons);
    }
}
