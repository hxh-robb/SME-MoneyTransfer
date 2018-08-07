package com.sme.mts.test.service;

import com.sme.mts.test.Testcase;
import com.sme.mts.service.TransactionTestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionTest extends Testcase {
    @Autowired
    private TransactionTestService service;

    @Test
    public void test(){
        service.clear();
        service.foobar();
    }
}
