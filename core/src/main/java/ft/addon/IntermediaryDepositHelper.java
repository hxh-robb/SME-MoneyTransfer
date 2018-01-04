package ft.addon;

import ft.spec.model.DepositSlip;
import ft.spec.model.TransferAddon;

import java.util.Map;

/**
 * Helper for intermediary deposit process
 */
public class IntermediaryDepositHelper extends PythonAddon implements TransferAddonConstant {
    private final String module; // python module

    /**
     * Construct transfer addon
     * @param addon
     */
    IntermediaryDepositHelper(TransferAddon addon){
        if( null == addon )
            throw new IllegalArgumentException("TransferAddon cannot be null");

        if( null == addon.getId() || null == addon.getContent() ) {
            throw new IllegalArgumentException("TransferAdd ID and Content cannot be null");
        }

        module = "addon_" + addon.getId().replaceAll("-", "");
        registerModule(module, addon.getContent());
    }

    @Override
    public <T> T execute(Map<String, Object> parameters) {
        return execute(Functions.GENERATE, parameters);
    }

    @Override
    public <T> T execute(String func, Map<String, Object> parameters) {
        if( true ) {
            Parameters param = Parameters.instance(parameters);

            DepositSlip slip = new DepositSlip();
            slip.content = executePython(Map.class, module, Functions.SIGN, param.account.getFields()).toString();
            System.out.println(param.account.getFields());
            return (T)slip;
        }

        // TODO
        return null;
    }

    public Map<String,Object> sign(Map<String,Object> staticFields, Double amount) {
        return null;
    }
}
