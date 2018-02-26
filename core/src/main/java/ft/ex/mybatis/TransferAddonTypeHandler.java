package ft.ex.mybatis;

import ft.spec.model.TransferAddon;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferAddonTypeHandler extends BaseTypeHandler<TransferAddon.Type> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TransferAddon.Type parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public TransferAddon.Type getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TransferAddon.Type.of(rs.getInt(columnName));
    }

    @Override
    public TransferAddon.Type getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TransferAddon.Type.of(rs.getInt(columnIndex));
    }

    @Override
    public TransferAddon.Type getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TransferAddon.Type.of(cs.getInt(columnIndex));
    }
}
