/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.util.Iterator;

/**
 *
 * @author Swapnesh
 */
public class BITSimProduct {
    private String productName;
    private float price;
    private int quantityAvailable;

    /**
     *
     * @param productName
     * @param price
     * @param quantityAvailable
     */
    public BITSimProduct(String productName, float price, int quantityAvailable) {
        this.productName = productName;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }
    
    /**
     *
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    /**
     *
     * @param quantityAvailable
     */
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    
}
