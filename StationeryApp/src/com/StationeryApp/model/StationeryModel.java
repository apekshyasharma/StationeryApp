package com.StationeryApp.model;

import java.util.Objects;

/**
 *
 * @author Apekshya Sharma
 */
//The class stationeryModel consists of the features held by a product in the stationery inventory.
public class StationeryModel {
    private int statProductId;
    private String statProductName;
    private int statProductQuantity;
    private double statProductPrice;
    
    
    
    //This constructer method helps in creating new products.
    public StationeryModel(int statProductId, String statProductName, int statProductQuantity, double statProductPrice){
        this.statProductId=statProductId;
        this.statProductName=statProductName;
        this.statProductQuantity=statProductQuantity;
        this.statProductPrice=statProductPrice;
    }
    //Implementing setter and getter methods for each products.

    public int getStatProductId() {
        return statProductId;
    }

    public void setStatProductId(int statProductId) {
        this.statProductId = statProductId;
    }

    public String getStatProductName() {
        return statProductName;
    }

    public void setStatProductName(String statProductName) {
        this.statProductName = statProductName;
    }

    public int getStatProductQuantity() {
        return statProductQuantity;
    }

    public void setStatProductQuantity(int statProductQuantity) {
        this.statProductQuantity = statProductQuantity;
    }

    public double getStatProductPrice() {
        return statProductPrice;
    }

    public void setStatProductPrice(double statProductPrice) {
        this.statProductPrice = statProductPrice;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Check for null and class type
        StationeryModel that = (StationeryModel) obj; // Cast to the correct type
        return statProductId == that.statProductId; // Compare based on product ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(statProductId); // Use product ID for hash code
    }
   
    
}



