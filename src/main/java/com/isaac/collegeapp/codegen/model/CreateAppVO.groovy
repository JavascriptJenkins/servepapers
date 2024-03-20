package com.isaac.collegeapp.codegen.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
class CreateAppVO implements Serializable {

    @JsonProperty
    String appName // will become the package name

    @JsonProperty
    List<FieldVO> fieldObjects = new ArrayList<>()// will have the fieldName and dataType

}
