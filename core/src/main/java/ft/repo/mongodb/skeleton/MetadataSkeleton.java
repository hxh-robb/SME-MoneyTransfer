package ft.repo.mongodb.skeleton;

import ft.spec.model.Metadata;
import org.mongodb.morphia.annotations.*;

@Entity(value = "metadatas", noClassnameStored = true)
@Indexes(
    @Index(value = "metadata_unique_index",
        fields = { @Field("catalog"), @Field("name") },
        options = @IndexOptions(unique = true) )
)
public class MetadataSkeleton extends Metadata {
    @Id
    private String id;

    private MetadataSkeleton(String catalog) {
        super(catalog);
    }
}
