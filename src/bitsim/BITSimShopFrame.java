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
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Swapnesh
 */
public class BITSimShopFrame extends JFrame {
    private ArrayList<String> productNames;
    private ArrayList<BITSimProduct> productList;
    private float totalAmount;
    
    private JPanel panelTop;
    private JPanel panelMessage;
    private JLabel labelMessage1;
    private JLabel labelMessage2;
    private JPanel panelProductDetails;
    private JLabel labelProductName;
    private JComboBox boxProductName;
    private JLabel labelPrice;
    private JTextField textPrice;
    private JLabel labelQuantity;
    private JTextField textQuantity;
    private JPanel panelList;
    private JLabel labelList;
    private JTextArea areaList;
    private JPanel panelAmount;
    private JLabel labelAmount;
    private JTextField textAmount;
    private JLabel labelQuantityRequired;
    private JTextField textQuantityRequired;
    private JPanel panelButtons;
    private JButton buttonAdd;
    private JButton buttonRemove;
    private JButton buttonBuy;
    private JButton buttonBack;
    
    /**
     *
     * @param title
     * @param user
     * @param shopIndex
     */
    public BITSimShopFrame(String title, final BITSimUser user, final int shopIndex) {
        super(title);
        setVisible(true);
        setSize(660, 620);
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
        
        productNames = new ArrayList<>();
        productList = new ArrayList<>();
        totalAmount = 0.0F;
        
        panelTop = new JPanel();
        panelTop.setLayout(new GridLayout(2, 1));
        
        panelMessage = new JPanel();
        panelMessage.setLayout(new GridLayout(2, 1));
        
        labelMessage1 = new JLabel("Playing as '" + user.getName() + "'", SwingUtilities.CENTER);
        panelMessage.add(labelMessage1);
        
        labelMessage2 = new JLabel("Status : After adding items to your list, press Buy.", SwingUtilities.CENTER);
        panelMessage.add(labelMessage2);
        
        panelTop.add(panelMessage); 
        
        panelProductDetails = new JPanel();
        
        labelProductName = new JLabel("Product : ");
        panelProductDetails.add(labelProductName);
        
        Iterator<BITSimProduct> it = BITSim.products.get(shopIndex).iterator();
        while(it.hasNext())
            productNames.add(it.next().getProductName());
        
        boxProductName = new JComboBox(productNames.toArray());
        boxProductName.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    textPrice.setText("Rs." + String.valueOf(BITSim.products.get(shopIndex).get(boxProductName.getSelectedIndex()).getPrice()));
                    textQuantity.setText(String.valueOf(BITSim.products.get(shopIndex).get(boxProductName.getSelectedIndex()).getQuantityAvailable()));
                }
            }
        });
        panelProductDetails.add(boxProductName);
        
        labelPrice = new JLabel("Price : ");
        panelProductDetails.add(labelPrice);
        
        textPrice = new JTextField("Rs." + String.valueOf(BITSim.products.get(shopIndex).get(boxProductName.getSelectedIndex()).getPrice()), 7);
        textPrice.setEditable(false);
        panelProductDetails.add(textPrice);
        
        labelQuantity = new JLabel("Quantity Available: ");
        panelProductDetails.add(labelQuantity);
        
        textQuantity = new JTextField(String.valueOf(BITSim.products.get(shopIndex).get(boxProductName.getSelectedIndex()).getQuantityAvailable()), 5);
        textQuantity.setEditable(false);
        panelProductDetails.add(textQuantity);
        
        panelTop.add(panelProductDetails);
        
        add(panelTop);
        
        panelList = new JPanel();
    //    panelList.setLayout(new GridLayout(2, 1));
        
        labelList = new JLabel("List : ", SwingUtilities.CENTER);
        panelList.add(labelList);
        
        areaList = new JTextArea(25, 30);
        areaList.setWrapStyleWord(true);
        areaList.setLineWrap(true);
        areaList.setEditable(false);
        panelList.add(new JScrollPane(areaList));
        
        add(panelList);
        
        panelAmount = new JPanel();
        
        labelQuantityRequired = new JLabel("Quantity Required : ");
        panelAmount.add(labelQuantityRequired);
        
        textQuantityRequired = new JTextField(10);
        panelAmount.add(textQuantityRequired);
        
        labelAmount = new JLabel("Total Amount : ");
        panelAmount.add(labelAmount);
        
        textAmount = new JTextField("Rs. 0.0", 10);
        textAmount.setEditable(false);
        panelAmount.add(textAmount);
        
        add(panelAmount);
        
        panelButtons = new JPanel();
        
        buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isNumeric(textQuantityRequired.getText()) || textQuantityRequired.getText().equals("") || Integer.parseInt(textQuantityRequired.getText()) < 0 || Integer.parseInt(textQuantityRequired.getText()) > Integer.parseInt(textQuantity.getText())) {
                    labelMessage2.setText("Status : Invalid quantity!");
                    textQuantityRequired.setText("");
                }
                else {
                    Iterator<BITSimProduct> it = productList.iterator();
                    BITSimProduct selectedProduct = new BITSimProduct(boxProductName.getSelectedItem().toString(), Float.parseFloat(textPrice.getText().substring(3)), Integer.parseInt(textQuantityRequired.getText()));
                    BITSimProduct tempProduct = new BITSimProduct("", 0, 0);
                    boolean isProductPresent = false;
                    while(it.hasNext()) {
                        tempProduct = it.next();
                        if(tempProduct.getProductName().equals(selectedProduct.getProductName())) {
                            tempProduct.setQuantityAvailable(tempProduct.getQuantityAvailable() + selectedProduct.getQuantityAvailable());
                            isProductPresent = true;
                            break;
                        }
                    }
                    if(!isProductPresent)
                        productList.add(selectedProduct);

                    labelMessage2.setText("Status : Added selected item(s) to the list.");
                    textAmount.setText("Rs. " + String.valueOf(totalAmount += (selectedProduct.getQuantityAvailable() * selectedProduct.getPrice())));
                    BITSim.products.get(shopIndex).get(boxProductName.getSelectedIndex()).setQuantityAvailable(Integer.parseInt(textQuantity.getText()) - selectedProduct.getQuantityAvailable());
                    textQuantity.setText(String.valueOf(Integer.parseInt(textQuantity.getText()) - selectedProduct.getQuantityAvailable()));
                    textQuantityRequired.setText("");

                    it = productList.iterator();
                    StringBuilder builder = new StringBuilder();
                    while(it.hasNext()) {
                        tempProduct = it.next();
                        builder.append(tempProduct.getProductName()).append(" : ").append(tempProduct.getQuantityAvailable()).append(" @ Rs. ").append(tempProduct.getPrice()).append("\n");
                    }
                    areaList.setText(builder.toString());
                }
            }
        });
        panelButtons.add(buttonAdd);
        
        buttonRemove = new JButton("Remove");
        buttonRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isNumeric(textQuantityRequired.getText()) || textQuantityRequired.getText().equals("") || Integer.parseInt(textQuantityRequired.getText()) < 0) {
                    labelMessage2.setText("Status : Invalid quantity!");
                    textQuantityRequired.setText("");
                }
                else {
                    Iterator<BITSimProduct> it = productList.iterator();
                    BITSimProduct selectedProduct = new BITSimProduct(boxProductName.getSelectedItem().toString(), Float.parseFloat(textPrice.getText().substring(3)), Integer.parseInt(textQuantityRequired.getText()));
                    BITSimProduct tempProduct = new BITSimProduct("", 0, 0);
                    boolean isProductPresent = false;
                    boolean isDiffNegative = false;
                    while(it.hasNext()) {
                        tempProduct = it.next();
                        if(tempProduct.getProductName().equals(selectedProduct.getProductName())) {
                            int difference = tempProduct.getQuantityAvailable() - selectedProduct.getQuantityAvailable();
                            if(difference < 0) {
                                difference = 0;
                                isDiffNegative = true;
                                break;
                            }
                            tempProduct.setQuantityAvailable(difference);
                            if(tempProduct.getQuantityAvailable() == 0)
                                productList.remove(productList.indexOf(tempProduct));
                            isProductPresent = true;
                            
                            labelMessage2.setText("Status : Removed selected item(s) from the list.");
                            textAmount.setText("Rs. " + String.valueOf(totalAmount -= (selectedProduct.getQuantityAvailable() * selectedProduct.getPrice())));
                            BITSim.products.get(shopIndex).get(boxProductName.getSelectedIndex()).setQuantityAvailable(Integer.parseInt(textQuantity.getText()) + selectedProduct.getQuantityAvailable());
                            textQuantity.setText(String.valueOf(Integer.parseInt(textQuantity.getText()) + selectedProduct.getQuantityAvailable()));
                            
                            it = productList.iterator();
                            StringBuilder builder = new StringBuilder();
                            while(it.hasNext()) {
                                tempProduct = it.next();
                                builder.append(tempProduct.getProductName()).append(" : ").append(tempProduct.getQuantityAvailable()).append(" @ Rs. ").append(tempProduct.getPrice()).append("\n");
                            }
                            areaList.setText(builder.toString());
                            break;
                        }
                    }
                    if(!isProductPresent)
                        labelMessage2.setText("Status : Item(s) not present in the list!");
                    if(isDiffNegative)
                        labelMessage2.setText("Status : Invalid quantity!");
                    textQuantityRequired.setText("");
                }
            }
        });
        panelButtons.add(buttonRemove);
        
        buttonBuy = new JButton("Buy");
        buttonBuy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalAmount == 0.0F)
                    labelMessage2.setText("Status : List is empty!");
                else {
                    changeButtonsState(false);
                    new BITSimPay("E-Wallet Payment", BITSimShopFrame.this, productList, BITSimShopFrame.this, user, shopIndex, totalAmount); 
                }
            }
        });
        panelButtons.add(buttonBuy);
        
        buttonBack = new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<BITSimProduct> itS = productList.iterator();
                Iterator<BITSimProduct> itM;
                BITSimProduct tempProductS = new BITSimProduct("", 0, 0);
                BITSimProduct tempProductM = new BITSimProduct("", 0, 0);
                while(itS.hasNext()) {
                    tempProductS = itS.next();
                    itM = BITSim.products.get(shopIndex).iterator();
                    while(itM.hasNext()) {
                        tempProductM = itM.next();
                        if(tempProductM.getProductName().equals(tempProductS.getProductName())) {
                            int index = BITSim.products.get(shopIndex).indexOf(tempProductM);
                            BITSim.products.get(shopIndex).get(index).setQuantityAvailable(tempProductM.getQuantityAvailable() + tempProductS.getQuantityAvailable());
                        }
                    }
                }
                
                new BITSimPlay("Play", BITSim.users.get(BITSim.getUserIndex(user)));
                dispose();
            }
        });
        panelButtons.add(buttonBack);
        
        add(panelButtons);
    }
    
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException n) {
            return false;
        }
        return true;
    }
    
    public void changeButtonsState(boolean state) {
        buttonAdd.setEnabled(state);
        buttonRemove.setEnabled(state);
        buttonBuy.setEnabled(state);
        buttonBack.setEnabled(state);
    }
}
