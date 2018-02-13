package ft.biz;

import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;
import ft.spec.model.Metadata;
import ft.spec.service.FundAccountService;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * Fund account service implementation
 */
@Service
public class FundAccountBiz extends EntityBiz<FundAccount, FundAccountDAO> implements FundAccountService {
    private MetadataDAO metadataDAO;
    private MetadataService metadataService;
    private TemplateEngine templateEngine;

    /**
     * Constructor injection
     * @param dao
     * @param metadataDAO
     */
    public FundAccountBiz(
        FundAccountDAO dao,
        MetadataDAO metadataDAO,
        MetadataService metadataService,
        TemplateEngine tp
    ) {
        super(dao); // Constructor injection
        this.metadataDAO = metadataDAO;
        this.metadataService = metadataService;
        this.templateEngine = tp;
    }

    @Override
    protected boolean invalid(FundAccount account) {
        if(super.invalid(account)) return true;
        if( null == account.getType() ) return true;

        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.catalog = Metadata.CATALOG.TRANSFER_ADDON;
        filter.value = account.getType();
        filter.de = false;
        return metadataDAO.list(filter).isEmpty();  // Cannot find match addon for this account
    }

    @Override
    protected boolean duplicated(FundAccount account) {
        if(super.duplicated(account)) return true;

        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.type = account.getType();
        filter.fields = account.getFields();
        return !dao.listDepositOptions(filter).isEmpty();
    }

    @Override
    public List<DepositOption> supportedDepositOption(String subject) {
        FundAccountDAO.Filter filter = createFilter();
        filter.de = false;
        // TODO: Set filter by subject(ACL)
        return dao.listDepositOptions(filter);
    }

    @Override
    public String jsonFormSchema(String subject) {
        Context ctx = new Context();
        ctx.setVariable("addons", metadataService.supportedTransferAddons(null));
        return templateEngine.process("fund_account_form_schema",ctx);
    }

    @Override
    public Result create(String subject, FundAccount entity) {
        // TODO : File paths manipulation

        return super.create(subject, entity);
    }

    @Override
    protected FundAccountDAO.Filter createFilter() {
        return new FundAccountDAO.Filter();
    }

    @Override
    protected FundAccount createEntity() {
        return new FundAccount();
    }
}
