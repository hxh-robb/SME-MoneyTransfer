package com.sme.mts.endpoint;

import com.sme.mts.data.document.Addon;
import com.sme.mts.data.document.Metadata;
import com.sme.mts.data.document.TransferAddon;
import com.sme.mts.extension.jaxrs.MediaType;
import com.sme.mts.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("metadata")
public class MetadataResource {
    @Autowired
    private MetadataService metadataService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "元数据")
    @Operation(summary = "获取系统元数据", description = "获取系统当前配置及支持功能等信息",
        responses = {
            @ApiResponse(responseCode = "200", description = "符合筛选条件的元数据列表",
                content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Metadata.class)))}),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response searchMetadata(
        @Parameter(
            description = "用于筛选指定类型的元数据:<br>" + "transfer-addon = 转账插件",
            schema = @Schema(allowableValues = {"TransferAddon"}))
        @QueryParam("catalog") String catalog
    ){
        // TODO:Validate authentication

        // TOxDO:Adapting request to service parameter

        // TODO:Validate parameter
        if(null == catalog){
            return Response.status(400, "Invalid catalog").build();
        }

        try {
            // Calling service business process logic
            List<Metadata> metadataList = metadataService.list(catalog);

            // Adapting service result to response
            return Response.ok().entity(metadataList).build();
        } catch (Throwable th){
            return Response.status(500, th.getMessage()).build();
        }
    }
}
