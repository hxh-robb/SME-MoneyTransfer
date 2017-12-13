package ft.repo.mongodb;

import ft.repo.MetadataDAO;
import ft.spec.model.DepositAddon;
import ft.spec.model.Metadata;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoMetadataDAO extends MongoDAOHelper<Metadata> implements MetadataDAO {

    @Autowired
    private Datastore mongodb;

    @Override
    public void create(Metadata data) {
        mongodb.save(data);
    }

    @Override
    public void update(Filter filter, Metadata data) {
        // Query<Metadata> q = mongodb.createQuery(Metadata.class);
        // fillQuery(q, filter);
        Query<Metadata> q = generateQuery(mongodb,filter);

        UpdateOperations<Metadata> uo = mongodb.createUpdateOperations(Metadata.class);
        fillUpdateOperators(uo,data);

        mongodb.update(q,uo);
    }

    @Override
    public void delete(Filter filter) {
        // Query<Metadata> q = mongodb.createQuery(Metadata.class);
        // fillQuery(q, filter);
        Query<Metadata> q = generateQuery(mongodb,filter);

        mongodb.delete(q);
    }

    @Override
    public List<Metadata> list(Filter filter) {
        // Query<Metadata> q = mongodb.createQuery(Metadata.class);
        // fillQuery(q, filter);
        Query<Metadata> q = generateQuery(mongodb,filter);

        return q.asList();
    }

    /**
     * Generate morphia query object
     * @param mongodb MongoDB datasotre
     * @param f The filter
     * @return
     */
    private Query generateQuery(Datastore mongodb, Filter f) {
        if( f instanceof DepositAddonFilter ) {
            Query<DepositAddon> q = mongodb.createQuery(DepositAddon.class);
            fillQuery(q, f);
            return q;
        } else {
            Query<Metadata> q = mongodb.createQuery(Metadata.class);
            fillQuery(q, f);
            return q;
        }
    }

    /**
     * Fill filter to morphia query object
     * @param q
     * @param f
     */
    private void fillQuery( Query q, Filter f){
        if( null != f.catalog ) q.field("catalog").equal(f.catalog);
        if( null != f.name ) q.field("name").equal(f.name);

        if( f instanceof DepositAddonFilter ) {
            q.getEntityClass();
            DepositAddonFilter daf = (DepositAddonFilter)f;
            if( null != daf.mode ) q.field("mode").equal(daf.mode);
            if( null != daf.type ) q.field("type").equal(daf.type);
        }
    }
}
