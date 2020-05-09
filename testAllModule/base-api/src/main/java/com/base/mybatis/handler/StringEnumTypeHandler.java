package com.base.mybatis.handler;

import com.base.util.StringEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringEnumTypeHandler extends BaseTypeHandler<StringEnum> {
	
	private Class<StringEnum> type;

	public StringEnumTypeHandler(Class<StringEnum> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null.");
		}			
		this.type = type;
	}

	private StringEnum convert(String status) {
		StringEnum[] objs = type.getEnumConstants();
		for (StringEnum em : objs) {
			if (em.getValue() == status) {
				return em;
			}
		}
		return null;
	}

	@Override
	public StringEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return convert(rs.getString(columnName));
	}

	@Override
	public StringEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return convert(rs.getString(columnIndex));
	}

	@Override
	public StringEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return convert(cs.getString(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			StringEnum enumObj, JdbcType jdbcType) throws SQLException {
		// baseTypeHandler已经做了parameter的null判断
		ps.setString(i, enumObj.getValue());

	}
}
