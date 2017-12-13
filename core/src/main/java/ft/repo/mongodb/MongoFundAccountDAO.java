package ft.repo.mongodb;

import ft.repo.FundAccountDAO;
import ft.spec.model.FundAccount;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoFundAccountDAO extends MongoDAOHelper<FundAccount> implements FundAccountDAO {

    @Autowired
    private Datastore mongodb;

    @Override
    public void create(FundAccount data) {
        mongodb.save(data);
    }

    @Override
    public void update(Filter filter, FundAccount data) {
        // TODO
    }

    @Override
    public void delete(Filter filter) {
        // TODO
    }

    @Override
    public List<FundAccount> list(Filter filter) {
        // TODO
        return null;
    }
}
