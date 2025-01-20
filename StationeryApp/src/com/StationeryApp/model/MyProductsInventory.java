/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StationeryApp.model;
import java.util.*;
import com.StationeryApp.view.UpdateMyCartFrame;
/**
 * @author Apekshya Sharma
 *this class holds the information of products available in the Aurora Stationery Inventory.
 * this class holds the methods to create/add product and store their information.
 * this class holds the method for sorting and updating the products in the inventory by price.
 */
public class MyProductsInventory {
    //creating an ArrayList which holds product information i.e. objects of the stationeryModel class.
     private final ArrayList <StationeryModel> productItems;
    public MyProductsInventory(){
        //Initialising the ArrayList to store product information.
        productItems=new ArrayList<>();
    }
    /**
     *creating a method which adds products to the Aurora Stationery inventory.
     * @param modelItems object needed to be added in the ArrayList.
     */
    public void myProductsAddition(StationeryModel modelItems){
        //adding new products as an object to the ArrayList.
        productItems.add(modelItems);
    }
    //retrieving products in the Aurora Stationery inventory to implement the CRUD operations.
    public ArrayList<StationeryModel> getEntireItems(){
        return productItems;
    }
    /**
     * Implementing Selection Sort Algorithm to sort the prices of products from low to high.
     */
    public void priceBeingSorted(){
        // Declaring a variable as sortingPrice which stores the number of products in the inventory.
    int sortingPrice = productItems.size();
    
    // Iterating through each product in the ArrayList through the outermost loop.
    for (int index_known = 0; index_known < sortingPrice - 1; index_known++) {
        // Taking minimumVal as the first unsorted element in the ArrayList.
        int minimumVal = index_known;

        /**
         * Gives index of the cheapest product in the list through the innermost loop.
         * Compares the prices of the products in the inventory.
         * Updating first unsorted element in the list i.e. minimumVal.
         */
        for (int index_update = index_known + 1; index_update < sortingPrice; index_update++) { // Corrected loop condition
            if (productItems.get(index_update).getStatProductPrice() < productItems.get(minimumVal).getStatProductPrice()) {
                minimumVal = index_update; // Update minimumVal if a cheaper product is found
            }
        }
        
        // Swapping cheapest priced product with the first element in the ArrayList.
        if (minimumVal != index_known) { // Only swap if a new minimum was found
            StationeryModel tempVal = productItems.get(minimumVal);
            productItems.set(minimumVal, productItems.get(index_known));
            productItems.set(index_known, tempVal);
        }
    }
    }
    
    
    /**
     * method to check for given product Id exists or not.
     * @param productId gives id of the product to be checked.
     * @return true when product with provided product id exist, false when the product with provided product id does not exist.
     */
    public boolean checkProductId(int productId){
        //iterating through products in the inventory.
        for (StationeryModel givenProduct:this.getEntireItems()){
          if (givenProduct.getStatProductId()==productId){
              return true;//for matched product ids.
          }  
        }
        return false;//for unmatched product ids.
    }
    /**
     * method to delete product with specific product id.
     * @param productId gives id of the product to be deleted.
     */
    public void deleteProduct(int productId){
        //iterating through products in the inventory.
        for (int firstIndex=0;firstIndex<productItems.size();firstIndex++){
            //checks for matched product ids in the inventory and text field input.
           if(productItems.get(firstIndex).getStatProductId()==productId){
               productItems.remove(firstIndex);//delete when matched.
               return;          
           }
        }
    }
}
     
  
    

