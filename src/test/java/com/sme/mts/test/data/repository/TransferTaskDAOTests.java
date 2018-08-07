package com.sme.mts.test.data.repository;

import com.sme.mts.test.RepositoryTestcase;
import com.sme.mts.data.entity.FundAccount;
import com.sme.mts.data.entity.TransferTask;
import com.sme.mts.data.repository.TransferTaskDAO;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransferTaskDAOTests extends RepositoryTestcase<TransferTask, TransferTaskDAO.Filter> {
    private static boolean init = false;
    private static final Map<String, TransferTask> expected = new ConcurrentHashMap<>();
    private static final String [] beneficiaries = (
        "Andera\n" + "Lura\n" + "Elenor\n" + "Devorah\n" + "Tamatha\n" +
        "Jenell\n" + "Cleotilde\n" + "Lasandra\n" + "Evalyn\n" + "Tyree\n" +
        "Heidy\n" + "Selina\n" + "Lelia\n" + "Kristan\n" + "Eusebio\n" +
        "Frank\n" + "Lovetta\n" + "Myron\n" + "Lynda\n" + "Tresa\n" +
        "Jamaal\n" + "Dionna\n" + "Kittie\n" + "Eleonore\n" + "Sueann\n" +
        "Marni\n" + "Ozella\n" + "Jarrod\n" + "Ahmed\n" + "Dirk\n" +
        "Kaye\n" + "Emmitt\n" + "Shaunna\n" + "Katheleen\n" + "Song\n" +
        "Anita\n" + "Marcelino\n" + "Stephanie\n" + "Phillip\n" + "Savanna\n" +
        "Nicolas\n" + "Angelina\n" + "Reda\n" + "Adalberto\n" + "Mari\n" +
        "Eric\n" + "Britteny\n" + "Alexander\n" + "Moises\n" + "Shanell"
    ).split("\n");

    @Autowired
    private TransferTaskDAO transferTaskDAO;

    @Override
    protected Map<String, TransferTask> expected() {
        return expected;
    }

    @Override
    protected boolean isInitialized() {
        return init;
    }

    @Override
    protected void setInitialized(boolean bool) {
        init = bool;
    }

    @Override
    protected TransferTaskDAO dao() {
        return transferTaskDAO;
    }

    @Override
    protected TransferTask newData() {
        return new TransferTask();
    }

    @Override
    protected TransferTaskDAO.Filter newFilter() {
        return new TransferTaskDAO.Filter();
    }

    @Override
    protected void setupData(TransferTask data) {
        data.setType(TransferTask.Type.DEPOSIT);
        data.setBeneficiary(beneficiaries[RandomUtils.nextInt(0,beneficiaries.length)]);
        data.setAmount(100.0);
        data.setStatus("New");
        data.setState("Assigning");
        data.setRetry(false);
        // data.setFallback(null);
        data.setRef(UUID.randomUUID().toString().split("-")[1]);
        if(FundAccountDAOTests.dummy.isEmpty()) {
            data.setFundAccount("dummy-fund-account");
        } else {
            String key = FundAccountDAOTests.dummy.keySet().toArray()[
                RandomUtils.nextInt(0,FundAccountDAOTests.dummy.size())
            ].toString();
            FundAccount deps = FundAccountDAOTests.dummy.get(key);
            data.setPlatform(deps.getPlatform());
            data.setFundAccount(deps.getId());
        }
    }

    @Override
    protected void setupModify(TransferTask data) {
        data.setUo("Robb");
        data.setStatus("Pending");
        data.setState("Joker");
    }

    @Test
    public void t0_dummy_data(){
        generateDummyData(RandomUtils.nextInt(FundAccountDAOTests.dummy.size(),100));
    }
}
