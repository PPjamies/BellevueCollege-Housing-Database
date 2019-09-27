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

public class AdminMenu {
    public static boolean checkUser(Connection con){
        Scanner reader = new Scanner(System.in);
		System.out.println("STAFF ID: ");
		String username = reader.next();
		System.out.println("Password: ");
		String password = reader.next();
		//reader.close();
		if (password.equals(""))
		{
			return false;
		}
        return password.equals(Housing_Queries.getAdminPassword(con, username));
    }
    
    public static void menu(Connection con) {
        
        if(checkUser(con) == true) {
        	Scanner reader = new Scanner(System.in);
            boolean dontQuit = true;
            while(dontQuit) {
                System.out.println("What would you like to do today?");
                System.out.println("1. Manage Applicants || 2. Reports || 3. Quit");
                int userChoice = reader.nextInt();
                switch(userChoice) {
                case 1:
                    //AppManagementMenu appManMenu = new AppManagementMenu();
                    AppManagementMenu.menu(con);
                    break;
                case 2: 
                    //ReportsMenu reportMenu = new ReportsMenu();
                	ReportsMenu.menu(con);
                    break;
                case 3:
                    dontQuit = false;
                    break;
                default:
                    System.out.println("Sorry, command not recognized.");
                    break;
			
                }
            }
            //reader.close();
		
		}
		else {
        	System.out.println("Invalid ID/Password Combination");
		}
    }
    
}
