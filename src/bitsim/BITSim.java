/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.JFrame;

import java.nio.file.Paths;

/**
 *
 * @author Swapnesh
 */
public class BITSim {

    /**
     */
    public static ArrayList<BITSimUser> users;
    public static ArrayList<ArrayList<BITSimProduct>> products;
    private static String path;
    private static File file_users;
    private static File[] file_products;
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        users = new ArrayList<>();
        products = new ArrayList<>();
        
        path = Paths.get(".").toAbsolutePath().normalize().toString() + "/res/";

        file_users = new File(path + "userDetails.txt");
        file_products = new File[4];
        file_products[0] = new File(path + "productsBorkars.txt");
        file_products[1] = new File(path + "productsFoodKing.txt");
        file_products[2] = new File(path + "productsMonginis.txt");
        file_products[3] = new File(path + "productsPragati.txt");
        
        if(file_users.exists())
            loadUserDetails();
        
        loadProducts();
        
        BITSimFrame frame = new BITSimFrame("BITSim");
        frame.setVisible(true);
    }
    
    /**
     *
     */
    public static void loadUserDetails() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file_users));
            String line = reader.readLine();
            BITSimUser user;
            
            while(line != null) {
                user = new BITSimUser(line, reader.readLine());
                user.setEWalletState(Boolean.parseBoolean(reader.readLine()));
                user.setPIN(reader.readLine().toCharArray());
                user.setEWalletBalance(Float.parseFloat(reader.readLine()));
                int productsNumber = Integer.parseInt(reader.readLine());
                ArrayList<BITSimProduct> productR = new ArrayList<>();
                for(int i = 0; i < productsNumber; i++) 
                    productR.add(new BITSimProduct(reader.readLine(), Float.parseFloat(reader.readLine()), Integer.parseInt(reader.readLine())));
                user.setBoughtProducts(productR);
                users.add(user);
                line = reader.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     *
     */
    public static void writeUserDetails() {
        
        try{
            if(!file_users.exists()){
                file_users.createNewFile();
            }
        
            FileWriter fileWriter = new FileWriter(file_users.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter, true);
            int productsNumber = 0;
            
            Iterator<BITSimUser> it = users.iterator();
            Iterator<BITSimProduct> itP;
            BITSimProduct tmpProduct;
            BITSimUser user = null;
            while(it.hasNext()) {
                user = it.next();
                productsNumber = user.getBoughtProducts().size();
                printWriter.println(user.getName());
                printWriter.println(user.getPassword());
                printWriter.println(user.getEWalletState());
                printWriter.println(new String(user.getPIN()));
                printWriter.println(user.getEWalletBalance());
                printWriter.println(productsNumber);
                itP = user.getBoughtProducts().iterator();
                while(itP.hasNext()) {
                    tmpProduct = itP.next();
                    printWriter.println(tmpProduct.getProductName());
                    printWriter.println(tmpProduct.getPrice());
                    printWriter.println(tmpProduct.getQuantityAvailable());
                }
            }
            
            printWriter.close();
            
        } catch(IOException IOEx) {
            IOEx.printStackTrace();
        }
    }
    
    /**
     *
     */
    public static void loadProducts() {
        try {
           for(int i = 0; i < 4; i++) {
               ArrayList<BITSimProduct> shopProducts = new ArrayList();
               if(!file_products[i].exists())
                   shopProducts.add(new BITSimProduct("No products", 0, 0));
               else {
                   BufferedReader reader = new BufferedReader(new FileReader(file_products[i]));
                   String line = reader.readLine();
                   BITSimProduct product = null;
                   
                   while(line != null) {
                       product = new BITSimProduct(line, Float.parseFloat(reader.readLine()), Integer.parseInt(reader.readLine()));
                       shopProducts.add(product);
                       line = reader.readLine();
                   }
               }
               products.add(shopProducts);
           }
        }catch(IOException ioex){
            ioex.printStackTrace();
        } 
    }
    
    public static void writeProducts(int shopIndex) {
        try {
            File file;
            switch(shopIndex) {
                case 0:
                    file = new File(path + "productsBorkars.txt");
                    break;
                case 1:
                    file = new File(path + "productsFoodKing.txt");
                    break;
                case 2:
                    file = new File(path + "productsMonginis.txt");
                    break;
                case 3:
                    file = new File(path + "productsPragati.txt");
                    break;
                default:
                    file = null;
            }
            
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter, true);
            
            Iterator<BITSimProduct> it = products.get(shopIndex).iterator();
            BITSimProduct product = null;
            while(it.hasNext()) {
                product = it.next();
                printWriter.println(product.getProductName());
                printWriter.println(product.getPrice());
                printWriter.println(product.getQuantityAvailable());
            }
            printWriter.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int getUserIndex(BITSimUser user) {
        Iterator<BITSimUser> it = users.iterator();
        BITSimUser u = null;
        while(it.hasNext()) {
            u = it.next();
            if(u.getName().equals(user.getName()))
                return users.indexOf(u);
        }
        return -1;
    }
    
    public static void setBoughtQuantity(BITSimUser user, ArrayList<BITSimProduct> productListA) {
        int index = getUserIndex(user);
        ArrayList<BITSimProduct> userProducts = users.get(index).getBoughtProducts();
        if(userProducts.isEmpty()) {
            userProducts = productListA;
        }
        else {
            Iterator<BITSimProduct> itS = productListA.iterator();
            ListIterator<BITSimProduct> itD = userProducts.listIterator();
            BITSimProduct productS = null;
            BITSimProduct productD = null;
            boolean isSet = false;
            while(itS.hasNext()) {
                isSet = false;
                productS = itS.next();
                itD = userProducts.listIterator();
                while(itD.hasNext()) {
                    productD = itD.next();
                    if(productS.getProductName().equals(productD.getProductName())) {
                        productD.setQuantityAvailable(productD.getQuantityAvailable() + productS.getQuantityAvailable());
                        itD.set(productD);
                        isSet = true;
                        break;
                    }
                }
                if(!isSet)
                    itD.add(productS);
            }
        } 
        
    //    System.out.println(userProducts.size());
        users.get(index).setBoughtProducts(userProducts);
    }
    
    public static void positionFrame(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight() - 40) / 2);
        frame.setLocation(x, y);
    }
}
