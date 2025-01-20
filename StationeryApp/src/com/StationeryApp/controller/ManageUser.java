/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StationeryApp.controller;
import java.util.*; //for using Hashmap from the java.util package. 
/**
 * @author Apekshya Sharma
 * This class implements user authentication for login functionality.
 */
public class ManageUser {
    //implementing HashMap to store user information for user authentication.
    //this Hashmap stores user name as String and objects from stationeryController class.
    private final HashMap<String,StationeryController> userNamePass;
    /**
     * creating constructor method for manageUser class.
     * Assigning predefined users as firstAdmin,firstUser and firstManager and storing it in the HashMap-
     * named as userNamePass.
     */
    public ManageUser(){
        userNamePass=new HashMap<>();//initiating HashMap.
        initiateUser();//this method stores multiple user to the HashMap.
    }
    private void initiateUser(){//this method stores multiple user to the HashMap.
        userNamePass.put("firstAdmin",new StationeryController("firstAdmin","adminPass"));//predefined user and password.
        userNamePass.put("firstUser", new StationeryController("firstUser","userPass"));//predefined user and password.
        userNamePass.put("firstManager", new StationeryController("firstManager","managerPass"));//predefined user and password.
    }
    /**
     * Method for user authentication for correct username and password matching with the HashMap.
     * @param nameUser User name provided by the user.
     * @param userPassword User password provided by the user.
     * @return checks for valid user credentials.
     */
    public boolean userAuthentication(String nameUser,String userPassword){
        //stores object for userName validation.
        StationeryController userValidate=userNamePass.get(nameUser);
        /**
         * checking for valid user credential.
         * True returns valid user credential.
         * False returns invalid user credential.
         */
        return userValidate!=null && userValidate.getUserPassword().equals(userPassword);  
    }
}
