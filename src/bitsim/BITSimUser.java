/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Swapnesh
 */
public class BITSimUser {
    private String name;
    private String password;
    private boolean isEWalletSetUp;
    private char[] PIN;
    private float eWalletBalance;
    private ArrayList<BITSimProduct> boughtProducts;
    
    /**
     *
     * @param name
     * @param password
     */
    public BITSimUser(String name, String password){
        this.name = name;
        this.password = password;
        this.isEWalletSetUp = false;
        this.PIN = new char[]{'0','0','0','0'};
        this.eWalletBalance = 0;
        this.boughtProducts = new ArrayList<>();
    }
    
    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }
    
    /**
     *
     * @return
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     *
     * @return
     */
    public boolean getEWalletState(){
        return this.isEWalletSetUp;
    }
    
    /**
     *
     * @return
     */
    public char[] getPIN(){
        return this.PIN;
    }
    
    public float getEWalletBalance(){
        return this.eWalletBalance;
    }
    
    public ArrayList<BITSimProduct> getBoughtProducts() {
        return this.boughtProducts;
    }
    
    /**
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     *
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     *
     * @param state
     */
    public void setEWalletState(boolean state){
        this.isEWalletSetUp = state;
    }
    
    /**
     *
     * @param PIN
     */
    public void setPIN(char[] PIN){
        System.arraycopy(PIN, 0, this.PIN, 0, 4);
    }
    
    public void setEWalletBalance(float amount){
        this.eWalletBalance = amount;
    }
    
    public void setBoughtProducts(ArrayList<BITSimProduct> userProducts) {
        this.boughtProducts = userProducts;
    }
    
    public void printProducts() {
        Iterator<BITSimProduct> it = this.boughtProducts.iterator();
    //    System.out.println("Print");
        while(it.hasNext())
            System.out.println(it.next().getProductName());
    }
}
