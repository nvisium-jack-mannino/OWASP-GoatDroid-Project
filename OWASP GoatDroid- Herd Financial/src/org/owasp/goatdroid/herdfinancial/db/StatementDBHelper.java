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

public class StatementDBHelper {

	private static final String DATABASE_NAME = "bankinginfo.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "history";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private SQLiteStatement deleteStmt;
	private static final String INSERT_TRANSACTION = "insert into "
			+ TABLE_NAME
			+ " (userName, date, amount, name, balance) values (?,?,?,?,?)";
	private static final String DELETE_TRANSACTION = "delete from "
			+ TABLE_NAME + " where id = ?";

	public StatementDBHelper(Context context) {
		this.context = context;
		StatementOpenHelper openHelper = new StatementOpenHelper(this.context);
		SQLiteDatabase.loadLibs(context);
		this.db = openHelper.getWritableDatabase("havey0us33nmyb@seball");
		this.insertStmt = this.db.compileStatement(INSERT_TRANSACTION);
		this.deleteStmt = this.db.compileStatement(DELETE_TRANSACTION);
	}

	public String[] getStatementArray(String selection) {

		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "date",
				"amount", "name", "balance" }, selection, null, null, null,
				null);
		String[] statement = new String[cursor.getCount()];
		int count = 0;
		while (cursor.moveToNext()) {
			String statementItem = "Date: " + cursor.getString(0)
					+ "\nAmount: " + cursor.getString(1) + "\nDescription: "
					+ cursor.getString(2) + "\nBalance: " + cursor.getString(3);
			statement[count] = statementItem;
			count++;
		}
		return statement;
	}

	public Cursor getStatementCursor(String selection) {

		return this.db.query(TABLE_NAME, new String[] { "date", "amount",
				"name", "balance" }, selection, null, null, null, null);
	}

	public String getStatementCSV(String selection) {

		String csv = "";
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "date",
				"amount", "name", "balance" }, selection, null, null, null,
				null);
		while (cursor.moveToNext()) {
			csv += cursor.getString(0) + "\t" + cursor.getString(1) + "\n"
					+ cursor.getString(2) + "\t" + cursor.getString(3);
		}
		return csv;
	}

	public Cursor query(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		return this.db.query(TABLE_NAME, projection, selection, selectionArgs,
				null, null, sortOrder);
	}

	public void insert(HashMap<String, String> transactionData) {
		this.insertStmt.bindString(1, transactionData.get("userName"));
		this.insertStmt.bindString(2, transactionData.get("date"));
		this.insertStmt.bindString(3, transactionData.get("amount"));
		this.insertStmt.bindString(4, transactionData.get("name"));
		this.insertStmt.bindString(5, transactionData.get("balance"));
		this.insertStmt.executeInsert();
	}

	public void delete(int id) {
		this.deleteStmt.bindLong(1, id);
		this.deleteStmt.execute();
	}

	public void close() {
		this.db.close();
	}

	private class StatementOpenHelper extends SQLiteOpenHelper {

		public StatementOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME
					+ "(id INTEGER PRIMARY KEY, userName TEXT, date TEXT, amount TEXT, name TEXT, "
					+ "balance TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Oh snap", "Upgrading");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}