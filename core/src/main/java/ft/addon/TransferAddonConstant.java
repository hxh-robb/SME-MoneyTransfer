package ft.addon;

import ft.spec.model.FundAccount;

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
         * Transfer amount
         */
        String AMOUNT = "amount";
    }

    /**
     * Parameters
     */
    final class Parameters {
        public FundAccount account;
        public Double amount;

        public static final Parameters instance(Map<String,Object> raw){
            if( null == raw ) return null;

            Object rawAccount = raw.get(ParamKeys.ACCOUNT);
            Object rawAmount = raw.get(ParamKeys.AMOUNT);

            if( null == rawAccount || !(rawAccount instanceof FundAccount) ||
                null == rawAmount || !(rawAmount instanceof Double))
                return null;

            Parameters parameters = new Parameters();
            parameters.account = (FundAccount)rawAccount;
            parameters.amount = (Double)rawAmount;
            return parameters;
        }
    }
}
