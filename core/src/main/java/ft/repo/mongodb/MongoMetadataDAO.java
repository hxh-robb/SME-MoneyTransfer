package ft.repo.mongodb;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoMetadataDAO implements MetadataDAO {

    @Autowired
    private Datastore mongodb;

    @Override
    public void create(Metadata data) {

    }

    @Override
    public void update(Metadata data) {

    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<Metadata> list(Filter filter) {
        return null;
    }
}
