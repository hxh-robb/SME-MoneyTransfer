package ft.ex.mybatis;

import ft.spec.model.TransferAddon;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferAddonModeHandler extends BaseTypeHandler<TransferAddon.Mode> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TransferAddon.Mode parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public TransferAddon.Mode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TransferAddon.Mode.of(rs.getInt(columnName));
    }

    @Override
    public TransferAddon.Mode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TransferAddon.Mode.of(rs.getInt(columnIndex));
    }

    @Override
    public TransferAddon.Mode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TransferAddon.Mode.of(cs.getInt(columnIndex));
    }
}
