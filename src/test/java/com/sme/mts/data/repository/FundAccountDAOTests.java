package com.sme.mts.data.repository;

import com.sme.mts.RepositoryTestcase;
import com.sme.mts.data.entity.FundAccount;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FundAccountDAOTests extends RepositoryTestcase<FundAccount, FundAccountDAO.Filter> {
    private static boolean init = false;
    private static final Map<String, FundAccount> expected = new ConcurrentHashMap<>();
    private static final String [] titles = new String[]{"中国银行", "中国农业银行", "中国工商银行", "中国建设银行"};
    private static final String [] names = new String[]{
        "boc-zhao", "boc-qian", "boc-sun", "boc-li",
        "abc-zhou", "abc-wu", "abc-zheng", "abc-wang",
        "icbc-feng", "icbc-chen", "icbc-zhu", "icbc-wei",
        "ccb-jiang", "ccb-shen", "ccb-hang", "ccb-yang",
    };

    static final Map<String, FundAccount> dummy = new ConcurrentHashMap<>();

    @Autowired
    private FundAccountDAO fundAccountDAO;

    @Override
    protected Map<String, FundAccount> expected() {
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
    protected FundAccountDAO dao() {
        return fundAccountDAO;
    }

    @Override
    protected FundAccount newData() {
        return new FundAccount();
    }

    @Override
    protected FundAccountDAO.Filter newFilter() {
        return new FundAccountDAO.Filter();
    }

    @Override
    protected void setupData(FundAccount data) {
        data.setType(FundAccount.Type.DEPOSIT_ACCOUNT);
        int titleIdx = RandomUtils.nextInt(0,titles.length);
        data.setTitle(titles[titleIdx] + "-" + data.getId().split("-")[1]);
        data.setName("dummy-" + names[RandomUtils.nextInt(titleIdx*4,titleIdx*4+4)] + "-" + data.getId().split("-")[0]);
    }

    @Override
    protected void setupModify(FundAccount data) {
        data.setUo("Robb");
        data.setDe(true);
    }

    @Test
    public void t0_dummy_data(){
        generateDummyData(RandomUtils.nextInt(20,100), data -> dummy.put(data.getId(), data));
    }
}
