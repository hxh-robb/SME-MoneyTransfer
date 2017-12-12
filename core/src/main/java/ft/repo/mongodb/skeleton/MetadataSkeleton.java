package ft.repo.mongodb.skeleton;

import ft.spec.model.DepositAddon;
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
@Entity(value = "metadatas", noClassnameStored = true)
@Indexes(
    @Index(value = "metadata_unique_index",
        fields = { @Field("catalog"), @Field("name"), @Field("value") },
        options = @IndexOptions(unique = true) )
)
@Converters(MetadataSkeleton.DepositAddonEnumConverter.class)
public class MetadataSkeleton extends Metadata {
    @Id
    private String id;

    private MetadataSkeleton(String catalog) {
        super(catalog);
    }

    public static final class DepositAddonEnumConverter extends TypeConverter implements SimpleValueConverter {
        public DepositAddonEnumConverter() {
            super(DepositAddon.Mode.class, DepositAddon.Type.class);
        }

        @Override
        public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
            if (fromDBObject == null) {
                return null;
            }


            if (DepositAddon.Mode.class.equals(optionalExtraInfo.getField().getType()))
                return DepositAddon.Mode.of((Integer)fromDBObject);
            else if (DepositAddon.Type.class.equals(optionalExtraInfo.getField().getType()))
                return DepositAddon.Type.of((Integer)fromDBObject);
            else
                return null;
        }

        @Override
        public Object encode(Object value, MappedField optionalExtraInfo) {
            if (value == null) {
                return null;
            }

            if (value instanceof DepositAddon.Mode)
                return ((DepositAddon.Mode)value).getValue();
            else if (value instanceof DepositAddon.Type)
                return ((DepositAddon.Type)value).getValue();
            else
                return null;
        }
    }
}
