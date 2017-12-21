package org.justgive.database;

import org.justgive.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper for a Map.
 */
public class DBResultRow {
	// All results are Strings for now
	private Map<String, String> results = new HashMap<String, String>();

	public void put(String columnName, String columnValue){
		if(StringUtil.isEmpty(columnName)){
			return;
		}

		// Keys saved as lower case to make retrieval case-sensitive
		columnName = columnName.toLowerCase();

		results.put(columnName, columnValue);
	}

	public String get(String columnName){
		if(StringUtil.isEmpty(columnName)){
			return "";
		}

		columnName = columnName.toLowerCase();

		String columnValue = results.get(columnName);

		if(StringUtil.isEmpty(columnValue)){
			return "";
		}

		return columnValue;
	}
}
