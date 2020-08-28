package com.common.es;

import lombok.Data;

/**
 *
 * @author guozhenhua
 * @date 2020-08-28 16:25
 */
@Data
public class SettingName {

    /**
     * index名称
     */
    String indexName;

    /**
     * type名称
     */
    String typeName;

    public SettingName(String indexName, String typeName) {
        this.indexName = indexName;
        this.typeName = typeName;
    }

    public static SettingName set(String indexName,String typeName){
        return new SettingName(indexName,typeName);
    }
}
