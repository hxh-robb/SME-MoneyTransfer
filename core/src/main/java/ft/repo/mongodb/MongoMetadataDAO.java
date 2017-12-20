package ft.repo.mongodb;

import com.mongodb.WriteResult;
import ft.repo.DAO;
import ft.repo.MetadataDAO;
import ft.spec.model.TransferAddon;
import ft.spec.model.Metadata;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Meta;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository
public class MongoMetadataDAO extends MongoDAOHelper<Metadata,MetadataDAO.Filter> implements MetadataDAO {

    @Autowired
    private Datastore mongodb;

    @Override
    public boolean create(Metadata data) {
        try {
            mongodb.save(data);
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }

    @Override
    public int update(Filter filter, Metadata data) {
        try {
            // Query<Metadata> q = mongodb.createQuery(Metadata.class);
            // fillQuery(q, filter);
            Query<Metadata> q = generateQuery(mongodb,filter);
            UpdateOperations<Metadata> uo = mongodb.createUpdateOperations(Metadata.class);
            fillUpdateOperators(uo,data);
            UpdateResults ur = mongodb.update(q,uo);
            return ur.getUpdatedCount();
        } catch (Throwable throwable) {
            return -1;
        }
    }

    @Override
    public int delete(Filter filter) {
        try {
            WriteResult wr = mongodb.delete(generateQuery(mongodb,filter));
            return wr.getN();
        } catch (Throwable throwable) {
            return -1;
        }
    }

    @Override
    public <T extends Metadata> List<T> list(Filter filter) {
        Query<T> q = generateQuery(mongodb,filter);
        return q.asList();
    }

    /**
     * Generate morphia query object
     * @param mongodb MongoDB datasotre
     * @param f The filter
     * @return
     */
    private Query generateQuery(Datastore mongodb, Filter f) {
        Query q;

        if( f instanceof TransferAddonFilter ) {
            q = mongodb.createQuery(TransferAddon.class);
        } else {
            q = mongodb.createQuery(Metadata.class);
        }

        fillQuery(q, f);
        return q;
    }

    /**
     * Fill filter to morphia query object
     * @param q MongoDB query object
     * @param f Filter
     * @return interrupted or not
     */
    @Override
    protected boolean fillQuery( Query q, Filter f){
        if( super.fillQuery(q,f) ) return true;

        if( null != f.catalog ) q.field("catalog").equal(f.catalog);
        if( null != f.name ) q.field("name").equal(f.name);

        if( f instanceof TransferAddonFilter ) {
            TransferAddonFilter daf = (TransferAddonFilter)f;
            if( null != daf.mode ) q.field("mode").equal(daf.mode);
            if( null != daf.type ) q.field("type").equal(daf.type);
        }
        return true;
    }
}
