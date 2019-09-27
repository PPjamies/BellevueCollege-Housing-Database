/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.util.Scanner;

import java.sql.*;
import java.io.*;
import java.text.*;
import com.mysql.cj.jdbc.Driver;

public class AppManagementMenu {
    
    public static void menu(Connection con){
    	Scanner reader = new Scanner(System.in);
        boolean dontQuit = true;
        while(dontQuit){
            System.out.println("1. Get Applicants Statuses || 2. Approve an Applicant || 3. Return to Admin Menu");
            int choice = reader.nextInt();
            switch(choice){
                case 1:
                    appStatuses(con);//does nothing yet
                    break;
                case 2:
                    appApproval(con);//does nothing yet
                    break;
                case 3:
                    dontQuit = false;
                    break;
            }
        }
    }
    
    //This is the method for an administrator to approve an application, bumping an applicant from
    //applicant to resident

    public static void appApproval(Connection con){

        Scanner reader = new Scanner(System.in);
        System.out.println("What is the resident's SID?");
        int res_sid = reader.nextInt();
        System.out.println("What is the resident's new housing number?");
        int housing_no = reader.nextInt();
        System.out.println("What is the resident's new building number?");
        int build_no = reader.nextInt();

        //This is the date take in.
        System.out.println("What is the resident's move in date in dd/mm/yyyy format?");
        String dateString = reader.next();
        java.sql.Date move_in_date = dateReturn(dateString);//takes to the method date return which takes in a string
        //converts it to a java.util.Date, and then converts that to a SQL date.

        //this is the second date take in
        System.out.println("What is the resident's move out date in dd/mm/yyyy format?");
        dateString = reader.next();
        java.sql.Date move_out_date = dateReturn(dateString); // passes to the date formatting method

        System.out.println("What are the resident's total dues?");
        int dues = reader.nextInt();
        System.out.println("What are the total dues paid by the resident?");
        int duesPaid = reader.nextInt();

        //passes all info into the createResident
        try {
			Housing_Queries.createResident(con, "bcsh", res_sid, build_no, housing_no, move_in_date, move_out_date, dues, duesPaid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //This is the method that takes in a string and returns a SQL Date
    public static java.sql.Date dateReturn(String dateString){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//formats the date to take in dd/mm/yyyy format
        java.util.Date date = null;//initializes the date to the current date.
        try {
            date = formatter.parse(dateString);//parses the string into the java.util.date above
        }
        catch(ParseException e){
            System.out.println("Date failed to read. Set to today's date.");
            e.printStackTrace();
        }
        //converts the java.util.Date into a java.sql.Date instead
         java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;//returns the date as a sql Date
    }
    
    
    //TODO: This function should just print out all the applicant statuses
    public static void appStatuses(Connection con){
    	try {
			Housing_Queries.adminChecksApplicationStatus(con, "bcsh");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
