package com.base.mybatis.handler;

import com.base.util.IntEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntEnumTypeHandler extends BaseTypeHandler<IntEnum> {
	
	private Class<IntEnum> type;

	public IntEnumTypeHandler(Class<IntEnum> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null.");
		}			
		this.type = type;
	}

	private IntEnum convert(int status) {
		IntEnum[] objs = type.getEnumConstants();
		for (IntEnum em : objs) {
			if (em.getValue().intValue() == status) {
				return em;
			}
		}
		return null;
	}

	@Override
	public IntEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return convert(rs.getInt(columnName));
	}

	@Override
	public IntEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return convert(rs.getInt(columnIndex));
	}

	@Override
	public IntEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return convert(cs.getInt(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			IntEnum enumObj, JdbcType jdbcType) throws SQLException {
		// baseTypeHandler已经做了parameter的null判断
		ps.setInt(i, enumObj.getValue());

	}
}
