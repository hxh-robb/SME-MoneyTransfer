package ft.ex.mybatis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

/**
 * Json string to map handler
 */
public class JsonHandler extends BaseTypeHandler<Map<String,Object>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        // do nothing, won't use json for update operation
    }

    private Map<String, Object> map(String json) {
        try {
            TypeReference<Map<String,Object>> ref = new TypeReference<Map<String, Object>>() {};
            if( null != json ) {
                ObjectMapper om = new ObjectMapper();
                return om.readValue(json, ref);
            }
            return Collections.EMPTY_MAP;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return map(rs.getString(columnName));
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return map(rs.getString(columnIndex));
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return map(cs.getString(columnIndex));
    }
}
