package ft.biz;

import ft.spec.model.Metadata;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;

/**
 * TODO
 */
public class MetadataBiz implements MetadataService {
    @Override
    public Metadata[] list(String catalog) {
        return new Metadata[0];
    }

    @Override
    public Result create(Metadata metadata) {
        return null;
    }

    @Override
    public Result delete(String catalog, String name) {
        return null;
    }
}
