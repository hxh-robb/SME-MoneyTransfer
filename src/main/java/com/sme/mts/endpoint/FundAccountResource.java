package com.sme.mts.endpoint;

import com.sme.mts.data.entity.FundAccount;
import com.sme.mts.data.repository.FundAccountDAO;
import com.sme.mts.data.repository.FundAccountDocDAO;
import com.sme.mts.extension.jaxrs.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("fund-accounts")
public class FundAccountResource {

    @Autowired
    private FundAccountDAO fundAccountDAO;

    @Autowired
    private FundAccountDocDAO fundAccountDocDAO;

    @GET @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "资金账号")
    @Operation(summary = "获取平台资金账号", description = "获取平台收款及出款资金账号",
        responses = {
            @ApiResponse(responseCode = "200", description = "平台资金账号列表"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response searchFundAccounts(
        @Parameter(description = "所属平台", required = true)
        @QueryParam("platform") String platform,

        @Parameter(description ="数据样式:<br>" +
            "default = 资金账号信息<br>" +
            "option = 渠道选项信息",
            schema = @Schema(allowableValues = {"default", "option"}))
        @QueryParam("profile") String profile
    ){
        List<FundAccount> accounts = fundAccountDAO.list(null);

        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();

        // return Response.status(500, "Not yet implemented").build();
        return Response.ok().entity(accounts).build();
    }

    @POST @Consumes(MediaType.APPLICATION_JSON) @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "资金账号")
    @Operation(summary = "增加平台资金账号", description = "增加平台收款及出款资金账号",
        responses = {
            @ApiResponse(responseCode = "200", description = "增加平台资金账号成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response createFundAccount(
        @RequestBody(description = "要增加的平台资金账号", required = true,
        content = @Content(schema = @Schema(implementation = Object.class)))
        Object fundAccount // TODO - FundAccount schema
    ){
        // test
        FundAccount data = new FundAccount();
        data.setId(UUID.randomUUID().toString());
        data.setCo("Robb");
        data.setType(0);
        data.setName("Dummy Account");
        fundAccountDAO.create(data);

        com.sme.mts.data.document.FundAccount doc = new com.sme.mts.data.document.FundAccount();
        doc.setId(data.getId());
        doc.getFields().put("test", "Robb");
        fundAccountDocDAO.create(doc);

        // @content(json form)
        return Response.status(500, "Not yet implemented").build();
    }

    @DELETE @Path("{id}") @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "资金账号") @Operation(summary = "删除平台资金账号", description = "删除平台收款及出款资金账号",
        responses = {
            @ApiResponse(responseCode = "200", description = "删除平台资金账号成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response deleteFundAccount(
        @Parameter(description = "要删除的平台资金账号ID", required = true)
        @PathParam("id") String id
    ){
        // test
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.de = true;
        fundAccountDAO.delete(filter);

        // @content(json form)
        return Response.status(500, "Not yet implemented").build();
    }

    @PUT @Path("{id}") @Consumes(MediaType.APPLICATION_JSON) @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "资金账号") @Operation(summary = "修改平台资金账号", description = "修改平台收款及出款资金账号",
        responses = {
            @ApiResponse(responseCode = "200", description = "修改平台资金账号成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response modifyFundAccount(
        @Parameter(description = "要修改的平台资金账号ID", required = true)
        @PathParam("id") String id,

        @RequestBody(description = "要修改的平台资金账号", required = true,
        content = @Content(schema = @Schema(implementation = Object.class)))
        Object fundAccount // TODO - FundAccount schema
    ){
        // test
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.id = id;
        FundAccount account = new FundAccount();
        account.setDe(true);
        fundAccountDAO.update(filter, account);

        // @content(json form)
        return Response.status(500, "Not yet implemented").build();
    }
}
