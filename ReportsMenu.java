/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Scanner;

import java.sql.*;
import java.io.*;
import com.mysql.cj.jdbc.Driver;

/**
 *
 * @author Brandae2
 */
public class ReportsMenu {
    
    public static void menu(Connection con){
    	Scanner reader = new Scanner(System.in);
        boolean dontQuit = true;
        while(dontQuit){
            System.out.println("1. Get Maintenance Department Report || 2. Return to Admin Menu");
            int choice = reader.nextInt();
            switch(choice){
            case 1:
                getReports(con);//TODO: does nothing yet
                break;
            case 2:
                dontQuit = false;//Returns to the admin menu
                break;
            default:
                System.out.println("Sorry, command not recognized");
                break;
            }
        }
        //reader.close();
    }
    
    //This function should run the reporting query
    public static void getReports(Connection con){
    	try {
			Housing_Queries.MaintanenceDepartmentReport(con, "bcsh");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
   


}
