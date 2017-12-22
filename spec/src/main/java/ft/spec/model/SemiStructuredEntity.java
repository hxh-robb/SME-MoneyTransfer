package ft.spec.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Semi-structured entity
 */
public class SemiStructuredEntity extends Entity {
//    public SemiStructuredEntity() {
//        super();
//        collectStructuredFields();
//    }

    /**
     * Force sub-class to call this constructor
     * @param init
     */
    public SemiStructuredEntity(boolean init) {
        super(init);
        collectStructuredFields();
    }

    private void collectStructuredFields(){
        try {
            BeanInfo info = Introspector.getBeanInfo(this.getClass());
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor descriptor:descriptors) {
                structuredFields.put(descriptor.getName(), descriptor);
            }
        } catch (IntrospectionException e) {
            // Ignore error
        }
    }

    /**
     * structured fields cache
     */
    private final Map<String, PropertyDescriptor> structuredFields = new ConcurrentHashMap<>();

    /**
     * dynamic fields
     */
    protected final Map<String, Object> unstructuredFields = new ConcurrentHashMap<>();

    /**
     * set value
     * @param field
     * @param value
     */
    public void set(String field, Object value) {
        PropertyDescriptor descriptor = structuredFields.get(field);
        if( null != descriptor && null != descriptor.getWriteMethod() ) {
            try {
                Method setter = descriptor.getWriteMethod();
                setter.invoke(this, value);
                return;
            } catch (Throwable throwable) {
                // Ignore error, use unstructuredFields to store the field and value
            }
        }

        if( null == value ) {
            unstructuredFields.remove(field);
            return;
        }
        unstructuredFields.put(field, value);
    }

    /**
     * set value
     * @param map
     */
    public void set(Map<String, Object> map) {
        if( null == map || map.isEmpty() ) {
            return;
        }

        for ( String field:map.keySet() ) {
            set(field, map.get(field));
        }
    }

    /**
     * get value
     * @param field
     * @return
     */
    public <T> T get(String field) {
        PropertyDescriptor descriptor = structuredFields.get(field);
        Method getter = null;
        if( null != descriptor && null != (getter = descriptor.getReadMethod()) ) {
            try {
                T value = (T)getter.invoke(this);
                if( null != value ) return value;
            } catch (Throwable throwable) {
                // Ignore error, get value from unstructuredFields
            }
        }

        try {
            return (T)unstructuredFields.get(field);
        } catch (Throwable throwable) {
            return null;
        }
    }
}
