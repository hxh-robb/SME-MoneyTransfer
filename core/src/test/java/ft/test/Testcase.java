package ft.test;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;


@RunWith(SpringRunner.class)  @SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("unit-test")
public abstract class Testcase {

    @Autowired
    protected DataSource ds;

    protected void reset(String table, AtomicBoolean resetDone) throws SQLException {
        if(resetDone.get())
            return;

        // Clean table `metadata`;
        try( Connection c = ds.getConnection() ) {
            try (PreparedStatement ps = c.prepareStatement(String.format("delete from %s",table))){
                ps.executeUpdate();
            }
        }
        resetDone.set(true);
    }
}
