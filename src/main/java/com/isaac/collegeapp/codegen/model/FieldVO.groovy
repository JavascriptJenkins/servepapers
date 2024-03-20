package com.isaac.collegeapp.codegen.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties
class FieldVO implements Serializable {

    String fieldName // "firstName"
    String dataType // "String"
    String jsonDataType // number

}
