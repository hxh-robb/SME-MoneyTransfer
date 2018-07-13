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

@Path("transfer-tasks")
public class TransferTaskResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "用户申请提款")
    @Operation(summary = "增加转账任务", description = "增加转账(收款/出款)任务",
            responses = {
                    @ApiResponse(responseCode = "200", description = "增加转账任务成功"),
                    @ApiResponse(responseCode = "400", description = "客户端入参错误"),
                    @ApiResponse(responseCode = "500", description = "服务端错误")
            }
    )
    public Response addTransferTask(
            @RequestBody(description = "要增加的转账任务", required = true,
                    content = @Content(schema = @Schema(implementation = Object.class)))
                    Object transferTask // TODO - TransferTask schema
    ){
        // @content(json form)
        return Response.status(500, "Not yet implemented").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "用户申请提款")
    @Operation(summary = "获取转账任务", description = "获取符合筛选条件的转账任务",
            responses = {
                    @ApiResponse(responseCode = "200", description = "转账任务列表"),
                    @ApiResponse(responseCode = "400", description = "客户端入参错误"),
                    @ApiResponse(responseCode = "500", description = "服务端错误")
            }
    )
    public Response searchTransferTasks(
            @Parameter(description = "所属平台", required = true)
            @QueryParam("platform") String platform,

            @Parameter(description ="任务状态:<br>" +
                    "new = 新建任务<br>" +
                    "pending = 处理中<br>" +
                    "done = 转账成功<br>",
                    schema = @Schema(allowableValues = {"new", "pending", "done"}))
            @QueryParam("status") String status
    ){
        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        return Response.status(500, "Not yet implemented").build();
    }

    @PATCH @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "用户申请提款")
    @Operation(summary = "更新转账任务状态", description = "更新转账任务流程状态(status)及结果状态(state)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "更新转账任务状态成功"),
                    @ApiResponse(responseCode = "400", description = "客户端入参错误"),
                    @ApiResponse(responseCode = "500", description = "服务端错误")
            }
    )
    public Response updateTransferTasks(
            @Parameter(description = "转账任务唯一标识")
            @PathParam("id") String id,

            @RequestBody(description = "转账任务状态", required = true,
                    content = @Content(schema = @Schema(implementation = Object.class)))
            Object transferTask // TODO - TransferTask status & state BeanParam
    ){
        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        return Response.status(500, "Not yet implemented").build();
    }
}
