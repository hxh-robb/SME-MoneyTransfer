package ft.biz;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;

/**
 * TODO
 */
public class MetadataBiz implements MetadataService {

    private MetadataDAO dao;

    @Override
    public Metadata[] list(String subject, String catalog) {
        return new Metadata[0];
    }

    @Override
    public Result create(String subject, Metadata metadata) {
        return null;
    }

    @Override
    public Result delete(String subject, String catalog, String name) {
        return null;
    }
}
