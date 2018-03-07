package ft.test.suites;

import ft.test.addon.CoordinatorTest;
import ft.test.biz.FundAccountServiceTest;
import ft.test.biz.MetadataServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    MetadataServiceTest.class,
    FundAccountServiceTest.class,
    CoordinatorTest.class
})
public class Tests {
}
