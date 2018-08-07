package com.sme.mts.test.endpoint;

import com.sme.mts.test.data.entity.TransferTask;
import com.sme.mts.test.data.repository.TransferTaskDAO;
import com.sme.mts.test.extension.jaxrs.MediaType;
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
import java.util.UUID;

@Path("transfer-tasks")
public class TransferTaskResource {

    @Autowired
    private TransferTaskDAO transferTaskDAO;


    @POST @Consumes(MediaType.APPLICATION_JSON) @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "用户申请提款") @Tag(name = "转账任务")
    @Operation(summary = "增加转账任务", description = "增加转账(收款/出款)任务",
        responses = {
            @ApiResponse(responseCode = "200", description = "增加转账任务成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response createTransferTask(
        @RequestBody(description = "要增加的转账任务", required = true,
        content = @Content(schema = @Schema(implementation = Object.class)))
        Object transferTask // TODO - TransferTask schema
    ){
        // dummy code
        TransferTask data = new TransferTask();
        data.setId(UUID.randomUUID().toString());
        data.setCo("Robb");
        data.setType(0);
        data.setBeneficiary("Robb");
        data.setAmount(50.0);
        data.setStatus("New");
        data.setState("Pending");
        data.setRetry(false);
        data.setRef("dummy-ref-" + data.getId());
        data.setFundAccount("352545b7-2c4c-4d1c-9810-ed80aaebdecc");
        transferTaskDAO.create(data);

        // @content(json form)
        return Response.status(500, "Not yet implemented").build();
    }

    @GET @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "用户申请提款") @Tag(name = "转账任务")
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
        // dummy code
        return Response.ok().entity(transferTaskDAO.list(null)).build();

        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        // return Response.status(500, "Not yet implemented").build();
    }

    @PATCH @Path("{id}") @Consumes(MediaType.APPLICATION_JSON) @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "用户申请提款") @Tag(name = "转账任务")
    @Operation(summary = "更新转账任务状态",
        description = "更新转账任务流程状态(status)及结果状态(state)",
        responses = {
            @ApiResponse(responseCode = "200", description = "更新转账任务状态成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response changeTransferTaskStatus(
        @Parameter(description = "要更新状态的转账任务ID")
        @PathParam("id") String id,

        @RequestBody(description = "转账任务状态", required = true,
        content = @Content(schema = @Schema(implementation = Object.class)))
        Object transferTask // TODO - TransferTask status & state BeanParam
    ){
        // dummy code
        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.id = id;
        TransferTask data = new TransferTask();
        data.setStatus("Done");
        data.setState("Success");
        transferTaskDAO.update(filter, data);

        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        return Response.status(500, "Not yet implemented").build();
    }

    @PUT @Path("{id}") @Consumes(MediaType.APPLICATION_JSON) @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "转账任务") @Operation(summary = "修改转账任务",
        description = "修改转账任务",
        responses = {
            @ApiResponse(responseCode = "200", description = "修改转账任务成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response modifyTransferTasks(
        @Parameter(description = "要修改的转账任务ID")
        @PathParam("id") String id,

        @RequestBody(description = "转账任务", required = true,
        content = @Content(schema = @Schema(implementation = Object.class)))
        Object transferTask // TODO - TransferTask
    ){
        // dummy code
        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.id = id;
        TransferTask data = new TransferTask();
        data.setDe(true);
        transferTaskDAO.update(filter, data);

        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        return Response.status(500, "Not yet implemented").build();
    }

    @DELETE @Path("{id}") @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "转账任务") @Operation(summary = "删除转账任务",
        description = "删除转账任务",
        responses = {
            @ApiResponse(responseCode = "200", description = "删除转账任务状态成功"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response deleteTransferTasks(
        @Parameter(description = "要删除的转账任务ID")
        @PathParam("id") String id
    ){
        // dummy code
        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.id = id;
        transferTaskDAO.delete(filter);

        // @profile(default:account)
        // #Get current login user as operator
        // return Response.ok().entity("TODO").build();
        return Response.status(500, "Not yet implemented").build();
    }
}
