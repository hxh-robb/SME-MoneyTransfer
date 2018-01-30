package ft.biz.motan;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import ft.biz.FundAccountBiz;
import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.spec.service.FundAccountService;

@MotanService(version = "1.0")
public class MotanFundAccountService extends FundAccountBiz implements FundAccountService {
    /**
     * Constructor injection
     *
     * @param dao
     * @param metadataDAO
     */
    public MotanFundAccountService(FundAccountDAO dao, MetadataDAO metadataDAO) {
        super(dao, metadataDAO);
    }
}
