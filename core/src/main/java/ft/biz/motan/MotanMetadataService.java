package ft.biz.motan;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import ft.biz.MetadataBiz;
import ft.repo.MetadataDAO;
import ft.spec.service.MetadataService;
import org.springframework.beans.factory.annotation.Qualifier;

@MotanService(version = "1.0") @Qualifier("FakeMetadataService")
public class MotanMetadataService extends MetadataBiz implements MetadataService {
    public MotanMetadataService(MetadataDAO dao) {
        super(dao);
    }
}
