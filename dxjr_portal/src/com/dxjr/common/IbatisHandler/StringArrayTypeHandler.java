package com.dxjr.common.IbatisHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Comments
 * <p>
 * Description :这里写描述<br/>
 *
 * @author wangbo
 * @version 2016/8/19
 * @package com.dxjr.portal.tzjinterface.entity
 */
@MappedTypes({String[].class})
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String[] strings, JdbcType jdbcType) throws SQLException {
        StringBuffer result = new StringBuffer();
        for (String value : strings)
            result.append(value).append(",");
        result.deleteCharAt(result.length()-1);
        preparedStatement.setString(i, result.toString());

    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return getStringArray(resultSet.getString(s));
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.getStringArray(resultSet.getString(i));
    }

    @Override
    public String[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.getStringArray(callableStatement.getString(i));
    }
    private String[] getStringArray(String columnValue) {
        if (columnValue == null)
            return null;
        return columnValue.split(",");
    }
}
