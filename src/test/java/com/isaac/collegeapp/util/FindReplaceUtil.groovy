package com.isaac.collegeapp.util

import com.isaac.collegeapp.codegen.tasks.ExecuteCreateCrudTask
import com.isaac.collegeapp.codegen.builder.JpaEntityBuilder
import com.isaac.collegeapp.codegen.model.CreateCrudVO
import com.isaac.collegeapp.codegen.model.FieldVO
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import spock.lang.Specification

class FindReplaceUtil extends Specification {


    ThreadPoolTaskScheduler threadPoolTaskScheduler

    JpaEntityBuilder jpaEntityBuilder

    def setup(){
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler(poolSize: 5, threadNamePrefix: "testThread")
        threadPoolTaskScheduler.initialize()

        // create the dependency instances to pass into the runnable task
        jpaEntityBuilder = new JpaEntityBuilder()
    }

    def "MakeCrudCode"(){

        given:

        List<FieldVO> list = new ArrayList<>()

        list.addAll(
                new FieldVO(fieldName: "org_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "description", dataType: "String", jsonDataType: "string")
        )


        CreateCrudVO createCrudVO = new CreateCrudVO()
        createCrudVO.setNameOfObject("organization")
        createCrudVO.setFieldObjects(list)

        ExecuteCreateCrudTask executeCreateCrudTask = new ExecuteCreateCrudTask(createCrudVO: createCrudVO)

//        when:
//        threadPoolTaskScheduler.schedule(
//                new ExecuteCreateCrudTask(createCrudVO: createCrudVO),
//                new Date())
        when:
        executeCreateCrudTask.run()

        then:
        0 * _._

    }


    def "Generate Product table"(){

        given:

        // create the dependency instances to pass into the runnable task
        jpaEntityBuilder = new JpaEntityBuilder()

        List<FieldVO> list = new ArrayList<>()

        list.addAll(
                new FieldVO(fieldName: "product_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "product_type_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "description", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "barcode", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "price", dataType: "String", jsonDataType: "string")
        )


        CreateCrudVO createCrudVO = new CreateCrudVO()
        createCrudVO.setNameOfObject("product")
        createCrudVO.setFieldObjects(list)

        ExecuteCreateCrudTask executeCreateCrudTask =
                new ExecuteCreateCrudTask(createCrudVO:  createCrudVO, jpaEntityBuilder: new JpaEntityBuilder())



        when:
        executeCreateCrudTask.run()

        then:
        0 * _._

    }



    def "Generate Employee and Employee Type CRUD management pages"(){

        given:

        // create the dependency instances to pass into the runnable task
        jpaEntityBuilder = new JpaEntityBuilder()

        List<FieldVO> list = new ArrayList<>()
        List<FieldVO> childlist = new ArrayList<>()

        list.addAll(
                new FieldVO(fieldName: "employee_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "employee_type_id", dataType: BuilderConstants.CHILD_OBJECT, jsonDataType: "number")
        )


        childlist.addAll(
                new FieldVO(fieldName: "employee_type_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "description", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "title", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "hourly_rate", dataType: "String", jsonDataType: "string")
        )


        // parent
        CreateCrudVO createCrudVO = new CreateCrudVO()
        createCrudVO.setNameOfObject("employee") // must be lowercase
        createCrudVO.setFieldObjects(list)

        // child
        CreateCrudVO createCrudChildVO = new CreateCrudVO()
        createCrudChildVO.setNameOfObject("employeeType") // must be camelCase
        createCrudChildVO.setName_of_object("employee_type") // must be _ seperated
        createCrudChildVO.setChild_name_of_object("employee_type_id") // must be _ seperated
        createCrudChildVO.setFieldObjects(childlist)

        ExecuteCreateCrudTask executeCreateCrudTask =
                new ExecuteCreateCrudTask(createCrudVO:  createCrudVO, createCrudTypeVO :createCrudChildVO, jpaEntityBuilder: new JpaEntityBuilder())



        when:
        executeCreateCrudTask.run()

        then:
        0 * _._

    }


    def "Generate Location and Location Type CRUD management pages"(){

        given:

        // create the dependency instances to pass into the runnable task
        jpaEntityBuilder = new JpaEntityBuilder()

        List<FieldVO> list = new ArrayList<>()
        List<FieldVO> childlist = new ArrayList<>()

        list.addAll(
                new FieldVO(fieldName: "location_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "location_type_id", dataType: BuilderConstants.CHILD_OBJECT, jsonDataType: "number")
        )


        childlist.addAll(
                new FieldVO(fieldName: "location_type_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "description", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
        )


        // parent
        CreateCrudVO createCrudVO = new CreateCrudVO()
        createCrudVO.setNameOfObject("location") // must be lowercase
        createCrudVO.setFieldObjects(list)

        // child
        CreateCrudVO createCrudChildVO = new CreateCrudVO()
        createCrudChildVO.setNameOfObject("locationType") // must be camelCase
        createCrudChildVO.setName_of_object("location_type") // must be _ seperated
        createCrudChildVO.setChild_name_of_object("location_type_id") // must be _ seperated
        createCrudChildVO.setFieldObjects(childlist)

        ExecuteCreateCrudTask executeCreateCrudTask =
                new ExecuteCreateCrudTask(createCrudVO:  createCrudVO, createCrudTypeVO :createCrudChildVO, jpaEntityBuilder: new JpaEntityBuilder())



        when:
        executeCreateCrudTask.run()

        then:
        0 * _._

    }


    def "Generate Batch and Batch Type CRUD management pages"(){

        given:

        // create the dependency instances to pass into the runnable task
        jpaEntityBuilder = new JpaEntityBuilder()

        List<FieldVO> list = new ArrayList<>()
        List<FieldVO> childlist = new ArrayList<>()

        list.addAll(
                new FieldVO(fieldName: "batch_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "batch_type_id", dataType: BuilderConstants.CHILD_OBJECT, jsonDataType: "number")
        )


        childlist.addAll(
                new FieldVO(fieldName: "batch_type_id", dataType: "Integer", jsonDataType: "number"),
                new FieldVO(fieldName: "description", dataType: "String", jsonDataType: "string"),
                new FieldVO(fieldName: "name", dataType: "String", jsonDataType: "string"),
        )


        // parent
        CreateCrudVO createCrudVO = new CreateCrudVO()
        createCrudVO.setNameOfObject("batch") // must be lowercase
        createCrudVO.setFieldObjects(list)

        // child
        CreateCrudVO createCrudChildVO = new CreateCrudVO()
        createCrudChildVO.setNameOfObject("batchType") // must be camelCase
        createCrudChildVO.setName_of_object("batch_type") // must be _ seperated
        createCrudChildVO.setChild_name_of_object("batch_type_id") // must be _ seperated
        createCrudChildVO.setFieldObjects(childlist)

        ExecuteCreateCrudTask executeCreateCrudTask =
                new ExecuteCreateCrudTask(createCrudVO:  createCrudVO, createCrudTypeVO :createCrudChildVO, jpaEntityBuilder: new JpaEntityBuilder())



        when:
        executeCreateCrudTask.run()

        then:
        0 * _._

    }


}
