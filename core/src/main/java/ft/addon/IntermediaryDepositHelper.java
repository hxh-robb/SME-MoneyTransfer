package ft.addon;

import ft.spec.model.TransferAddon;

import java.util.Map;

/**
 * Helper for intermediary deposit process
 */
public class IntermediaryDepositHelper extends PythonAddon implements TransferAddonConstant {
    /**
     * Construct transfer addon
     * @param addon
     */
    public IntermediaryDepositHelper(TransferAddon addon){
        // TODO:register python module
    }

    @Override
    public <T> T execute(Map<String, Object> parameters) {
        return execute(Functions.GENERATE, parameters);
    }

    @Override
    public <T> T execute(String func, Map<String, Object> parameters) {
        // TODO
        return null;
    }
}
