package com.common.es;

import lombok.Data;

@Data
public class SettingName {

    String indexName;

    String typeName;

    public SettingName(String indexName, String typeName) {
        this.indexName = indexName;
        this.typeName = typeName;
    }

    public static SettingName set(String indexName,String typeName){
        return new SettingName(indexName,typeName);
    }
}
