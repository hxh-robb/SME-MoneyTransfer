package com.sme.mts.test.service;

import com.sme.mts.service.TransactionTestService;
import com.sme.mts.test.Testcase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TransactionTests extends Testcase {
    @Autowired @Qualifier("fund-account")
    private TransactionTestService fundAccount;

    @Autowired @Qualifier("transfer-task")
    private TransactionTestService transferTask;

    @Test
    public void testCallSeparately(){
        try {
            transferTask.clear();
            fundAccount.clear();

            transferTask.foobar();
            fundAccount.foobar();
        } catch (Throwable th){} finally {
            transferTask.list();
            fundAccount.list();
        }
    }

    @Test
    public void testCallTogether(){
        try {
            transferTask.clear();
            fundAccount.clear();

            transferTask.foobar(fundAccount::foobar);
        } catch (Throwable th){} finally {
            transferTask.list();
            fundAccount.list();
        }
    }
}
