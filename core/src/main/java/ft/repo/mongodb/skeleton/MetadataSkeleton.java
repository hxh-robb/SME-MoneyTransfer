package ft.repo.mongodb.skeleton;

import ft.spec.model.TransferAddon;
import ft.spec.model.Metadata;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

/**
 * @see <a href="https://github.com/mongodb/morphia/blob/76aa6ce28bcf63bfa8c1d1fe6f50fab71c259e8a/morphia/src/test/java/org/mongodb/morphia/ext/ExternalMapperExtTest.java">
 * MongoDB Morphia mapping trick
 * </a>
 */
@Entity(value = "metadata", noClassnameStored = true)
//@Indexes(
//    @Index(value = "metadata_unique_index",
//        fields = { @Field("catalog"), @Field("name"), @Field("value") },
//        options = @IndexOptions(unique = true) )
//)
@Converters(MetadataSkeleton.TransferAddonEnumConverter.class)
public class MetadataSkeleton extends Metadata {
    @Id
    private String id;

    private MetadataSkeleton(String catalog) {
        super(catalog);
    }

    public static final class TransferAddonEnumConverter extends TypeConverter implements SimpleValueConverter {
        public TransferAddonEnumConverter() {
            super(TransferAddon.Mode.class, TransferAddon.Type.class);
        }

        @Override
        public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
            if (fromDBObject == null) {
                return null;
            }


            if (TransferAddon.Mode.class.equals(optionalExtraInfo.getField().getType()))
                return TransferAddon.Mode.of((Integer)fromDBObject);
            else if (TransferAddon.Type.class.equals(optionalExtraInfo.getField().getType()))
                return TransferAddon.Type.of((Integer)fromDBObject);
            else
                return null;
        }

        @Override
        public Object encode(Object value, MappedField optionalExtraInfo) {
            if (value == null) {
                return null;
            }

            if (value instanceof TransferAddon.Mode)
                return ((TransferAddon.Mode)value).getValue();
            else if (value instanceof TransferAddon.Type)
                return ((TransferAddon.Type)value).getValue();
            else
                return null;
        }
    }
}
