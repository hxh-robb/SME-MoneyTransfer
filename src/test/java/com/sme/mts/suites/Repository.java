package com.sme.mts.suites;


import com.sme.mts.data.repository.FundAccountDAOTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Please start MariaDB and MongoDB docker containers before running this Testsuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    FundAccountDAOTests.class,
//    FundAccountDocDAOTests.class
})
public class Repository {
}
