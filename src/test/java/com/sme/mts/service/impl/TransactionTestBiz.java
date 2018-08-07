package com.sme.mts.service.impl;

import com.sme.mts.service.TransactionTestService;

public abstract class TransactionTestBiz implements TransactionTestService {
    @Override
    public void foobar(Runnable... runs) {
        doFoobar();

        for (Runnable runner: runs) {
            runner.run();
        }
    }

    protected abstract void doFoobar();
}
