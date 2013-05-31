/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.db;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class CheckinDBHelper {

	private static final String DATABASE_NAME = "checkins.db";
	private static final int DATABASE_VERSION = 1;
	private static final String CHECKINS_TABLE_NAME = "checkins";
	private static final String AUTO_CHECKIN_TABLE = "autocheckin";

	private Context context;
	private SQLiteDatabase db;

	private SQLiteStatement insertStmt;
	private SQLiteStatement insertAutoCheckinStmt;

	private static final String INSERT_CHECKIN = "insert into "
			+ CHECKINS_TABLE_NAME
			+ " (checkinID, venueName, dateTime, latitude, longitude) "
			+ "values (?,?,?,?,?)";

	private static final String INSERT_AUTO_CHECKIN = "insert into "
			+ AUTO_CHECKIN_TABLE
			+ " (latitude, longitude, dateTime) values (?,?,?)";

	public CheckinDBHelper(Context context) {
		this.context = context;
		CheckinOpenHelper openHelper = new CheckinOpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmt = this.db.compileStatement(INSERT_CHECKIN);
		this.insertAutoCheckinStmt = this.db
				.compileStatement(INSERT_AUTO_CHECKIN);
	}

	public ArrayList<HashMap<String, String>> getCheckins() {

		Cursor cursor = this.db
				.query(CHECKINS_TABLE_NAME, new String[] { "checkinID",
						"venueName", "dateTime", "latitude", "longitude" },
						null, null, null, null, null);
		ArrayList<HashMap<String, String>> checkins = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> checkin = new HashMap<String, String>();

		while (cursor.moveToNext()) {
			checkin.put("checkinID",
					cursor.getString(cursor.getColumnIndex("checkinID")));
			checkin.put("venueName",
					cursor.getString(cursor.getColumnIndex("venueName")));
			checkin.put("dateTime",
					cursor.getString(cursor.getColumnIndex("dateTime")));
			checkin.put("latitude",
					cursor.getString(cursor.getColumnIndex("latitude")));
			checkin.put("longitude",
					cursor.getString(cursor.getColumnIndex("longitude")));
			checkins.add(checkin);
		}

		return checkins;
	}

	public void insertCheckin(HashMap<String, String> checkin) {
		this.insertStmt.bindString(1, checkin.get("checkinID"));
		this.insertStmt.bindString(2, checkin.get("venueName"));
		this.insertStmt.bindString(3, checkin.get("dateTime"));
		this.insertStmt.bindString(4, checkin.get("latitude"));
		this.insertStmt.bindString(5, checkin.get("longitude"));
		this.insertStmt.executeInsert();
	}

	public void insertAutoCheckin(String latitude, String longitude,
			String dateTime) {
		this.insertAutoCheckinStmt.bindString(1, latitude);
		this.insertAutoCheckinStmt.bindString(2, longitude);
		this.insertAutoCheckinStmt.bindString(3, dateTime);
		this.insertAutoCheckinStmt.executeInsert();
	}

	public void close() {
		this.db.close();
	}

	private class CheckinOpenHelper extends SQLiteOpenHelper {

		public CheckinOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ CHECKINS_TABLE_NAME
					+ "(id INTEGER PRIMARY KEY, checkinID TEXT, venueName TEXT, "
					+ "dateTime TEXT, latitude TEXT, longitude TEXT)");
			db.execSQL("CREATE TABLE "
					+ AUTO_CHECKIN_TABLE
					+ "(id INTEGER PRIMARY KEY, dateTime TEXT, latitude TEXT, longitude TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Oh snap", "Upgrading");
			db.execSQL("DROP TABLE IF EXISTS " + CHECKINS_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + AUTO_CHECKIN_TABLE);
			onCreate(db);
		}
	}
}