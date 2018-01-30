package ft.spec.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
    protected final Object struct;

    /**
     * Force sub-class to call this constructor
     * @param init
     */
    public SemiStructuredEntity(boolean init, Object struct) {
        super(init); this.struct = struct;
        collectStructuredFields(null == struct ? this : struct);
    }

    private void collectStructuredFields(Object struct){
        try {
            BeanInfo info = Introspector.getBeanInfo(struct.getClass());
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor descriptor:descriptors) {
                structuredFields.put(descriptor.getName(), new Tuple(struct, descriptor));
            }

            if( struct != this ) {
                BeanInfo entityInfo = Introspector.getBeanInfo(this.getClass());
                PropertyDescriptor[] entityDescriptors = entityInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor:entityDescriptors) {
                    structuredFields.put(descriptor.getName(), new Tuple(this, descriptor));
                }
            }
        } catch (IntrospectionException e) {
            // Ignore error
        }
    }

    /**
     * 2-tuples
     */
    private static final class Tuple {
        public Tuple(Object instance, PropertyDescriptor descriptor) {
            this.instance = instance;
            this.descriptor = descriptor;
        }
        public Object instance;
        public PropertyDescriptor descriptor;
    }

    /**
     * structured fields cache
     */
    private final Map<String, Tuple> structuredFields = new ConcurrentHashMap<>();

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
        if( null == field ) {
            return;
        }
        Tuple tuple = structuredFields.get(field);
        Method setter;
        if( null != tuple && null != tuple.descriptor && null != ( setter = tuple.descriptor.getWriteMethod()) ) {
            try {
                if( ( setter.getModifiers() & Modifier.FINAL ) == 0 ) { // ignore final setter
                    setter.invoke(tuple.instance, value);
                    return;
                }
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
    public final <T> T get(String field) {
        return get(field, false);
    }

    /**
     * get value
     * @param field
     * @return
     */
    public final <T> T get(String field, boolean remove) {
        Tuple tuple = structuredFields.get(field);
        Method getter;
        if( null != tuple && null != tuple.descriptor && null != (getter = tuple.descriptor.getReadMethod()) ) {
            try {
                if( ( getter.getModifiers() & Modifier.FINAL ) == 0 ) { // ignore final getter
                    T value = (T) getter.invoke(tuple.instance);
                    if (null != value) return value;
                }
            } catch (Throwable throwable) {
                // Ignore error, get value from unstructuredFields
            }
        }

        try {
            if(remove) {
                return (T)unstructuredFields.remove(field);
            }
            return (T)unstructuredFields.get(field);
        } catch (Throwable throwable) {
            return null;
        }
    }
}
