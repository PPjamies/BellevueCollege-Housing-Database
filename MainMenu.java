/*

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
public class MainMenu {

    public static void main(String[] args) {
    	
		Connection conn = null;
		try {
			// one way is to define driver this way and no need for import driver or the exception
			// Class.forName("com.mysql.cj.jdbc.Driver");
			DriverManager.registerDriver(new Driver());
			String url = "jdbc:mysql://localhost:3306/bcsh?serverTimezone=UTC&useSSL=TRUE";
			String user, pass;
			user = readEntry("UserId: ");
			pass = readEntry("Password: ");
			conn = DriverManager.getConnection(url, user, pass);	//gets connection
			
			
			Scanner reader = new Scanner(System.in);
			boolean dontQuit = true;
			while(dontQuit) {
		            System.out.println("What are you?");
		            System.out.println("1. Resident || 2. Applicant || 3. Administrator || 4. Quit");
		            int userChoice = reader.nextInt();
		            switch(userChoice) {
		            case 1:
		            	ResidentMenu.menu(conn);
		            	break;
		            case 2:
		                //ApplicantMenu appMenu = new ApplicantMenu();
		                ApplicantMenu.menu(conn);
		                break;
		            case 3:
		                //AdminMenu adminMenu = new AdminMenu();
		                AdminMenu.menu(conn);
		                break;
		            case 4:
		                dontQuit = false;
		                break;
		            default:
		            	System.out.println("Sorry, command not recognized. Please try again.");
		            }
			}
			reader.close();
			
		}catch (SQLException ex) {
			System.out.println(ex);
		}finally{
			if (conn != null) {
				try {
					conn.close();
				}catch (SQLException e){
					// ignored 
				}
			}
		}
    	

    }
    
	static String readEntry(String prompt) {
		try {
			StringBuffer buffer = new StringBuffer();
			System.out.print(prompt);
			System.out.flush();
			int c = System.in.read();
			while(c != '\n' && c != -1) {
				buffer.append((char)c);	
				c = System.in.read();
			}
			return buffer.toString().trim();
		}catch (IOException e){
			return "";
		}
	}
}
