/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StationeryApp.controller;

/**
 *
 * @author Apekshya Sharma
 */
public class StationeryController {
    private final String nameUser; //declaring instance variable which stores User's Name.
    private final String userPassword;//declaring instance variable which stores User's Password.
    
    /**
     * Constructor method which creates new User for the system.
     * @param nameUser -> instance variable to store user name.
     * @param userPassword -> instance variable to store user password.
     */
    public StationeryController(String nameUser, String userPassword){
        this.nameUser=nameUser;
        this.userPassword=userPassword;
    }
    // Declaring methods to retreive User credentials for proper login to the system.
    public String getNameUser() {
        return nameUser; //returns user name provided by the user.
    }

    public String getUserPassword() {
        return userPassword; //returns password provided by the user.
    }
}
 
