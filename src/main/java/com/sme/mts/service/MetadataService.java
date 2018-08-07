package com.sme.mts.service;

import com.sme.mts.data.document.TransferAddon;

import java.util.List;

/**
 * 元数据服务接口
 * @param <A> 身份认证类
 */
public interface MetadataService<A> extends Service {
    /**
     * 资金账号表单规格列表
     * @param authentication 身份认证
     * @return
     */
    List<TransferAddon> listTransferAddon(A authentication);
}
