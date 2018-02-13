package ft.biz.motan;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import ft.biz.FundAccountBiz;
import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.spec.service.FundAccountService;
import ft.spec.service.MetadataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.thymeleaf.TemplateEngine;

@MotanService(version = "1.0") @Qualifier("FakeFundAccountService")
public class MotanFundAccountService extends FundAccountBiz implements FundAccountService {
    /**
     * Constructor injection
     *
     * @param dao
     * @param metadataDAO
     */
    public MotanFundAccountService(
        FundAccountDAO dao,
        MetadataDAO metadataDAO,
        @Qualifier("ActualMetadataService") MetadataService metadataService,
        TemplateEngine tp
    ) {
        super(dao, metadataDAO, metadataService, tp);
    }
}
