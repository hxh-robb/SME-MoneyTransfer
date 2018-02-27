package ft.biz;

import ft.files.FileManager;
import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;
import ft.spec.model.Metadata;
import ft.spec.service.FundAccountService;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

/**
 * Fund account service implementation
 */
@Service @Qualifier("ActualFundAccountService")
public class FundAccountBiz extends EntityBiz<FundAccount, FundAccountDAO> implements FundAccountService {
    private MetadataDAO metadataDAO;
    private MetadataService metadataService;
    private TemplateEngine templateEngine;
    private FileManager fm;

    /**
     * Constructor injection
     * @param dao
     * @param metadataDAO
     */
    public FundAccountBiz(
        FundAccountDAO dao,
        MetadataDAO metadataDAO,
        @Qualifier("ActualMetadataService") MetadataService metadataService,
        TemplateEngine tp,
        FileManager fm
    ) {
        super(dao); // Constructor injection
        this.metadataDAO = metadataDAO;
        this.metadataService = metadataService;
        this.templateEngine = tp;
        this.fm = fm;
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
        filter.de = false;
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
        String iconRawPath = entity.getInfo().getIcon();
        if( null != iconRawPath ) {
            String iconWebPath = fm.copyTo(iconRawPath, "icon", true);
            if (null == iconWebPath) {
                LoggerFactory.getLogger(FundAccountBiz.class).error(String.format("Can NOT move %s raw file[%s]", "icon", iconRawPath));
                return new Result();
            }
            entity.getInfo().setIcon(iconWebPath);
        }

        Map<String, Object> fields = entity.getFields();
        for(String field : fields.keySet()) {
            Object value = fields.get(field);
            if(!(value instanceof String) || !fm.isRaw((String)value)) {
                continue;
            }

            String newPath = fm.copyTo((String)value, field, false);
            if( null == newPath ) {
                LoggerFactory.getLogger(FundAccountBiz.class).error(String.format("Can NOT move %s raw file[%s]", field, value));
                return new Result();
            }
            entity.set(field,newPath);
        }

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
