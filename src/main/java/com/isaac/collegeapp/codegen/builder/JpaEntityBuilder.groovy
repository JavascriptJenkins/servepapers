package com.isaac.collegeapp.codegen.builder

import com.isaac.collegeapp.codegen.model.CreateAppVO
import com.isaac.collegeapp.codegen.model.CreateCrudTypeVO
import com.isaac.collegeapp.codegen.model.CreateCrudVO
import com.isaac.collegeapp.codegen.model.FieldVO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class JpaEntityBuilder {


    private final Logger logger = LoggerFactory.getLogger(this.getClass())


    StringBuilder buildChildJpaEntity(CreateCrudTypeVO createCrudTypeVO, StringBuilder payload, CreateAppVO createAppVO){

        boolean foundFoerignKey = false
        createCrudTypeVO.fieldObjects.each { FieldVO item ->
            if(item.fieldName.contains("id_type")){
                logger.info("FOUND A FOREIGN KEY!!!")
                foundFoerignKey == true
            }
        }

//        if(foundFoerignKey){
//            return buildEntityParentTable(createCrudVO, payload)
//            return buildEntityChildTable(createCrudVO, payload)
//        } else {
        return buildChildEntityTable(createCrudTypeVO, payload, createAppVO)
//        }

    }

    // for each fieldname, generate a piece of the jpa entity
    StringBuilder buildChildEntityTable(CreateCrudTypeVO createCrudTypeVO, StringBuilder sb, CreateAppVO createAppVO){

        // append the header and imports
        sb.append("package com.techvvs."+createAppVO.appName+".model;\n" +
                "\n" +
                "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n" +
                "import com.fasterxml.jackson.annotation.JsonProperty;\n" +
                "import java.io.Serializable;\n" +
                "import javax.persistence.*;" +
                "\n" +
                "@JsonIgnoreProperties\n" +
                "@Entity\n" +
                "@Table(name=\""+createCrudTypeVO.nameOfObject+"\")\n" +
                "public class "+upperCase(createCrudTypeVO.nameOfObject)+"VO implements Serializable {\n" +
                "\n")

        // loop thru unique new table fields
        createCrudTypeVO.fieldObjects.each { FieldVO item ->


            // This is where dataTypes are mapped.
            // TODO: implement int, Double - do a review of all datatypes currently in model
            if("Integer" == item.dataType && item.fieldName.contains("id")){
                sb = buildIdType(sb, item.fieldName)
            } else if ("String" == item.dataType){
                sb = buildStringType(sb, item.fieldName)
            } else if("Integer" == item.dataType && !item.fieldName.contains("id")){
                sb = buildIntegerType(sb, item.fieldName)
            } else if("java.util.Date" == item.dataType){
                sb = buildDateType(sb, item.fieldName)
            }
        }

        sb = buildGenericFields(sb)



        return sb
    }


    StringBuilder buildJpaEntity(CreateCrudVO createCrudVO, StringBuilder payload, CreateAppVO createAppVO){

        boolean foundFoerignKey = false
        createCrudVO.fieldObjects.each { FieldVO item ->
            if(item.fieldName.contains("id_type")){
                logger.info("FOUND A FOREIGN KEY!!!")
                foundFoerignKey == true
            }
        }

//        if(foundFoerignKey){
//            return buildEntityParentTable(createCrudVO, payload)
//            return buildEntityChildTable(createCrudVO, payload)
//        } else {
            return buildNormalEntityTable(createCrudVO, payload, createAppVO)
//        }

    }



    // for each fieldname, generate a piece of the jpa entity
    StringBuilder buildNormalEntityTable(CreateCrudVO createCrudVO, StringBuilder sb, CreateAppVO createAppVO){

        // append the header and imports
        sb.append("package com.techvvs."+createAppVO.appName+".model;\n" +
                "\n" +
                "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n" +
                "import com.fasterxml.jackson.annotation.JsonProperty;\n" +
                "import java.io.Serializable;\n" +
                "import javax.persistence.*;" +
                "\n" +
                "@JsonIgnoreProperties\n" +
                "@Entity\n" +
                "@Table(name=\""+createCrudVO.nameOfObject+"\")\n" +
                "public class "+upperCase(createCrudVO.nameOfObject)+"VO implements Serializable {\n" +
                "\n")

        // loop thru unique new table fields
        createCrudVO.fieldObjects.each { FieldVO item ->


            // This is where dataTypes are mapped.
            // TODO: implement int, Double - do a review of all datatypes currently in model
            if("Integer" == item.dataType && item.fieldName.contains("id")){
               sb = buildIdType(sb, item.fieldName)
            } else if ("String" == item.dataType){
                sb = buildStringType(sb, item.fieldName)
            } else if("Integer" == item.dataType && !item.fieldName.contains("id")){
                sb = buildIntegerType(sb, item.fieldName)
            } else if("java.util.Date" == item.dataType){
                sb = buildDateType(sb, item.fieldName)
            }
        }

        sb = buildGenericFields(sb)



        return sb
    }


    static StringBuilder buildIntegerType(StringBuilder sb, String fieldName){
        sb.append("    @JsonProperty\n" +
                "    Integer "+fieldName+";\n")
    }

    static StringBuilder buildDateType(StringBuilder sb, String fieldName){
        sb.append(" @Temporal(TemporalType.TIMESTAMP)\n" +
                "    @JsonProperty\n" +
                "    java.util.Date "+fieldName+"\n")
    }

    static StringBuilder buildIdType(StringBuilder sb, String fieldName){
        sb.append("    @Id\n" +
                "    @GeneratedValue(strategy = GenerationType.AUTO)\n" +
                "    @JsonProperty\n" +
                "    Integer "+fieldName+";\n")
    }

    static StringBuilder buildForeignKeyStub(StringBuilder sb, String fieldName){
        sb.append("    @Id\n" +
                "    @GeneratedValue(strategy = GenerationType.AUTO)\n" +
                "    @JsonProperty\n" +
                "    Integer "+fieldName+";\n")
    }

    static StringBuilder buildStringType(StringBuilder sb, String fieldName){
        sb.append("    @JsonProperty\n" +
                "    String "+fieldName+";\n")
    }


    static StringBuilder buildGenericFields(StringBuilder sb){
        sb.append("    // generic fields below\n" +
                "    @Temporal(TemporalType.TIMESTAMP)\n" +
                "    @JsonProperty\n" +
                "    java.util.Date updateTimeStamp;\n" +
                "\n" +
                "    @Temporal(TemporalType.TIMESTAMP)\n" +
                "    @JsonProperty\n" +
                "    java.util.Date createTimeStamp;\n")
        sb.append(
                "                }\n")
    }

    static String upperCase(String string){
        String cap = string.substring(0, 1).toUpperCase() + string.substring(1);
        return cap
    }

}
