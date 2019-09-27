package project;

import java.sql.*;
import java.io.*;
import com.mysql.cj.jdbc.Driver;
import java.util.*;

public class Housing_Queries {
		/*
	public static void main(String args[]) throws SQLException {
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
			viewTable(conn, "bcsh", "1234");
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
	*/
	
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
	
	public static void printResidentInfo(Connection con, String dbName, String userID) throws SQLException {
		PreparedStatement pstmt = null;
		String query = "SELECT * " +
		               "FROM " + dbName + ".resident JOIN " + dbName +".applicant ON app_sid = res_sid " +
		               "WHERE resident.res_sid=?";
		try {
			pstmt = con.prepareStatement(query);				//create con(nection) prepareStatement
			pstmt.clearParameters();							//parameters stay until cleared
			pstmt.setString(1,userID);							//from index 1 to userID
		    ResultSet rs = pstmt.executeQuery();				//retrieves results
		    
		    while (rs.next()) {
		    	String firstName = rs.getString("fname");
		    	String midName = rs.getString("mname");
		    	String lastName = rs.getString("lname");
		        int residentID = rs.getInt("res_sid");
		        int buildingNo = rs.getInt("build_no");
		        int housingNo = rs.getInt("housing_no");
		        java.sql.Date moveInDate = rs.getDate("move_in_date");
		        java.sql.Date moveOutDate = rs.getDate("move_in_date");
		        int dues = rs.getInt("dues");
		        int duesPaid = rs.getInt("dues_paid");
		        
		        //Test
		        System.out.println(	firstName + "\t" + midName + "\t" + lastName +
		        					"\t"+ residentID + "\t" + buildingNo +
		                           "\t" + housingNo + "\t" + moveInDate +
		                           "\t" + moveOutDate + "\t" + dues +
		                           "\t" + duesPaid);
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	public static void adminViewApplicantTable(Connection con, String dbName, String userID) throws SQLException {
		PreparedStatement pstmt = null;
		String query = "SELECT * " +
		               "FROM " + dbName + ".applicant JOIN " + dbName + ".application ON applicant.app_sid=application.app_sid "+
		               "WHERE NOT EXISTS (SELECT * FROM " + dbName + ".resident WHERE res_sid = applicant.app_sid) "+
		               "ORDER BY app_date ASC, alumni ASC";
	
		try {
			pstmt = con.prepareStatement(query);				//create con(nection) prepareStatement
			pstmt.clearParameters();							//parameters stay until cleared
		//	pstmt.setString(1,userID);							//from index 1 to userID
		    ResultSet rs = pstmt.executeQuery();				//retrieves results
		    
		    while (rs.next()) {
		    	String firstName = rs.getString("fname");
		    	String midName = rs.getString("mname");
		    	String lastName = rs.getString("lname");
		        int studentID = rs.getInt("app_sid");
		        int phone = rs.getInt("phone");
		        boolean alumni = rs.getBoolean("alumni");
		        String gender = rs.getString("gender");
		        String address = rs.getString("address");
		        String majColl = rs.getString("majColl");
		        String majDept = rs.getString("majDept");
		        int yearOfStudy = rs.getInt("year_of_study");
		        boolean married = rs.getBoolean("married");
		        int ssn = rs.getInt("app_ssn");
		        int feesDue = rs.getInt("fees_due");
		        String email = rs.getString("email");
		        int emergContact = rs.getInt("emergency_contact");
		        int citizenStatus = rs.getInt("citizenship_status");
		        
	//Test
		        System.out.println(	firstName + "\t" + midName + "\t" + lastName + "\t" +
					        		studentID + "\t" + phone + "\t" + alumni + "\t" +
					        		gender + "\t" + address + "\t" + majColl + "\t" +
					        		majDept + "\t" + yearOfStudy + "\t" + married + "\t" +
					        		ssn + "\t" + feesDue + "\t" + email + "\t" +
					        		emergContact + "\t" + citizenStatus);
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	/*
	//Applicants ONLY: applicants can check availability of spec. apartment
	public static void checkApartmentAvailability(Connection con, String dbName, String userID, String housingTypeID) throws SQLException {
		Statement stmt = null;
		String query = "SELECT * " +
		               "FROM " + dbName + ".housing " +
		               "ORDER BY housing.current_occupancy";
	
		try {
			stmt = con.createStatement();							//create con(nection) Statement
		    ResultSet rs = stmt.executeQuery(query);				//retrieves results
		    
		    while (rs.next()) {
		    	int buildingNo = rs.getInt("build_no");
		    	int housingNo = rs.getInt("housing_no");
		    	String housingType = rs.getString("housing_type_id");
		    	int currOccupancy = rs.getInt("current_occupancy");
		  
	//Test
		        System.out.println(	"Housing Preference:\t" + housingType + "\n" +
		        					"Building Number:\t" + buildingNo + "\n" +
					        		"Housing Number:\t" + housingNo + "\n" +
		        					"Avaiability:\t" + currOccupancy);
	
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (stmt != null) {
				stmt.close();
			}
		}
	}
		*/
	
	
	public static void checkApartmentAvailability(Connection con, String dbName) throws SQLException {
		Statement stmt = null;
		String query = "SELECT * "+
						"FROM " + dbName + ".housing AS H JOIN " + dbName + ".housing_type AS HT "+
						"ON H.housing_type_id = HT.housing_type_id " +
						"LEFT JOIN ( " +
						"SELECT R.build_no, R.housing_no, COUNT(*) AS curr_occupancy " +
						"FROM " + dbName + ".resident as R " +
						"GROUP BY R.build_no, R.housing_no ) " +
						"AS O ON H.build_no = O.build_no AND H.housing_no = O.housing_no " +
						"WHERE curr_occupancy < max_occupancy OR curr_occupancy IS NULL";
						

		try {
			stmt = con.createStatement();							//create con(nection) Statement
			ResultSet rs = stmt.executeQuery(query);				//retrieves results
			
			while (rs.next()) {
				int buildingNo = rs.getInt("H.build_no");
				int housingNo = rs.getInt("H.housing_no");
				String housingType = rs.getString("H.housing_type_id");
				int currOccupancy = rs.getInt("curr_occupancy");
				int maxOccupancy = rs.getInt("max_occupancy");
				int availability = maxOccupancy - currOccupancy;
			
				System.out.println(	"Housing Type:\t" + housingType + "\n" +
									"Building Number:\t" + buildingNo + "\n" +
									"Housing Number:\t" + housingNo + "\n" +
									"Avaiability:\t" + availability);

			}
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	/*
	//Applicants ONLY: applicants can check all availabilities 
	public static void checkApartmentAvailability(Connection con, String dbName) throws SQLException {
		Statement stmt = null;
		String query = "SELECT * "+
						"FROM " + dbName + ".housing AS H, " + dbName + ".housing_type AS HT "+
						"ORDER BY H.current_occupancy";

		try {
			stmt = con.createStatement();							//create con(nection) Statement
			ResultSet rs = stmt.executeQuery(query);				//retrieves results
			
			while (rs.next()) {
				int buildingNo = rs.getInt("build_no");
				int housingNo = rs.getInt("housing_no");
				String housingType = rs.getString("housing_type_id");
				int currOccupancy = rs.getInt("current_occupancy");
				int maxOccupancy = rs.getInt("max_occupancy");
				int availability = maxOccupancy - currOccupancy;
			
				System.out.println(	"Housing Preference:\t" + housingType + "\n" +
									"Building Number:\t" + buildingNo + "\n" +
									"Housing Number:\t" + housingNo + "\n" +
									"Avaiability:\t" + availability);

			}
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	*/
	
	public static void checkSpecApartmentAvailability(Connection con, String dbName, String housingTypeID) throws SQLException {
		PreparedStatement pstmt = null;
		String query = "SELECT * "+
				"FROM " + dbName + ".housing AS H JOIN " + dbName + ".housing_type AS HT "+
				"ON H.housing_type_id = HT.housing_type_id " +
				"LEFT JOIN ( " +
				"SELECT R.build_no, R.housing_no, COUNT(*) AS curr_occupancy " +
				"FROM " + dbName + ".resident as R " +
				"GROUP BY R.build_no, R.housing_no ) " +
				"AS O ON H.build_no = O.build_no AND H.housing_no = O.housing_no " +
				"WHERE housing.housing_type_id=? AND (curr_occupancy < max_occupancy  OR curr_occupancy IS NULL)";

		try {
			pstmt = con.prepareStatement(query);							//create con(nection) Statement
			pstmt.clearParameters();
			pstmt.setString(1, housingTypeID);
			ResultSet rs = pstmt.executeQuery(query);				//retrieves results
			
			while (rs.next()) {
				int buildingNo = rs.getInt("H.build_no");
				int housingNo = rs.getInt("H.housing_no");
				String housingType = rs.getString("H.housing_type_id");
				int currOccupancy = rs.getInt("curr_occupancy");
				int maxOccupancy = rs.getInt("max_occupancy");
				int availability = maxOccupancy - currOccupancy;
			
				System.out.println(	"Housing Type:\t" + housingType + "\n" +
									"Building Number:\t" + buildingNo + "\n" +
									"Housing Number:\t" + housingNo + "\n" +
									"Avaiability:\t" + availability);

			}
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	
	/*
	//Applicants ONLY: checks for a specific apartment avaiability 
		public static void checkSpecApartmentAvailability(Connection con, String dbName, String housingTypeID) throws SQLException {
			Statement stmt = null;
			String query = "SELECT * "+
							"FROM " + dbName + ".housing AS H, " + dbName + ".housing_type AS HT "+
							"WHERE H.housing_type_id = " + housingTypeID+
							"ORDER BY H.current_occupancy";

			try {
				stmt = con.createStatement();							//create con(nection) Statement
				ResultSet rs = stmt.executeQuery(query);				//retrieves results
				
				while (rs.next()) {
					int buildingNo = rs.getInt("build_no");
					int housingNo = rs.getInt("housing_no");
					String housingType = rs.getString("housing_type_id");
					int currOccupancy = rs.getInt("current_occupancy");
					int maxOccupancy = rs.getInt("max_occupancy");
					int availability = maxOccupancy - currOccupancy;
				
					System.out.println(	"Housing Preference:\t" + housingType + "\n" +
										"Building Number:\t" + buildingNo + "\n" +
										"Housing Number:\t" + housingNo + "\n" +
										"Avaiability:\t" + availability);

				}
			} catch (SQLException e ) {
				System.out.println(e);
			} finally {	//Executes no matter what
				if (stmt != null) {
					stmt.close();
				}
			}
		}
	*/
		
		//Applicants ONLY: applicants checks application status
		public static boolean checkApplicationStatus(Connection con, String dbName, String userID) throws SQLException {
			PreparedStatement pstmt = null;
			String query = "SELECT * " +
			               "FROM " + dbName + ".application " +
			               "WHERE application.app_sid=?";
		
			try {  
				pstmt = con.prepareStatement(query);							//create con(nection) Statement
			    pstmt.clearParameters();							//parameters stay until cleared
				pstmt.setString(1,userID);							//from index 1 to userID
			    ResultSet rs = pstmt.executeQuery();
			    
			    return rs.next();
			} catch (SQLException e ) {
				System.out.println(e);
			} finally {	//Executes no matter what
				if (pstmt != null) {
					pstmt.close();
				}
			}
			return false;
		}
	/*
		//Applicants ONLY: applicants checks application status
	public static void checkApplicationStatus(Connection con, String dbName, String userID) throws SQLException {
		Statement stmt = null;
		String query = "SELECT * " +
		               "FROM " + dbName + ".application " +
		               "WHERE application.app_sid=" + userID;
	
		try {  
			stmt = con.createStatement();							//create con(nection) Statement
		    ResultSet rs = stmt.executeQuery(query);				//retrieves results
		    
		    while (rs.next()) {
		    	String appStatus = rs.getString("app_status");
	//Test
		        System.out.println(	"Application Status:\t" + appStatus);
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	*/
		
		
		public static void adminChecksApplicationStatus(Connection con, String dbName) throws SQLException {
			Statement stmt = null;
			String query = "SELECT * "+
			"FROM bcsh.applicant JOIN bcsh.application ON applicant.app_sid = application.app_sid LEFT JOIN bcsh.resident ON applicant.app_sid = resident.res_sid "+
			"WHERE res_sid IS NULL";

			try {  
				stmt = con.createStatement();							//create con(nection) Statement
				ResultSet rs = stmt.executeQuery(query);				//retrieves results
				
				while (rs.next()) {
					String fname = rs.getString("fname");
					String mname = rs.getString("mname");
					String lname = rs.getString("lname");
					//String appStatus = rs.getString("app_status");
					System.out.println(	"Name:\t" + fname + " " + mname + " " + lname); // + " " + "Application Status:\t" + appStatus);
				}
			} catch (SQLException e ) {
				System.out.println(e);
			} finally {	//Executes no matter what
				if (stmt != null) {
					stmt.close();
				}
			}
		}
		
	
	//Admin Use ONLY: applicants checks application status
		/*
		public static void adminChecksApplicationStatus(Connection con, String dbName) throws SQLException {
			Statement stmt = null;
			String query = "SELECT A.app_status, AP.fname, AP.mname, AP.lname " +
							"FROM " + dbName + ".application AS A," + dbName + ".applicant AS AP";

			try {  
				stmt = con.createStatement();							//create con(nection) Statement
				ResultSet rs = stmt.executeQuery(query);				//retrieves results
				
				while (rs.next()) {
					String fname = rs.getString("fname");
					String mname = rs.getString("mname");
					String lname = rs.getString("lname");
					String appStatus = rs.getString("app_status");
					System.out.println(	"Name:\t" + fname + " " + mname + " " + lname + " " + "Application Status:\t" + appStatus);
				}
			} catch (SQLException e ) {
				System.out.println(e);
			} finally {	//Executes no matter what
				if (stmt != null) {
					stmt.close();
				}
			}
		}
		*/
	
	//Returns application status as a string
		public static String getApplicationStatus(Connection con, String userID) throws SQLException {
			String appStatus= "";
			Statement stmt = null;
			String query = "SELECT * " +
			               "FROM bcsh.application " +
			               "WHERE application.app_sid=" + userID;
		
			try {  
				stmt = con.createStatement();							//create con(nection) Statement
			    ResultSet rs = stmt.executeQuery(query);				//retrieves results
			    while (rs.next()) {
					appStatus = rs.getString("app_status");
				}
			} catch (SQLException e ) {
				System.out.println(e);
			} finally {	//Executes no matter what
				if (stmt != null) {
					stmt.close();
				}
			}
			return appStatus;
		}

		//checks if Applicant
		public static boolean checkIfIsApplicant(Connection con, String userID, String password) throws SQLException
		 {
			PreparedStatement pstmt = null;
			String query = "SELECT * " +
			               "FROM bcsh.applicant " +
			               "WHERE applicant.app_sid=? AND applicant.pw=?";
		
			try {  
				pstmt = con.prepareStatement(query);			//create con(nection) Statement
				pstmt.clearParameters();						//parameters stay until cleared
				pstmt.setString(1,userID);						//from index 1 to userID
				pstmt.setString(2, password);
			    ResultSet rs = pstmt.executeQuery();			//retrieves results
			    if (rs.next()){
			    	return true;
				}
			} catch (SQLException e ) {
				System.out.println(e);
			} finally {	//Executes no matter what
				if (pstmt != null) {
					pstmt.close();
				}
			}
			return false;
		}
			//checks if Resident
			public static boolean checkIfIsResident(Connection con, String userID, String password) throws SQLException
			{
			   PreparedStatement pstmt = null;
			   String query = "SELECT * " +
							  "FROM bcsh.applicant JOIN bcsh.resident ON applicant.app_sid=resident.res_sid " +
							  "WHERE resident.res_sid=? AND applicant.pw=?";
		   
			   try {  
				   pstmt = con.prepareStatement(query);			//create con(nection) Statement
				   pstmt.clearParameters();						//parameters stay until cleared
				   pstmt.setString(1,userID);						//from index 1 to userID
				   pstmt.setString(2, password);
				   ResultSet rs = pstmt.executeQuery();			//retrieves results
				   if (rs.next()){
					   return true;
				   }
			   } catch (SQLException e ) {
				   System.out.println(e);
			   } finally {	//Executes no matter what
				   if (pstmt != null) {
					   pstmt.close();
				   }
			   }
			   return false;
		   }
	
	
	public static String getApplicantPassword(Connection con, String userID)
	{
		PreparedStatement pstmt = null;
		String query = "SELECT pw " + 
				"FROM bcsh.applicant " + 
				"WHERE app_sid = ?";
		
		try {
			pstmt = con.prepareStatement(query);				//create con(nection) prepareStatement
			pstmt.clearParameters();							//parameters stay until cleared
			pstmt.setString(1,userID);							//from index 1 to userID
		    ResultSet rs = pstmt.executeQuery();				//retrieves results
		    
		    if (rs.next()) {
		        return rs.getString(1);
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	public static String getAdminPassword(Connection con, String userID)
	{
		PreparedStatement pstmt = null;
		String query = "SELECT pw " + 
				"FROM bcsh.administrator " + 
				"WHERE staff_id = ?";
		
		try {
			pstmt = con.prepareStatement(query);				//create con(nection) prepareStatement
			pstmt.clearParameters();							//parameters stay until cleared
			pstmt.setString(1,userID);							//from index 1 to userID
		    ResultSet rs = pstmt.executeQuery();				//retrieves results
		    
		    if (rs.next()) {
		        return rs.getString(1);
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	
	public static void createApplicant(Connection conn, String dbname, int SID, String pw, String fName, String mName, String lName, 
            long phoneNumber, boolean alumni, String gender, String address, String major,
            String department, int year_of_study, boolean married, int SSN, int feesDue, 
            String email, long emergencyContact, String citizenshipStatus) throws SQLException, IOException {
        
        
        PreparedStatement pstmt = null;
        String query = "insert into bcsh.applicant " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        
        try{
        pstmt = conn.prepareStatement(query);				//create con(nection) prepareStatement
	pstmt.clearParameters();							//parameters stay until cleared
	pstmt.setInt(1,SID);
	pstmt.setString(2, pw);
        pstmt.setString(3, fName);
        pstmt.setString(4, mName);
        pstmt.setString(5, lName);
        pstmt.setLong(6, phoneNumber);
        pstmt.setBoolean(7, alumni);
        pstmt.setString(8, gender);
        pstmt.setString(9, address);
        pstmt.setString(10, major);
        pstmt.setString(11, department);
        pstmt.setInt(12, year_of_study);
        pstmt.setBoolean(13, married);
        pstmt.setInt(14, SSN);
        pstmt.setInt(15, feesDue);
        pstmt.setString(16, email);
        pstmt.setLong(17, emergencyContact);
        pstmt.setString(18, citizenshipStatus);
                        
        
        pstmt.execute();
        System.out.println("Sucessfully added to the database.");
        


        }catch (SQLException e ) {
            System.out.println(e);
	} finally {	//Executes no matter what
            if (pstmt != null) {
                pstmt.close();
            }
        }
}
    public static void createApplication(Connection conn, String dbname, int SID, int arn, String status, 
            String housingPreference1, String housingPreference2, String housingPreference3, String preferVillage, int prefer_rm_sid,
            int preferQuarterMovein) throws SQLException, IOException {
        
        
        PreparedStatement pstmt = null;
        String query = "insert into bcsh.application " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        
        try{
        pstmt = conn.prepareStatement(query);				//create con(nection) prepareStatement
	pstmt.clearParameters();							//parameters stay until cleared
	pstmt.setInt(1,SID);
        pstmt.setInt(2, arn);
        pstmt.setString(3, status);
        
        
        // TODO: I'm not sure if this calender instance is actually setting a time
        //or is blank. Please check?
        Calendar cal = Calendar.getInstance();
        java.sql.Date jsqlD = new java.sql.Date( cal.getTime().getTime() );
        pstmt.setDate(4, jsqlD);
//        
        pstmt.setString(5, housingPreference1);
        pstmt.setString(6, housingPreference2);
        pstmt.setString(7, housingPreference3);
        pstmt.setString(8, preferVillage);
        if (prefer_rm_sid !=0)
        {
           	pstmt.setInt(9, prefer_rm_sid);
        }
        else
        {
        	pstmt.setNull(9, Types.INTEGER);
        }
        pstmt.setInt(10, preferQuarterMovein);

        pstmt.execute();
        System.out.println("Sucessfully added to the database.");
        


        }catch (SQLException e ) {
            System.out.println(e);
	} finally {	//Executes no matter what
            if (pstmt != null) {
                pstmt.close();
            }
        }
}
    public static void MaintanenceDepartmentReport(Connection conn, String dbName) throws SQLException, IOException {
        
        
        Statement stmt = null;
	String query = "SELECT housing_no, build_no, count(*) " +
	 "FROM " + dbName + ".maint_req " +
	"GROUP BY housing_no, build_no " +
        "HAVING count(*)>1 "+
        "ORDER BY count(*) DESC";
	
	try {  
            stmt = conn.createStatement();							//create con(nection) Statement
            ResultSet rs = stmt.executeQuery(query);				//retrieves results
		    
            System.out.println("MAINTENANCE DEPARTMENT REPORT\n");
            while (rs.next()) {
		String housing_no = rs.getString("housing_no");
                String building_no = rs.getString("build_no");
                String count = rs.getString("count(*)");
	//Test
		System.out.print("Housing Number:\t" + housing_no);
                System.out.print("Building Number:\t" + building_no);
                System.out.print("Count:\t" + count+"\n");
            }
	} catch (SQLException e ) {
            System.out.println(e);
	} finally {	//Executes no matter what
            if (stmt != null) {
		stmt.close();
            }
	}
    }
public static void createResident(Connection conn, String dbname, int res_sid, int build_no, int housing_no,  
            java.sql.Date move_in_date, java.sql.Date move_out_date, int dues, int duesPaid) throws SQLException, IOException {
        
        
        PreparedStatement pstmt = null;
        String query = "insert into resident " +
            "values(?, ?, ?, ?, ?, ?, ?) ";
        
        try{
        pstmt = conn.prepareStatement(query);				//create con(nection) prepareStatement
	pstmt.clearParameters();							//parameters stay until cleared
	pstmt.setInt(1,res_sid);
        pstmt.setInt(2, build_no);
        pstmt.setInt(3, housing_no);
        pstmt.setDate(4, move_in_date);
        pstmt.setDate(5, move_out_date);
        pstmt.setInt(6, dues);
        pstmt.setInt(7, duesPaid);
                        
        
        pstmt.execute();
        System.out.println("Sucessfully added to the database.");
        


        }catch (SQLException e ) {
            System.out.println(e);
	} finally {	//Executes no matter what
            if (pstmt != null) {
                pstmt.close();
            }
        }
}
	
	/*
	public static void printResidentInfo(Connection con, String dbName, String userID) throws SQLException {
		PreparedStatement pstmt = null;
		String query = "SELECT * " +
		               "FROM " + dbName + ".resident " +
		               "WHERE resident.res_sid=?";
		try {
			pstmt = con.prepareStatement(query);				//create con(nection) prepareStatement
			pstmt.clearParameters();							//parameters stay until cleared
			pstmt.setString(1,userID);							//from index 1 to userID
		    ResultSet rs = pstmt.executeQuery();				//retrieves results
		    
		    while (rs.next()) {
		        int residentID = rs.getInt("res_sid");
		        int buildingNo = rs.getInt("build_no");
		        int housingNo = rs.getInt("housing_no");
		        Date moveInDate = rs.getDate("move_in_date");
		        Date moveOutDate = rs.getDate("move_in_date");
		        int dues = rs.getInt("dues");
		        int duesPaid = rs.getInt("dues_paid");
		        
		        //Test
		        System.out.println(residentID + "\t" + buildingNo +
		                           "\t" + housingNo + "\t" + moveInDate +
		                           "\t" + moveOutDate + "\t" + dues +
		                           "\t" + duesPaid);
		    }
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {	//Executes no matter what
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	*/
	
}