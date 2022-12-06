package com.cestar.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import com.cestar.model.Patient;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/*
 * This class performs CRUD operations on the database
 */
public class PatientDao {
	/*
	 * Establish connection with MySql database
	 */
	public Connection createConnection() {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/patientrecords";
		String user = "root";
		String pwd = "";

		try {
			// Register the Driver for Mysql
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/*
	 * Method to fetch records from the database table
	 */
	public void read() {
		Connection con = createConnection();
		String sql = "select * from patients";

		try {
			// Header format for output table
			String format = "%-10s| %-20s| %-13s| %-15s| %-15s| %-10s";
			System.out.println(String.format(format, "PatientId", "Name", "Contact", "Region", "Disease", "VisitDate"));
			System.out.println(
					"---------------------------------------------------------------------------------------------");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int patientid = rs.getInt("patientid");
				String name = rs.getString("name");
				String contact = rs.getString("contact");
				String region = rs.getString("region");
				String disease = rs.getString("disease");
				Date date = rs.getDate("visitdate");

				// display fetched record
				System.out.println(String.format(format, patientid + "", name + "", contact + "", region + "",
						disease + "", date + ""));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * method to insert a record to db table
	 */
	public void insert(Patient patient_to_insert) {
		Connection con = createConnection();
		String sql = "insert into patients values(?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, patient_to_insert.getPatientId());
			pstmt.setString(2, patient_to_insert.getName());
			pstmt.setString(3, patient_to_insert.getContact());
			pstmt.setString(4, patient_to_insert.getRegion());
			pstmt.setString(5, patient_to_insert.getDisease());

			// convert java.util.date to java.sql.date
			java.util.Date utilDate = patient_to_insert.getVisitDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String stringDate = dateFormat.format(utilDate);
			java.sql.Date sqlDate = java.sql.Date.valueOf(stringDate);

			pstmt.setDate(6, sqlDate);

			int status = pstmt.executeUpdate();
			if (status > 0)
				System.out.println("Patient record inserted successfully!!");
			else
				System.out.println("Patient Record insertion failure....Try Again!!");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(
					"Patient Id " + patient_to_insert.getPatientId() + " already exists!! Please enter a diff one");
			System.out.println("Patient Record insertion failure....Try Again!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * method to delete a record from db table
	 */
	public void delete(int id_to_delete) {
		Connection con = createConnection();
		String sql = "delete from patients where patientid=?";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id_to_delete);

			int status = pstmt.executeUpdate();
			if (status > 0)
				System.out.println("Patient record deleted successfully!!");
			else
				System.out.println("Patient Record deletion failure....Try Again!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * method to update a record in db table
	 */
	public void updateRecById(int curr_id, Patient updated_patient) {
		Connection con = createConnection();
		String sql = "update patients set patientid = ?, name=?, contact=?, region=?, disease=?, visitdate=? where patientid=?";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, updated_patient.getPatientId());
			pstmt.setString(2, updated_patient.getName());
			pstmt.setString(3, updated_patient.getContact());
			pstmt.setString(4, updated_patient.getRegion());
			pstmt.setString(5, updated_patient.getDisease());

			// convert java.util.date to java.sql.date
			java.util.Date utilDate = updated_patient.getVisitDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String stringDate = dateFormat.format(utilDate);
			java.sql.Date sqlDate = java.sql.Date.valueOf(stringDate);

			pstmt.setDate(6, sqlDate);
			pstmt.setInt(7, curr_id);

			int status = pstmt.executeUpdate();
			if (status > 0)
				System.out.println("Patient Record updated successfully!!");
			else
				System.out.println("Patient Record updation failure....Try Again!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}