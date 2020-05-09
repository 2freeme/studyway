package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VersionControlHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(VersionControlHelper.class.getName());
	
	private static final ThreadLocal<Version> VERSION_CTRL_HOLDER = new ThreadLocal<Version>();
	
	private static final String DEFAULT_FILED_NAME = "lastUpdateDate";
	
	private static final String DEFAULT_COLUMN_NAME = "LAST_UPDATE_DATE";
	
	private VersionControlHelper(){		
	}
	
	public static void setVersionControl() {
		setVersionControl(DEFAULT_FILED_NAME, DEFAULT_COLUMN_NAME);
	}
	
	public static void setVersionControl(String field, String column) {		
		if (VERSION_CTRL_HOLDER.get() == null) {
			VERSION_CTRL_HOLDER.set(new Version(field, column));
		}
	}
	
	public static Version getVersionControl() {
		return VERSION_CTRL_HOLDER.get();
	}
	
	public static void removeVersionControl() {
		if (getVersionControl() != null) {
			VERSION_CTRL_HOLDER.remove();
			logger.debug("已清除ThreadLocal VERSION_CTRL_HOLDER");
		}
	}
	
	public static class Version {
		
		private String field;		
		
		private String column;
		
		Version(String field, String column) {
			this.field = field;
			this.column = column;
		}
		
		public String getField() {
			return field;
		}
		
		public void setField(String field) {
			this.field = field;
		}
		
		public String getColumn() {
			return column;
		}
		
		public void setColumn(String column) {
			this.column = column;
		}
	}
	
}
