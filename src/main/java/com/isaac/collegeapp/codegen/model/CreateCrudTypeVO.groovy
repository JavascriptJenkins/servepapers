package com.isaac.collegeapp.codegen.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// This is specifically for embedding inside parent objects
@JsonIgnoreProperties
class CreateCrudTypeVO implements Serializable {

    @JsonProperty
    String nameOfObject // the name of new object that will be mapped to angular/entity code

    @JsonProperty
    String name_of_object // same thing but an underscored version if its a child Type object...

    @JsonProperty
    List<FieldVO> fieldObjects = new ArrayList<>()// will have the fieldName and dataType

}
