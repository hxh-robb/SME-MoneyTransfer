package ft.repo.mongodb.skeleton;

import ft.spec.model.FundAccount;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "fund_accounts", noClassnameStored = true)
public class FundAccountSkeleton extends FundAccount {
    @Id
    private String id;
}
