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

public class ApplicantMenu {

    public static String UserLogin(Connection con) {
		Scanner reader = new Scanner(System.in);
		System.out.println("SID:");
		String username = reader.next();
		System.out.println("Password:");
		String password = reader.next();
		//reader.close();
		if (password.equals(""))
		{
			return "";
		}
        if( password.equals(Housing_Queries.getApplicantPassword(con, username)))
        {
        	return username;
        }
        else
        {
        	return "";
        }
    }
	
    public static void menu(Connection con) {
        //userlogin needs to move to not block registration
         
    	Scanner reader = new Scanner(System.in);
        boolean dontQuit = true;
        while(dontQuit) {
            System.out.println("What would you like to do today?");
            System.out.println("1. Register as Applicant || 2. Submit Application || 3. Apartment Availability || 4. Quit");
            int userChoice = reader.nextInt();
            switch(userChoice) {
            case 1:
                fillOutApplicant(con);//TODO: Currently does not do anything with it's data
                break;
            case 2:
            	String un = UserLogin(con);
            	if(un != "")
            	{
            		filloutApplication(con, un);
            	}
            	else {
                    System.out.println("Invalid SID/Password Combination");
        		}
                //TODO: Submit Application Function has not been written
                break;
            case 3:
                apartmentAvailability(con);
                break;
            case 4: 
                dontQuit = false;
                break;
            default:
                System.out.println("Sorry, command not recognized.");
                break;
		
            }
        }
        //reader.close();
    }
    
    private static void filloutApplication(Connection con, String un) {
    	Scanner reader = new Scanner(System.in);
        System.out.println("\\*******************************************************************************************************************\\\n");
        System.out.println("                                     HOUSING APPLICATION\n");
        System.out.println("\\*******************************************************************************************************************\\");
		// TODO Auto-generated method stub
    	//gather necessary data here
        int SID = Integer.parseInt(un);
        int arn = 0;
        System.out.println("Please specify your first room preference:");
        String  housingPreference1 = reader.next();
        System.out.println("Please specify your second room preference:");
        String  housingPreference2 = reader.next();
        System.out.println("Please specify your third room preference:");
        String  housingPreference3 = reader.next();
        System.out.println("Please specify your village preference:");
        String  preferVillage = reader.next();
        System.out.println("Please specify your preferred roommate's SID. If you do not have a preference, please input value 0");
        int prefer_rm_sid = reader.nextInt();
        System.out.println("When do you expect to move in? (Fall, Winter, Spring, Summer):");
        int preferQuarterMovein = reader.nextInt();
		try {
			Housing_Queries.createApplication(con, "bcsh", SID, arn, "foo", housingPreference1, housingPreference2, housingPreference3, preferVillage, prefer_rm_sid, preferQuarterMovein);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("You have applied for housing. You will hear from us shortly");
	}

	public static void apartmentAvailability(Connection con){
        Scanner reader = new Scanner(System.in);
        System.out.println("1. All available rooms || 2. Checking a specific type of room");// || 3. Check by room number");
        int choice = reader.nextInt();
        switch(choice){
            case 1:
                //TODO: Print all available rooms
			try {
				Housing_Queries.checkApartmentAvailability(con, "bcsh");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case 2: 
                //TODO: User checks for all available of one type ie: all available studios
            	String ht = "";
            	System.out.println("Room Type:");
            	ht = reader.next();
			try {
				Housing_Queries.checkSpecApartmentAvailability(con, "bcsh", ht);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
                /*
            case 3:
                //TODO: search for a room by room number, print out whether or not the room is available or has been assigned a resident
                break;
                */
            default:
                System.out.println("Sorry, that's not an option.");
                break;
        }
        //reader.close();
    }//endApartmentAvailability
    
    private static void fillOutApplicant(Connection con){
        Scanner reader = new Scanner(System.in);
        System.out.println("What is your first name?");
        String fName = reader.next();
        System.out.println("What is your middle name/inital?");
        String mName = reader.next();
        System.out.println("What is your last name?");
        String lName = reader.next();
        System.out.println("What is your SID?");
        int SID = reader.nextInt();
        String password="";
        while(true)
        {
        	System.out.println("What is your password?");
            password = reader.next();
            if(password.equals(""))
            {
            	System.out.println("Invalid password");
            }
            else
            {
            	break;
            }
        }
        
        System.out.println("What is your phone number?");
        long phoneNumber = reader.nextLong();//TODO: CHANGE VALUE FROM STRING TO INT???
        boolean checkAlum = true;
        boolean alumni = false; 
        while(checkAlum){
            System.out.println("Are you an alumni? Y or N");
            
            String alumniYN = reader.next();
            if(alumniYN.equals("Y") || alumniYN.equals("y")){
                alumni = true;
                checkAlum = false;
            }   
            else if(alumniYN.equals("N") || alumniYN.equals("n")){
                alumni = false;
                checkAlum = false;
            }
            else{
                System.out.println("Sorry, try that again.");
            }
        }
        System.out.println("What is your Gender? Please enter a single character. M for male, F for female, or O for other.");
        System.out.println("F || M || O ");
        String g = reader.next();
        String gender = ""+g.charAt(0);
        System.out.println("What is your address");
        String address = reader.next();
        System.out.println("What is your college major?");
        String majColl = reader.next();
        System.out.println("What is your college department?");
        String majDept = reader.next();
        System.out.println("What is your year of study? Please estimate your year in your program, regardless of how many years you have attended college.");
        System.out.println("1 - First Year || 2 - Second Year || 3 - 3rd Year || 4 - Fourth Year || 5 - Already graduated");
        int year_of_study = reader.nextInt();
        boolean checkMarried = true;
        boolean married = false;
        while(checkMarried){
            System.out.println("Are you an alumni? Y or N");
             
            String marriedYN = reader.next();
            if(marriedYN.equals("Y") || marriedYN.equals("y")){
        		married = true;	
                checkMarried = false;
            }   
            else if(marriedYN.equals("N") || marriedYN.equals("n")){
                married = false;
                checkMarried = false;
            }
            else{
                System.out.println("Sorry, try that again.");
            }
        }
        System.out.println("What is your social security number?");
        int app_ssn = reader.nextInt();
        System.out.println("What is your email?");
        String email = reader.next();
        System.out.println("What is your emergency contact's phone number?");
        long emergencyNumber = reader.nextLong();
        System.out.println("What is your citizenship status?");
        String citizenship_status = reader.nextLine();
        try {
			Housing_Queries.createApplicant(con, "bcsh", SID,password, fName, mName, lName, phoneNumber, alumni, gender, address, majColl, majDept, year_of_study, married, app_ssn, 0, email, emergencyNumber, citizenship_status);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //TODO, query using all this
        //reader.close();
    }

}
