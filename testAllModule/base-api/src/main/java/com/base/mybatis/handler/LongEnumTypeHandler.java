package com.base.mybatis.handler;

import com.base.util.LongEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LongEnumTypeHandler extends BaseTypeHandler<LongEnum> {
	
	private Class<LongEnum> type;

	public LongEnumTypeHandler(Class<LongEnum> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null.");
		}			
		this.type = type;
	}

	private LongEnum convert(long status) {
		LongEnum[] objs = type.getEnumConstants();
		for (LongEnum em : objs) {
			if (em.getValue().longValue() == status) {
				return em;
			}
		}
		return null;
	}

	@Override
	public LongEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return convert(rs.getLong(columnName));
	}

	@Override
	public LongEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return convert(rs.getLong(columnIndex));
	}

	@Override
	public LongEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return convert(cs.getLong(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			LongEnum enumObj, JdbcType jdbcType) throws SQLException {
		// baseTypeHandler已经做了parameter的null判断
		ps.setLong(i, enumObj.getValue());

	}
}
