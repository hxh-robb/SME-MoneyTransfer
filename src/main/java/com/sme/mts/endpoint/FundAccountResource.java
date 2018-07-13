package com.sme.mts.endpoint;

import com.sme.mts.extension.jaxrs.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("fund-accounts")
public class FundAccountResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值")
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
        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        return Response.status(500, "Not yet implemented").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值")
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
        // @content(json form)
        return Response.status(500, "Not yet implemented").build();
    }
}
