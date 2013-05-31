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
 * @created 2012
 */
package org.owasp.goatdroid.herdfinancial.db;

import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteStatement;
import net.sqlcipher.database.SQLiteDatabase;
import android.util.Log;

public class UserInfoDBHelper {

	private static final String DATABASE_NAME = "userinfo.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "info";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private SQLiteStatement deleteStmt;
	private SQLiteStatement updateAnswersStmt;
	private SQLiteStatement clearSessionStmt;
	private static final String INSERT_INFO = "insert into " + TABLE_NAME
			+ " (sessionToken, userName, accountNumber) values (?,?,?)";
	private static final String DELETE_INFO = "delete from " + TABLE_NAME;
	private static final String UPDATE_SECRET_QUESTIONS = "update "
			+ TABLE_NAME
			+ " SET answer1 = ?, answer2 = ?, answer3 = ? where id = 1";
	private static final String CLEAR_SESSION_TOKEN = "update info SET sessionToken = 0 where id = 1";

	public UserInfoDBHelper(Context context) {
		this.context = context;
		UserInfoOpenHelper openHelper = new UserInfoOpenHelper(this.context);
		SQLiteDatabase.loadLibs(this.context);
		this.db = openHelper.getWritableDatabase("hammer");
		this.insertStmt = this.db.compileStatement(INSERT_INFO);
		this.deleteStmt = this.db.compileStatement(DELETE_INFO);
		this.updateAnswersStmt = this.db
				.compileStatement(UPDATE_SECRET_QUESTIONS);
		this.clearSessionStmt = this.db.compileStatement(CLEAR_SESSION_TOKEN);
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

	public String getAccountNumber() {

		Cursor cursor = this.db.query(TABLE_NAME,
				new String[] { "accountNumber" }, null, null, null, null, null);
		if (cursor.moveToFirst())
			return cursor.getString(0);
		else
			return "";
	}

	public String getSecretQuestionAnswer(int index) {

		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "answer"
				+ Integer.toString(index) }, null, null, null, null, null);
		if (cursor.moveToFirst())
			return cursor.getString(0);
		else
			return "";
	}

	public void insertSettings(HashMap<String, String> settings) {
		this.insertStmt.bindString(1, settings.get("sessionToken"));
		this.insertStmt.bindString(2, settings.get("userName"));
		this.insertStmt.bindString(3, settings.get("accountNumber"));
		this.insertStmt.executeInsert();
	}

	public void updateAnswers(String answer1, String answer2, String answer3) {
		this.updateAnswersStmt.bindString(1, answer1);
		this.updateAnswersStmt.bindString(2, answer2);
		this.updateAnswersStmt.bindString(3, answer3);
		this.updateAnswersStmt.executeInsert();
	}

	public void clearSessionToken() {
		this.clearSessionStmt.executeInsert();

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
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME
					+ "(id INTEGER PRIMARY KEY, sessionToken TEXT, userName TEXT, accountNumber TEXT, "
					+ "answer1 TEXT, answer2 TEXT, answer3 TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Oh snap", "Upgrading");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}