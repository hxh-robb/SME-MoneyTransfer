package com.sme.mts.service;

import com.sme.mts.data.document.Metadata;

import java.util.List;

/**
 * 元数据服务接口
 */
public interface MetadataService extends Service {
    /**
     * 资金账号表单规格列表
     * @return
     */
    List<Metadata> list(String catalog);
}
