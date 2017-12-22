package ft.boot;

import ft.repo.DAO;
import ft.spec.model.Entity;
import ft.spec.model.Metadata;
import org.apache.ibatis.ognl.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class MybatisConfiguration {
    /**
     * Ignore NoSuchPropertyException exception cause Mappers accept different inheritances of Entity and Filter
     */
    @PostConstruct
    public void initMybatisCustomSettings(){
        PropertyAccessor accessor = new ObjectPropertyAccessor(){
            @Override
            public Object getProperty(Map context, Object target, Object oname) throws OgnlException {
                try {
                    return super.getProperty(context, target, oname);
                } catch (NoSuchPropertyException ex) {
                    return null;
                }
            }
        };

        OgnlRuntime.setPropertyAccessor(DAO.Filter.class, accessor);
        OgnlRuntime.setPropertyAccessor(Entity.class, accessor);
    }
}
