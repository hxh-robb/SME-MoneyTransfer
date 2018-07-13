package com.sme.mts.endpoint;

import com.sme.mts.extension.jaxrs.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("metadata")
public class MetadataResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值")
    @Operation(summary = "获取系统元数据", description = "获取系统当前配置及支持功能等信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "符合筛选条件的元数据列表"),
                    @ApiResponse(responseCode = "400", description = "客户端入参错误"),
                    @ApiResponse(responseCode = "500", description = "服务端错误")
            }
    )
    public Response searchMetadata(
            @Parameter(description = "用于筛选指定类型的元数据:<br>" +
                    "transfer-addon = 转账插件",
                    schema = @Schema(allowableValues = {"transfer-addon"}))
            @QueryParam("type") String type
    ){
        // @type(default:all)
        // #Get current login user as operator
        return Response.status(500, "Not yet implemented").build();
    }
}
