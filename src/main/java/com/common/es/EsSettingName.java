package com.common.es;

import lombok.Data;

/**
 * es的index和type
 * @author guozhenhua
 * @date 2020-08-28 16:25
 */
@Data
public class EsSettingName {

    /**
     * index名称
     */
    String indexName;

    /**
     * type名称
     */
    String typeName;

    public EsSettingName(String indexName, String typeName) {
        this.indexName = indexName;
        this.typeName = typeName;
    }

    public static EsSettingName set(String indexName,String typeName){
        return new EsSettingName(indexName,typeName);
    }
}
