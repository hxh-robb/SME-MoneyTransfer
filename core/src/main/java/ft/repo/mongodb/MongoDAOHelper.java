package ft.repo.mongodb;

import org.mongodb.morphia.query.UpdateOperations;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public abstract class MongoDAOHelper<T> {

    protected void fillUpdateOperators(UpdateOperations uo, T entity) {
        try {
            PropertyDescriptor[] properties = Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd: properties) {
                if( pd.getReadMethod() == null || pd.getName().equals("class") ) continue;
                uo.set(pd.getName(), pd.getReadMethod().invoke(entity));
            }
        } catch (Exception e) {
            // Ignore updating
        }
    }
}
