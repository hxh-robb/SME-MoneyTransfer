package com.sme.mts.test.suites;


import com.sme.mts.test.data.repository.FundAccountDocDAOTests;
import com.sme.mts.test.data.repository.TransferTaskDAOTests;
import com.sme.mts.test.data.repository.FundAccountDAOTests;
import com.sme.mts.test.data.repository.MetadataDAOTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Please start MariaDB and MongoDB docker containers before running this Testsuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    MetadataDAOTests.class,
    FundAccountDAOTests.class,
    FundAccountDocDAOTests.class,
    TransferTaskDAOTests.class
})
public class RepositoryTests {
}
