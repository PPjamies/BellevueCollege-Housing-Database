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
public class ResidentMenu {
	
	public static void UserLogin(Connection con) {
		Scanner reader = new Scanner(System.in);
		System.out.println("SID:");
		String username = reader.next();
		System.out.println("Password:");
		String password = reader.next();
		//reader.close();
		if (password.equals(""))
		{
			System.out.println("Invalid SID/Password Combination");
		} else
			try {
				if( Housing_Queries.checkIfIsApplicant(con, username, password) == true)//if person is applicant
				{ 
					if( Housing_Queries.checkIfIsResident(con, username, password) == true)//if person is resident
					{
						Housing_Queries.printResidentInfo(con, "bcsh", username);//print out resident info
					}
					else //person is not resident, but is an applicant
					{
						if(Housing_Queries.checkApplicationStatus(con, "bcsh", username))
						{
							System.out.println("Application is pending");
						}
						else
						{
							System.out.println("Sorry, no application has been submitted by this applicant");
							ApplicantMenu.menu(con);
						}
						/*
						Housing_Queries.checkApplicationStatus(con, "bcsh", username);//print application status (not boolean)
						String appStatus = Housing_Queries.getApplicationStatus(con, username);
						if( appStatus.equals("") || appStatus.equalsIgnoreCase("NULL")) //no application has been submitted by this applicant
						{
							System.out.println("Sorry, no application has been submitted by this applicant");
							//ApplicantMenu appMenu = new ApplicantMenu();
							ApplicantMenu.menu(con);
						}
						*/
					}
				}
				/*
				else if ( password.equals(Housing_Queries.getApplicantPassword(con, username)) )
				{
					//print resident info if in table, otherwise inform of application being processed
					
				}
				*/
				else
				{
					System.out.println("Invalid SID/Password Combination");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	
    public static void menu(Connection con) {
        //ApplicantMenu appMenu = new ApplicantMenu();
    	/*
        int userLoginStatus = UserLogin();
		if(userLoginStatus == 1) {
            String residentInfo = ""; //TODO: WRITE QUERY TO PRINT THEIR INFO
            System.out.println(residentInfo);
		}
		else if(userLoginStatus == 0){//TODO: CHECK TO SEE IF USER IS AN APPLICANT
			System.out.println("Application still being processed. Please check again later.");
        }
		else {
            System.out.println("Sorry, user not found. Please try again later.");
		}
		*/
    	UserLogin(con);
    	

    }
}
