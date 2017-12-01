package ft.repo.mongodb.skeleton;

import org.mongodb.morphia.mapping.MappedClass;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.Mapper;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public class CloneMapper {
    private final Mapper mapper;

    public CloneMapper(final Mapper mapper) {
        this.mapper = mapper;
    }

    public void map(final Class sourceClass, final Class destClass) {
        final MappedClass destMC = mapper.getMappedClass(destClass);
        final MappedClass sourceMC = mapper.getMappedClass(sourceClass);
        //copy the class level annotations
        for (final Map.Entry<Class<? extends Annotation>, List<Annotation>> e : sourceMC.getRelevantAnnotations().entrySet()) {
            if (e.getValue() != null && !e.getValue().isEmpty()) {
                for (final Annotation ann : e.getValue()) {
                    destMC.addAnnotation(e.getKey(), ann);
                }
            }
        }
        //copy the fields.
        for (final MappedField mf : sourceMC.getPersistenceFields()) {
            final Map<Class<? extends Annotation>, Annotation> annMap = mf.getAnnotations();
            final MappedField destMF = destMC.getMappedFieldByJavaField(mf.getJavaFieldName());
            if (destMF != null && annMap != null && !annMap.isEmpty()) {
                for (final Map.Entry<Class<? extends Annotation>, Annotation> e : annMap.entrySet()) {
                    destMF.addAnnotation(e.getKey(), e.getValue());
                }
            }
        }

    }
}
