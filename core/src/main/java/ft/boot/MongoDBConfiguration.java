package ft.boot;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import ft.repo.mongodb.skeleton.CloneMapper;
import ft.repo.mongodb.skeleton.FundAccountSkeleton;
import ft.repo.mongodb.skeleton.MetadataSkeleton;
import ft.spec.model.TransferAddon;
import ft.spec.model.FundAccount;
import ft.spec.model.Metadata;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for MongoDB
 * @see <a href="https://github.com/xeraa/morphia-demo/blob/master/src/main/java/net/xeraa/morphia_demo/config/MongoDB.java">Morphia settings</a>
 */
@Configuration
public class MongoDBConfiguration {


    @Value("${mongodb.address}")
    private String mongoAddr;

    @Value("${mongodb.username}")
    private String mongoUser;

    @Value("${mongodb.password}")
    private String mongoPass;

    @Bean
    public Datastore datastore(){
        Morphia morphia = new Morphia();

        // Mapping stage
        final Mapper mapper = morphia.getMapper();
        final CloneMapper helper = new CloneMapper(mapper);

        // Metadata
        helper.map(MetadataSkeleton.class, Metadata.class);
        mapper.getMappedClass(Metadata.class).update();

        // TransferAddon
        helper.map(MetadataSkeleton.class, TransferAddon.class);
        mapper.getMappedClass(TransferAddon.class).update();

        // FundAccount
        helper.map(FundAccountSkeleton.class, FundAccount.class);
        mapper.getMappedClass(FundAccount.class).update();

        // Common mapper setting
        mapper.getOptions().setStoreNulls(true);

        MongoClientOptions options = MongoClientOptions.builder()
            .minConnectionsPerHost(5)
            .build();
        MongoClient client = new MongoClient(mongoAddr, options);
        Datastore datastore = morphia.createDatastore(client, "outwit-FT");
        datastore.ensureIndexes();
        return datastore;
    }
}
