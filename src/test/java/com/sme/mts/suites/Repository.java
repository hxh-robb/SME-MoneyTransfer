package com.sme.mts.suites;


import com.sme.mts.data.repository.FundAccountDAOTests;
import com.sme.mts.data.repository.TransferTaskDAO;
import com.sme.mts.data.repository.TransferTaskDAOTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Please start MariaDB and MongoDB docker containers before running this Testsuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    FundAccountDAOTests.class,
    TransferTaskDAOTests.class
})
public class Repository {
}
