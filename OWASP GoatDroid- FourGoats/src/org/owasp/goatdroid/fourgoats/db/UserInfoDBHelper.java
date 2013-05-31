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

import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class UserInfoDBHelper {

	private static final String DATABASE_NAME = "userinfo.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "info";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private SQLiteStatement updateStmt;
	private SQLiteStatement deleteStmt;

	private static final String INSERT_INFO = "insert into "
			+ TABLE_NAME
			+ " (sessionToken, userName, isPublic, autoCheckin, isAdmin) values (?,?,?,?,?)";
	private static final String UPDATE_PREFERENCES = "update " + TABLE_NAME
			+ " set isPublic = ?, autoCheckin = ? where id = 1";
	private static final String DELETE_INFO = "delete from " + TABLE_NAME;

	public UserInfoDBHelper(Context context) {
		this.context = context;
		UserInfoOpenHelper openHelper = new UserInfoOpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmt = this.db.compileStatement(INSERT_INFO);
		this.updateStmt = this.db.compileStatement(UPDATE_PREFERENCES);
		this.deleteStmt = this.db.compileStatement(DELETE_INFO);

	}

	public String getSessionToken() {

		Cursor cursor = this.db.query(TABLE_NAME,
				new String[] { "sessionToken" }, null, null, null, null, null);
		if (cursor.moveToFirst())
			return cursor.getString(0);
		else
			return "";
	}

	public String getUserName() {

		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "userName" },
				null, null, null, null, null);
		if (cursor.moveToFirst())
			return cursor.getString(0);
		else
			return "";
	}

	public HashMap<String, String> getPreferences() {

		HashMap<String, String> preferences = new HashMap<String, String>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "isPublic",
				"autoCheckin" }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			preferences.put("isPublic", cursor.getString(0));
			preferences.put("autoCheckin", cursor.getString(1));
			return preferences;
		} else
			return preferences;
	}

	public boolean getIsAdmin() {

		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "isAdmin" },
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			if (cursor.getString(0).equals("true"))
				return true;
			else
				return false;

		} else
			return false;
	}

	public void insertSettings(HashMap<String, String> settings) {
		this.insertStmt.bindString(1, settings.get("sessionToken"));
		this.insertStmt.bindString(2, settings.get("userName"));
		this.insertStmt.bindString(3, settings.get("isPublic"));
		this.insertStmt.bindString(4, settings.get("autoCheckin"));
		this.insertStmt.bindString(5, settings.get("isAdmin"));
		this.insertStmt.executeInsert();
	}

	public void updatePreferences(String isPublic, String autoCheckin) {
		this.updateStmt.bindString(1, isPublic);
		this.updateStmt.bindString(2, autoCheckin);
		this.updateStmt.executeInsert();
	}

	public void deleteInfo() {
		this.deleteStmt.executeInsert();
	}

	public void close() {
		this.db.close();
	}

	private class UserInfoOpenHelper extends SQLiteOpenHelper {

		public UserInfoOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL("CREATE TABLE "
						+ TABLE_NAME
						+ "(id INTEGER PRIMARY KEY, sessionToken TEXT, userName TEXT, isPublic INT, "
						+ "autoCheckin INT, isAdmin INT)");
			} catch (RuntimeException e) {

			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Oh snap", "Upgrading");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}