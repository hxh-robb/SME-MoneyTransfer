package com.sme.mts.test.data.repository;

import com.sme.mts.test.RepositoryTestcase;
import com.sme.mts.test.data.document.FundAccount;
import com.sme.mts.test.data.document.Metadata;
import com.sme.mts.test.data.document.TransferAddon;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FundAccountDocDAOTests extends RepositoryTestcase<FundAccount, DocDAO.Filter> {
    private static boolean init = false;
    private static final Map<String, FundAccount> expected = new ConcurrentHashMap<>();
    private static final List<com.sme.mts.test.data.entity.FundAccount> entities = new ArrayList<>();
    private static final Map<String,String> holder = new HashMap<>();
    static {
        holder.put("zhao", "赵倩");
        holder.put("qian", "钱隼江");
        holder.put("sun", "孙俪州");
        holder.put("li", "李州");
        holder.put("zhou", "周五政");
        holder.put("wu", "吴政卫");
        holder.put("zheng", "郑隼望");
        holder.put("wang", "王丰");
        holder.put("feng", "辰丰");
        holder.put("chen", "陈竺丰");
        holder.put("zhu", "朱申威");
        holder.put("wei", "卫江");
        holder.put("jiang", "蒋申");
        holder.put("shen", "沈起航");
        holder.put("hang", "韩起阳");
        holder.put("yang", "杨起");
    }

    @Autowired
    private FundAccountDocDAO fundAccountDocDAO;


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
    protected FundAccountDocDAO dao() {
        return fundAccountDocDAO;
    }

    @Override
    protected FundAccount newData() {
        return new FundAccount();
    }

    @Override
    protected DocDAO.Filter newFilter() {
        return new DocDAO.Filter();
    }

    @Override
    protected void setupData(FundAccount data) {
        // dummy bank account
        List<Metadata> addons = MetadataDAOTests.dummy.values().stream().filter(m->m instanceof TransferAddon).collect(Collectors.toList());
        if(addons.isEmpty()) {
            Assert.fail("TransferAddon not ready!");
        }


        TransferAddon addon = (TransferAddon)addons.get(0);
        data.setAddon(addon);

        if(TransferAddon.Mode.BANK_DEPOSIT.equals(addon.getMode())) {
            // For dummy data
            com.sme.mts.test.data.entity.FundAccount entity = null;
            if(!entities.isEmpty()){
                entity = entities.remove(RandomUtils.nextInt(0,entities.size()));
                data.setId(entity.getId());
            }
            data.getFields().put("account", "6225" + String.format("%012d", RandomUtils.nextLong(0, 1000000000000l)));
            data.getFields().put("holder", null == entity ? "迟佧仁" : holder.get(entity.getName().split("-")[2]));
            data.getFields().put("bank", null == entity ? "中国民主银行" : entity.getTitle().split("-")[0]);
            data.getFields().put("branch", RandomUtils.nextInt(0,10) < 8 ? null : "深水埗" + data.getId().split("-")[1] + "街道支行");
        } else if (TransferAddon.Mode.INTERMEDIARY_DEPOSIT.equals(addon.getMode())) {
            Assert.fail("Not support yet");
        } else {
            Assert.fail("Invalid addon mode");
        }
    }

    @Override
    protected void setupModify(FundAccount data) {
        data.getFields().put("remark", "集成测试修改");
    }

    @Test
    public void t0_dummy_data(){
        entities.addAll(FundAccountDAOTests.dummy.values());
        generateDummyData(FundAccountDAOTests.dummy.size());
    }
}
