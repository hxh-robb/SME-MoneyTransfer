package com.sme.mts.test.extension.morphia;

import com.mongodb.DBObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.CustomMapper;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.cache.EntityCache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Super bad smell hack way to map entity dynamic fields
 */
public class ValueMapperWithDynamicFieldsSupport implements CustomMapper {
    private static final Map<Class<?>,Set<String>> entityFixedFields = new ConcurrentHashMap<>();

    @Override
    public void fromDBObject(Datastore datastore, DBObject dbObject, MappedField mf, Object entity, EntityCache cache, Mapper mapper) {
        if( null == mf.getField().getDeclaredAnnotation(DynamicFields.class) || !mf.isMap() ) {
            mapper.getConverters().fromDBObject(dbObject, mf, entity);
        } else { // Heavily overhead?
            Set<String> fields = entityFixedFields.get(entity.getClass());
            if( null == fields ) {
                fields = mapper.getMappedClass(entity).getPersistenceFields().stream()
                    .map(f->f.getNameToStore())
                    .filter(fn -> !fn.equals(mf.getField().getName()))
                    .collect(Collectors.toSet());
                entityFixedFields.put(entity.getClass(), fields);
            }

            final Set<String> fs = fields;
            dbObject.keySet().stream()
                .filter(f -> !fs.contains(f))
                .forEach(k ->
                    ((Map)mf.getFieldValue(entity)).put(
                        mapper.getConverters().decode(mf.getMapKeyClass(), k , mf),
                        mapper.getConverters().decode(mf.getSubClass(), dbObject.get(k),mf)
                    ));
        }
    }

    @Override
    public void toDBObject(Object entity, MappedField mf, DBObject dbObject, Map<Object, DBObject> involvedObjects, Mapper mapper) {
        try {
            if( null == mf.getField().getDeclaredAnnotation(DynamicFields.class) || !mf.isMap() ) {
                mapper.getConverters().toDBObject(entity, mf, dbObject, mapper.getOptions());
            } else {
                ((Map)mf.getFieldValue(entity)).forEach((key,val) -> dbObject.put(
                    mapper.getConverters().encode(key).toString(),
                    mapper.getConverters().encode(val))
                );
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
