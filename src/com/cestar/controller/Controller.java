package com.cestar.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.cestar.dao.PatientDao;
import com.cestar.model.Patient;

public class Controller {
	PatientDao dao = new PatientDao();
	Scanner sc = new Scanner(System.in);

	/*
	 * calls DAO read() method to fetch records from db
	 */
	public void display() {
		dao.read();
	}

	/*
	 * method collects user input and passes it to DAO
	 */
	public void insert() {
		System.out.println("Please enter id of the patient to insert record");
		int patientId = sc.nextInt();

		// consume space to take string i/p after taking int i/p
		sc.nextLine();

		System.out.println("Please enter name of the patient");
		String name = sc.nextLine();

		System.out.println("Please enter contact of the patient");
		String contact = sc.nextLine();

		System.out.println("Please enter region of the patient");
		String region = sc.nextLine();

		System.out.println("Please enter disease of the patient");
		String disease = sc.nextLine();

		System.out.println("Please enter visit date of the patient in the format YYYY-MM-DD");
		String date = sc.nextLine();
		// convert string to date
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		Date visitDate = null;
		try {
			visitDate = f1.parse(date);
		} catch (ParseException e) {
			System.out.println("Please enter date in the format YYYY-MM-DD. you have entered " + date);
			e.printStackTrace();
		}

		// create new patient using above inputs
		Patient patient = new Patient(patientId, name, contact, region, disease, visitDate);

		// pass this patient object to dao class
		dao.insert(patient);
	}

	/*
	 * collects user id and passes to DAO to delete the record
	 */
	public void delete() {
		System.out.println("Enter id of the patient to delete the record");
		int patient_id = sc.nextInt();

		dao.delete(patient_id);
	}

	/*
	 * collects user data and passes to DAO to update the record
	 */
	public void update() {
		System.out.println("Please enter id of the patient to update the record");
		int curr_id = sc.nextInt();

		System.out.println("Please enter the new id of the patient");
		int new_id = sc.nextInt();
		sc.nextLine();

		System.out.println("Please enter new name of the patient");
		String new_name = sc.nextLine();

		System.out.println("Please enter new contact of the patient");
		String new_contact = sc.nextLine();

		System.out.println("Please enter new region of the patient");
		String new_region = sc.nextLine();

		System.out.println("Please enter new disease of the patient");
		String new_disease = sc.nextLine();

		System.out.println("Please enter new visit date of the patient in the format YYYY-MM-DD");
		String date = sc.nextLine();
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		Date new_visitDate = null;
		try {
			new_visitDate = f1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// create new patient using above inputs
		Patient patient = new Patient(new_id, new_name, new_contact, new_region, new_disease, new_visitDate);

		// pass this patient object to dao class

		dao.updateRecById(curr_id, patient);
	}

}