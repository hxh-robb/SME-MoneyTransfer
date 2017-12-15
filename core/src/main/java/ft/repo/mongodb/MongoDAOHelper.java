package ft.repo.mongodb;

import ft.repo.DAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public abstract class MongoDAOHelper<T,F extends DAO.Filter> {

    /**
     * TODO
     * @param uo
     * @param entity
     */
    protected void fillUpdateOperators(UpdateOperations uo, T entity) {
        try {
            PropertyDescriptor[] properties = Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd: properties) {
                if( pd.getReadMethod() == null || pd.getName().equals("class") ) continue;
                Object value = pd.getReadMethod().invoke(entity);
                if( null == value ) continue;
                uo.set(pd.getName(), value);
            }
        } catch (Exception e) {
            // Ignore updating
            e.printStackTrace();
        }
    }

    /**
     * Fill filter to morphia query object
     * @param q MongoDB query object
     * @param f Filter
     * @return interupted or not
     */
    protected boolean fillQuery(Query q, F f) {
        if( null == f ) return true;
        if( null != f.id ) q.field("id").equal(f.id);
        if( null != f.de ) q.field("de").equal(f.de);
        return false;
    }
}
