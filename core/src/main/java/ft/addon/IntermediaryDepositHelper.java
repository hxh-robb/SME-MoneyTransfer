package ft.addon;

import ft.spec.model.DepositSlip;
import ft.spec.model.TransferAddon;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.Map;

/**
 * Helper for intermediary deposit process
 */
class IntermediaryDepositHelper extends PythonAddon implements TransferAddonConstant {
    private final String module; // python module
    private final TemplateEngine tp;

    /**
     * Construct transfer addon
     * @param addon
     */
    IntermediaryDepositHelper(TransferAddon addon,TemplateEngine tp){
        if( null == addon )
            throw new IllegalArgumentException("TransferAddon cannot be null");

        if( null == addon.getId() || null == addon.getContent() ) {
            throw new IllegalArgumentException("TransferAdd ID and Content cannot be null");
        }

        module = "addon_" + addon.getId().replaceAll("-", "");
        registerModule(module, addon.getContent());

        this.tp = tp;
    }

    @Override
    public <T> T execute(Map<String, Object> parameters) {
        return execute(Functions.GENERATE, parameters);
    }

    @Override
    public <T> T execute(String func, Map<String, Object> parameters) {
        if( true ) {
            Parameters param = Parameters.instance(parameters);
            String action = param.account.get("__form_action__", true);
            Map<String, Object> signedParam = executePython(Map.class, module, Functions.SIGN, param);

            DepositSlip slip = new DepositSlip();
            slip.mime = DepositSlip.MIME.HTML;
            Context ctx  = new Context();
            ctx.setVariable("action", action);
            ctx.setVariable("inputs", signedParam);
            slip.content = tp.process("deposit_slip", ctx);

            return (T)slip;
        }

        // TODO
        return null;
    }

    @Override
    public void deactive() {
        deregisterModule(this.module,true);
    }
//    public Map<String,Object> sign(Map<String,Object> staticFields, Double amount) {
//        return null;
//    }
}
