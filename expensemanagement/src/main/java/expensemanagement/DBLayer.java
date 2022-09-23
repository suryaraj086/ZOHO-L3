package expensemanagement;

import java.sql.Connection;
import java.sql.Statement;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import connection.ConnectionUtility;
import exception.CustomException;
import java.util.HashMap;

public class DBLayer {

	public void createTable() throws CustomException, Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.execute(
					"create table trip(tridid int Not null primary key,tripname varchar(max),startdate Date,enddate Date)");
			stmt.execute(
					"create table user(userid int Not null primary key,username varchar(max),phonenumber bigint,password varchar(max))");
			stmt.execute(
					"create table friends(userid int foreign key reference user(userid),friendid int foreign key reference user(userid))");
			stmt.execute(
					"create table tripmembers(tridid varchar(max) Not null primary key,memberid int reference user(userid),trip name varchar(max),startdate Date,enddate Date)");
			stmt.execute(
					"create table tripexpense(tridid int foreign key reference trip(tripid),fromuser int,touser int,amount int,description varchar(max)");
		} catch (Exception e) {
			throw new CustomException("sql error");
		} finally {
			stmt.close();
			con.close();
		}
	}

	public void jsonConverter(Object map) {
		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		String gsonString = gson.toJson(map, gsonType);
		System.out.println(gsonString);
	}

	public void passwordHashing() {
		String passwordToHash = "password";
		String generatedPassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);
	}
}

//	public String addNewTrip(Trip obj) {
//
//	Connection con = ConnectionUtility.CONNECTION.getConnection();
//	Statement stmt = null;
//	try {
//	PreparedStatement stmt=con.prepareStatement("insert into trip values(?,?,?,?)");  
//	stmt.setInt(1,obj.getTripId());  
//	stmt.setString(2,obj.getTripName());  
//	stmt.setString(3,obj.getStartDate());
//	int i=stmt.executeUpdate();  
//	return i+" records inserted";  
//	}
//	catch(Exception e)
//	{}finally{
//	stmt.close();
//	}
//
