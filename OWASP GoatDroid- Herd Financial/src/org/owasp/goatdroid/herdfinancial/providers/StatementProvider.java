package org.owasp.goatdroid.herdfinancial.providers;

import java.util.List;

import org.owasp.goatdroid.herdfinancial.db.StatementDBHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class StatementProvider extends ContentProvider {

	static final String PROVIDER = "org.owasp.goatdroid.herdfinancial.statementprovider";

	private static final UriMatcher sUriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		sUriMatcher.addURI(PROVIDER, "statements/#/#", 1);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		StatementDBHelper sdbh = new StatementDBHelper(this.getContext());
		Cursor cursor;
		try {
			if (sUriMatcher.match(uri) == 1) {

				List<String> pathSegments = uri.getPathSegments();
				selection = "startDate > "
						+ pathSegments.get(pathSegments.size() - 2)
						+ "and endDate < "
						+ pathSegments.get(pathSegments.size() - 1);
				cursor = sdbh.getStatementCursor(selection);
			} else {
				cursor = sdbh.query(projection, selection, selectionArgs,
						sortOrder);
			}
		} finally {
			sdbh.close();
		}
		return cursor;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
