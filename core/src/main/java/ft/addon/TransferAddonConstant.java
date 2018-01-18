package ft.addon;

import ft.spec.model.FundAccount;
import ft.spec.model.TransferTask;

import java.util.Map;

/**
 * Constants for {@link ft.spec.model.TransferAddon}
 */
public interface TransferAddonConstant {

    /**
     * Addon function
     */
    interface Functions{
        /**
         * Generate deposit slip
         */
        String GENERATE = "generate";

        /**
         * Generate signature for deposit slip
         */
        String SIGN = "sign";

        /**
         * Verify notify signature
         */
        String VERIFY = "verify";
    }

    /**
     * Parameter name
     */
    interface ParamKeys{
        /**
         * Fund account
         * @see ft.spec.model.FundAccount
         */
        String ACCOUNT = "account";

        /**
         * Transfer details
         */
        String TASK = "task";
    }

    /**
     * Parameters
     */
    final class Parameters {
        public FundAccount account;
        public TransferTask task;

        public static final Parameters instance(Map<String,Object> raw){
            if( null == raw ) return null;

            Object rawAccount = raw.get(ParamKeys.ACCOUNT);
            Object rawTask = raw.get(ParamKeys.TASK);

            if( null == rawAccount || !(rawAccount instanceof FundAccount) ||
                null == rawTask || !(rawTask instanceof TransferTask))
                return null;

            Parameters parameters = new Parameters();
            parameters.account = (FundAccount)rawAccount;
            parameters.account.setAddon(null);
            parameters.task = (TransferTask)rawTask;
            return parameters;
        }

        @Override
        public String toString() {
            return "Parameters{" +
            "account=" + account +
            ", task=" + task +
            '}';
        }
    }
}
