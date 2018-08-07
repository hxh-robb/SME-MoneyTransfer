package com.sme.mts.endpoint;

import com.sme.mts.data.document.TransferAddon;
import com.sme.mts.data.repository.MetadataDAO;
import com.sme.mts.extension.jaxrs.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("metadata")
public class MetadataResource {
    @Autowired
    private MetadataDAO metadataDAO;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Tag(name = "用户申请充值") @Tag(name = "元数据")
    @Operation(summary = "获取系统元数据", description = "获取系统当前配置及支持功能等信息",
        responses = {
            @ApiResponse(responseCode = "200", description = "符合筛选条件的元数据列表"),
            @ApiResponse(responseCode = "400", description = "客户端入参错误"),
            @ApiResponse(responseCode = "500", description = "服务端错误")
        }
    )
    public Response searchMetadata(
        @Parameter(
            description = "用于筛选指定类型的元数据:<br>" +
            "transfer-addon = 转账插件",
            schema = @Schema(allowableValues = {"transfer-addon"}))
        @QueryParam("type") String type
    ){
//        TransferAddon addon = new TransferAddon();
//        addon.setId(UUID.randomUUID().toString());
//        addon.setDe(false);
//        addon.setName("DummyTransferAddon");
//        addon.setValue("dummy1");
//        addon.setType(Addon.Type.PYTHON);
//        addon.setContent("pass;");
//        addon.setMode(TransferAddon.Mode.BANK_DEPOSIT);
//        addon.setSpec("Dummy json form spec");
//        metadataDAO.create(addon);
//
//        Addon dummy = new Addon();
//        dummy.setId(UUID.randomUUID().toString());
//        dummy.setDe(false);
//        dummy.setName("DummyAddon");
//        dummy.setValue("dummy2");
//        dummy.setType(Addon.Type.PYTHON);
//        dummy.setContent("pass;");
//        metadataDAO.create(dummy);
//
//        Metadata metadata = new Metadata();
//        metadata.setId(UUID.randomUUID().toString());
//        metadata.setDe(false);
//        metadata.setName("DummyMetadata");
//        metadata.setValue("dummy3");
//        metadataDAO.create(metadata);

        // @type(default:all)
        // #Get current login user as operator
        // return Response.status(500, "Not yet implemented").build();
        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.matches.put(MetadataDAO.Filter.CATALOG, TransferAddon.class.getSimpleName());
        return Response.ok().entity(metadataDAO.list(filter)).build();
    }
}
