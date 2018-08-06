package com.sme.mts.data.repository;

import com.sme.mts.RepositoryTestcase;
import com.sme.mts.data.document.Addon;
import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.document.TransferAddon;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MetadataDAOTests extends RepositoryTestcase<Metadata, MetadataDAO.Filter> {
    private static boolean init = false;
    private static final Map<String, Metadata> expected = new ConcurrentHashMap<>();
    static final Map<String, Metadata> dummy = new ConcurrentHashMap<>();

    private static final Class<? extends Metadata>[] metadataTypes = new Class[MetadataDAO.subclasses.size() + 1];
    static {
        metadataTypes[0] = Metadata.class;
        System.arraycopy(
            MetadataDAO.subclasses.values().toArray(),0,
            metadataTypes, 1, metadataTypes.length - 1
        );
    }

    @Autowired
    private MetadataDAO metadataDAO;

    @Override
    protected Map<String, Metadata> expected() {
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
    protected MetadataDAO dao() {
        return metadataDAO;
    }

    @Override
    protected Metadata newData() {
        try {
            return metadataTypes[RandomUtils.nextInt(0,metadataTypes.length)].newInstance();
        } catch (Throwable th) {
            throw new RuntimeException("Cannot new metadata instance",th);
        }
    }

    @Override
    protected Metadata newDataForModify(Metadata original) {
        try {
            return original.getClass().newInstance();
        } catch (Throwable th) {
            throw new RuntimeException("Cannot new metadata instance",th);
        }
    }

    @Override
    protected MetadataDAO.Filter newFilter() {
        return new MetadataDAO.Filter();
    }

    @Override
    protected void setupData(Metadata data) {
        if( data instanceof TransferAddon ) {
            data.setName("BankAccountDepositSlipGenerator-" + data.getId().split("-")[1]);
            data.setDescription("银行账号存款单生成器 - " + data.getId().split("-")[1]);
            data.setValue(data.getId().split("-")[1]);

            ((Addon) data).setType(Addon.Type.PYTHON);
            ((Addon) data).setContent( // TODO
                "print 'TODO(Bank account deposit slip generating)'\n" +
                "pass"
            );

            ((TransferAddon) data).setMode(TransferAddon.Mode.BANK_DEPOSIT);
            ((TransferAddon) data).setSpec("TODO(JsonForms - Bank account)"); // TODO
        } else if( data instanceof Addon ) {
            data.setName("DummyAddon-" + data.getId().split("-")[1]);
            data.setDescription("For JUnit testing");
            data.setValue("dummy-addon-" + data.getId().split("-")[1]);

            ((Addon) data).setType(Addon.Type.PYTHON);
            ((Addon) data).setContent(
                "print 'hello, junit'\n" +
                "pass"
            );
        } else {
            data.setName("DummyMetadata-" + data.getId().split("-")[1]);
            data.setDescription("For JUnit testing");
            data.setValue(data.getId().split("-")[1]);
        }
    }

    @Override
    protected void setupModify(Metadata data) {
        if( data instanceof TransferAddon ) {
            data.setName("HnapayDepositSlipGenerator");
            data.setDescription("新生支付存款单生成器");
            ((Addon) data).setContent( // TODO
                "print 'TODO(Hanpay deposit slip generating)'\n" +
                "pass"
            );
            ((TransferAddon) data).setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
            ((TransferAddon) data).setSpec("TODO(JsonForms - Hnapay redirection form)"); // TODO
        } else if( data instanceof Addon ) {
            data.setDescription("JUnit test modify");
            ((Addon) data).setContent(
                "print 'goodbye, junit'\n" +
                "pass"
            );
        } else {
            data.setDescription("JUnit test modify");
            data.setValue(UUID.randomUUID().toString());
        }
    }

    @Test
    public void t0_dummy_data(){
        generateDummyData(RandomUtils.nextInt(20,100),data -> dummy.put(data.getId(), data));
    }
}
