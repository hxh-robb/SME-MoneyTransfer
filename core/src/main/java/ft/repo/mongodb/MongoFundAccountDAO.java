package ft.repo.mongodb;

import ft.repo.FundAccountDAO;
import ft.spec.model.FundAccount;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository
public class MongoFundAccountDAO extends MongoDAOHelper<FundAccount, FundAccountDAO.Filter> implements FundAccountDAO {

    @Autowired
    private Datastore mongodb;

    @Override
    public boolean create(FundAccount data) {
        mongodb.save(data);
        return true;
    }

    @Override
    public int update(Filter filter, FundAccount data) {
        Query<FundAccount> q = generateQuery(mongodb,filter);

        UpdateOperations<FundAccount> uo = mongodb.createUpdateOperations(FundAccount.class);
        fillUpdateOperators(uo,data);

        mongodb.update(q,uo);
        return -1;
    }

    @Override
    public int delete(Filter filter) {
        Query<FundAccount> q = generateQuery(mongodb,filter);
        mongodb.delete(q);
        return -1;
    }

    @Override
    public List<FundAccount> list(Filter filter) {
        // TODO
        return null;
    }

    /**
     * Generate morphia query object
     * @param mongodb MongoDB datasotre
     * @param f The filter
     * @return
     */
    private Query generateQuery(Datastore mongodb, Filter f) {
        Query<FundAccount> q = mongodb.createQuery(FundAccount.class);
        fillQuery(q, f);
        return q;
    }

}
