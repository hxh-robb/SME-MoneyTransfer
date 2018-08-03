package com.sme.mts;

import com.sme.mts.data.Data;
import com.sme.mts.data.DataAccessObject;
import com.sme.mts.data.entity.Entity;
import com.sme.mts.data.entity.PlatformRelatedEntity;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class RepositoryTestcase<D extends Data,F extends DataAccessObject.Filter> extends Testcase {

    private void doInitData(Data data) {
        if( null == data ) return;
        data.setId(UUID.randomUUID().toString());
        data.setDe(false);
    }

    private void doInitData(Entity data) {
        if( null == data ) return;
        doInitData((Data)data);
        data.setCo(operator);
    }

    private void doInitData(PlatformRelatedEntity data) {
        if( null == data ) return;
        doInitData((Entity)data);
        data.setPlatform(data.getId().toCharArray()[1]+"");
    }

    protected void initData(Data data){
        if( null == data ) return;
        if(data instanceof PlatformRelatedEntity) {
            doInitData((PlatformRelatedEntity)data);
        } else if(data instanceof Entity) {
            doInitData((Entity)data);
        } else {
            doInitData(data);
        }
    }

    protected abstract Map<String, D> expected();
    protected abstract boolean isInitialized();
    protected abstract void setInitialized(boolean bool);

    protected abstract DataAccessObject<D,F> dao();
    protected abstract D newData();
    protected abstract F newFilter();
    protected abstract void setupData(D data);
    protected abstract void setupModify(D data);

    @Before
    public void init() {
        if(isInitialized()) {
            return;
        }

        dao().delete(null);
        Assert.assertTrue("Cannot clear table data",dao().list(null).isEmpty());
        setInitialized(true);
    }

    protected void doCreate(boolean addToExpected,Consumer<D> ... consumers){
        D data = newData();
        initData(data);
        setupData(data);
        if( null != consumers ) {
            for (Consumer<D> consumer : consumers) {
                if (null == consumer) continue;
                consumer.accept(data);
            }
        }
        Assert.assertTrue(dao().create(data));
        if(addToExpected)expected().put(data.getId(), data);
    }

    @Test
    public void t0_create(){
        doCreate(true);
    }

    @Test
    public void t1_list(){
        Assert.assertFalse(expected().isEmpty());

        F filter = newFilter();
        filter.id = expected().keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected().size())];
        List<D> list = dao().list(filter);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(expected().get(filter.id), list.get(0));
    }

    @Test
    public void t2_modify() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        F filter = newFilter();
        filter.id = expected().keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected().size())];
        D data = newData();
        data.setDe(null);
        if( data instanceof Entity) {
            ((Entity) data).setUo(operator);
        }
        setupModify(data);

        D expected = expected().get(filter.id);

        PropertyDescriptor[] pds = Introspector.getBeanInfo(data.getClass()).getPropertyDescriptors();
        for (PropertyDescriptor pd:pds) {
            if( null == pd.getReadMethod() || "class".equals(pd.getName()) ) continue;
            Object fv1 = pd.getReadMethod().invoke(data);
            Object fv2 = pd.getReadMethod().invoke(expected);

            if( null == fv1 && null == fv2) continue;
            Assert.assertNotEquals(pd.getName() + " didn't change",fv1, fv2);
        }

        Assert.assertEquals(1, dao().update(filter, data));


        List<D> list = dao().list(filter);
        Assert.assertEquals(1, list.size());
        setupModify(expected);
        Assert.assertEquals(expected, list.get(0));
    }

    @Test
    public void t3_delete(){
        int total = dao().list(null).size();

        F filter = newFilter();
        filter.id = expected().keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected().size())];
        List<D> list = dao().list(filter);
        Assert.assertEquals(1, list.size());

        Assert.assertEquals(1, dao().delete(filter));
        Assert.assertEquals(total - 1, dao().list(null).size());
    }

    protected void generateDummyData(int size,Consumer<D> ... consumers) {
        for (int i = 0; i < size; i++) {
            doCreate(false,consumers);
        }
    }
}
