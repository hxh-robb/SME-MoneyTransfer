package ft.api;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import ft.spec.model.FundAccount;
import ft.spec.model.TransferAddon;
import ft.spec.service.FundAccountService;
import ft.spec.service.MetadataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("api")
public class FundAccountController {
    @MotanReferer(version = "1.0")
    private FundAccountService fundAccountService;

    @MotanReferer(version = "1.0")
    private MetadataService metadataService;

    @GetMapping("test")
    public String list(){
//        TransferAddon addon = new TransferAddon();
//        addon.setName("Bank");
//        addon.setMode(TransferAddon.Mode.BANK_DEPOSIT);
//        addon.setType(TransferAddon.Type.JAVA);
//        addon.setSpec("TODO");
//        addon.setContent("ft.core.addon.BankDepositAddon");
//        addon.setValue("0");
//        metadataService.create("Robb",addon);
//
//        FundAccount account = new FundAccount();
//        account.getInfo().setName("蛤蟆");
//        account.setType(addon.getValue());
//        account.set("account","6888888888888888");
//        account.set("holder","江泽民");
//        account.set("bank","中国中央银行");
//        account.set("branch","中南海支行");
//        fundAccountService.create("Robb", account);
//
//        return "OK";

        return fundAccountService.list(null).toString();
    }
}
