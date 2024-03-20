package com.isaac.collegeapp.codegen.tasks

import com.isaac.collegeapp.codegen.builder.JpaEntityBuilder
import com.isaac.collegeapp.codegen.model.CreateAppVO
import com.isaac.collegeapp.codegen.model.CreateCrudVO
import com.isaac.collegeapp.codegen.model.FieldVO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ExecuteCreateNewAppTask implements Runnable {


    @Autowired
    JpaEntityBuilder jpaEntityBuilder


// TODO: debug the showAdd sections to make sure they initialize the parent objects before the childs..

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    public ExecuteCreateCrudTask(CreateCrudVO createCrudVO, JpaEntityBuilder jpaEntityBuilder){
//        this.jpaEntityBuilder = jpaEntityBuilder
//        this.createCrudVO = createCrudVO
//    }

    // Have to create a bean for this so spring knows that one can be passed into the
    // constructor of this class (for some reason)
//    @Bean
//    CreateCrudVO createCrudVO(){
//        new CreateCrudVO()
//    }

    List<CreateCrudVO> createCrudVOList
    CreateAppVO createAppVO


    StringBuilder payload = new StringBuilder()

    CreateCrudVO genericFields = new CreateCrudVO() // to handle generic fields

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    // '+upperCase(createCrudVO.nameOfObject)+'
    // '+createCrudVO.nameOfObject+'
    // NOTE: createCrudVO.nameOfObject ---> this must be passed in lowercase!!!!!!!
    @Override
    void run() {
        logger.info("RUNNING ExecuteCreateNewAppTask")

                 if(createAppVO != null){

                     logger.info("createAppVO FOUND, CREATING: "+createAppVO.appName)

                     createNewAppStructure()
                     createModelFiles()
                 }



//        if(createCrudTypeVO != null && createCrudVO != null){
//            logger.info("TYPE OBJECT FOUND, CREATING: createTsParentChildCrudPages")
//            createTsParentChildCrudPages() // ie. ProductVO ---> ProductTypeVO
//            createTsParentChildTypeHtmlPages() // ie. ProductVO ---> '+upperCase(createCrudVO.nameOfObject)+'TypeVO
//        }


//
//        createHtmlFile()
//
//        createTypeScriptFile()
//
//        createTsServiceFile()
//
//        createTsDataTable()
////
//         createRestController()
////
//         createJpaInterface()
////
//         createJpaEntity()

//        logger.info("DONT FORGET TO EDIT THESE FILES IN ANGULAR: ")
//        logger.info("--crud-tables.module.ts")
//        logger.info("--app.routing.module.ts")
//
//        logger.info("PUT THE SERVICE FILE NAME IN THIS FILE: ")
//        logger.info("---app.module.ts")
//        logger.info("IN THIS ARRAY:")
//        logger.info("-------providers: [")

    }


    void createModelFiles(){

        println("CREATING THE MODEL FILES")

        for(CreateCrudVO createCrudVO :createCrudVOList){

            createCrudObject(createCrudVO)
            if(createCrudVO.crudTypeVO != null){
                createCrudChildObject(createCrudVO)
            }

        }

    }

    void createNewAppStructure(){
        println("CREATING NEW APP!!!")

        println("CREATING DIRS!!!")
//        addGenericAngularTable()

        File file = new File("./codegen")
        file.mkdir()
        file = new File("./codegen/"+createAppVO.appName)
        file.mkdir()
        file = new File("./codegen/"+createAppVO.appName+"/"+"pom.xml")
        createPomFile(file)
        file = new File("./codegen/"+createAppVO.appName+"/"+"README.md")
        createReadMeFile(file)
        file = new File("./codegen/"+createAppVO.appName+"/"+"LICENSE.txt")
        createLicenseFile(file)
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"logs/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"data/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"scripts/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/test/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName)
        file.mkdir()//

        // make package dirs
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/constants")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/model")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/jparepo")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/util")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/runlistener")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/viewcontroller")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/service")
        file.mkdir()//

        // Make main App class
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/"+createAppVO.appName.toUpperCase()+"App.java")
        createMainClassFile(file)

        // make resources dir
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/resources")
        file.mkdir()//

        // put files in resources dir
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/resources/images")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/resources/static")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/resources/templates")
        file.mkdir()//
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/resources/application.properties")
        createAppPropertiesFile(file)
        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/resources/data.sql")
        createDataSQLFile(file)

//        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/"+createAppVO.appName)
//        file.mkdir()//
//        file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/"+createAppVO.appName)
//        file.mkdir()//

        // File file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/model/sample.java")
//        file.mkdir()
//        file = new File("./codegen/"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject+".component.html")
//        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder

    }

    void createRunlistenerFile(File file){
        println("CREATING createRunlistenerFile FILE!!!")
        writeCodeToRunListenerFile()
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createConstantsFile(File file){
        println("CREATING createConstantsFile FILE!!!")
        writeCodeToConstantsFile()
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder

    }

    void createMainClassFile(File file){
        hydrateMainClassFile()
        println("CREATING Main class java FILE!!!")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createAppPropertiesFile(File file){
        println("CREATING createAppPropertiesFile FILE!!!")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createDataSQLFile(File file){
        println("CREATING createDataSQLFile FILE!!!")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createPomFile(File file){
        println("CREATING createPomFile FILE!!!")
        writeCodeToPomFile()
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder

    }
    void createReadMeFile(File file){
        println("CREATING createReadMeFile FILE!!!")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }
    void createLicenseFile(File file){
        println("CREATING createLicenseFile FILE!!!")
        writeContentToLicenseFile()
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createCrudObject(CreateCrudVO createCrudVO){
        File file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/model/"+createCrudVO.nameOfObject+"VO.java")
        println("CREATING createCrudObject FILE!!!")
        writeContentToCrudFile(createCrudVO)
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createCrudChildObject(CreateCrudVO createCrudVO){
        File file = new File("./codegen/"+createAppVO.appName+"/"+"src/main/java/com/techvvs/"+createAppVO.appName+"/model/"+createCrudVO.crudTypeVO.nameOfObject+"VO.java")
        println("CREATING createCrudChildObject FILE!!!")
        writeContentToChildCrudFile(createCrudVO)
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }



    void createHtmlFile(){
        println("CREATING HTML FILE!!!")
        addGenericAngularTable()
        File file = new File("./codegen/"+createCrudVO.nameOfObject+"/")
        file.mkdir()
        file = new File("./codegen/"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject+".component.html")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createTypeScriptFile(){
        buildTypeScriptFile()
        println("CREATING .TS FILE!!!")
        File file = new File("./codegen/"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject +".component.ts")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createTsServiceFile(){
        buildTypeScriptServiceFile()
        println("CREATING SERVICE .TS FILE!!!")
        File file = new File("./codegen/service/")
        file.mkdir()
        file = new File("./codegen/service"+"/"+createCrudVO.nameOfObject +".service.ts")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createTsDataTable(){
        buildTsDataTable()
        println("CREATING DATA-TABLE .TS FILE!!!")
        File file = new File("./codegen/"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject +"-data-table"+".ts")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder

    }

    void createRestController(){
        buildRestController()
        println("CREATING REST CONTROLLER .GROOVY FILE!!!")
        File file = new File("./codegen/"+upperCase(createCrudVO.nameOfObject)+"Controller.groovy")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createJpaInterface(){
        buildJpaInterface()
        println("CREATING JPA INTERFACE .GROOVY FILE!!!")
        File file = new File("./codegen/"+upperCase(createCrudVO.nameOfObject)+"Repository.java")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }

    void createJpaEntity(){
        buildObjectJpaEntity()
        println("CREATING JPA ENTITY .GROOVY FILE!!!")
        File file = new File("./codegen/"+upperCase(createCrudVO.nameOfObject)+"VO.groovy")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }


    // Step 1) re-write the product/product type page to use generics/clean code
    // Step 2) paste it in here and account for data types


    // Assumptions: we will serve as much data as frontend wants as long as its not sensitive.
    //          cont.    We will then filter the data on the frontend, even if we could technically filter with hibernate.
    // Assumptions: A service ts file will not be created for Type entities.  Will be stored in parent service file
    // NOTE: must support seperate data types
    // Build a typescript page that will automatically bind to /{$this_entity}/getAll
    // and provide full CRUD operations on a Parent object and it's child type.


    void writeContentToCrudFile(CreateCrudVO createCrudVO){
        jpaEntityBuilder.buildJpaEntity(createCrudVO, payload, createAppVO)
    }

    void writeContentToChildCrudFile(CreateCrudVO createCrudVO){
        jpaEntityBuilder.buildJpaEntity(createCrudVO, payload, createAppVO)
    }


    void createTsParentChildTypeHtmlPages(){
        println("CREATING PARENT CHILD .HTML FILES!!!")

        buildParentTypeHTMLPage()
        File file = new File("./codegen/"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject +".component.html")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder


        buildChildTypeHTMLPage()
        File childFile = new File("./codegen/"+createCrudTypeVO.nameOfObject+"/"+createCrudTypeVO.nameOfObject +".component.html")
        childFile.write payload.toString()
        payload.setLength(0) // clear the stringbuilder



    }



    void createTsParentChildCrudPages(){
        buildTsParentCrudPage()
        println("CREATING PARENT CHILD .TS FILES!!!")
        File file = new File("./codegen/"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject +".component.ts")
        file.write payload.toString()
        payload.setLength(0) // clear the stringbuilder


        buildTsChildTypeCrudPage()
        File childFile = new File("./codegen/"+createCrudTypeVO.nameOfObject+"/"+createCrudTypeVO.nameOfObject +".component.ts")
        childFile.write payload.toString()
        payload.setLength(0) // clear the stringbuilder
    }


    void writeCodeToRunListenerFile(){
        payload.append("package com.techvvs."+createAppVO.appName+".runlistener;\n" +
                "\n" +
                "import com.techvvs."+createAppVO.appName+".util.SimpleCache;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.boot.context.event.ApplicationReadyEvent;\n" +
                "import org.springframework.context.ApplicationListener;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "@Component\n" +
                "public class GlobalRunListener implements ApplicationListener<ApplicationReadyEvent> {\n" +
                "\n" +
                "    @Autowired\n" +
                "    SimpleCache simpleCache;\n" +
                "\n" +
                "    @Override\n" +
                "    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {\n" +
                "        System.out.println(\"----- TechVVS Application has started ----\");\n" +
                "        System.out.println(\"------- TechVVS Custom Cache Init ------\");\n" +
                "\n" +
                "        simpleCache.refreshCache();\n" +
                "    }\n" +
                "\n" +
                "}\n")
    }

    void writeCodeToConstantsFile(){

        payload.append("package com.techvvs."+createAppVO.appName+".constants;\n" +
                "\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "@Component\n" +
                "public class StaticRoles {\n" +
                "\n" +
                "\n" +
                "    protected String WRITE = \"WRITE,\";\n" +
                "    protected String READ = \"READ,\";\n" +
                "\n" +
                "    public String getWRITE() {\n" +
                "        return WRITE;\n" +
                "    }\n" +
                "\n" +
                "    public String getREAD() {\n" +
                "        return READ;\n" +
                "    }\n" +
                "\n" +
                "    public String getDELETE() {\n" +
                "        return DELETE;\n" +
                "    }\n" +
                "\n" +
                "    public String getVIP() {\n" +
                "        return VIP;\n" +
                "    }\n" +
                "\n" +
                "    public String getADMIN() {\n" +
                "        return ADMIN;\n" +
                "    }\n" +
                "\n" +
                "    protected String DELETE = \"DELETE,\";\n" +
                "\n" +
                "    protected String VIP = \"VIP,\";\n" +
                "\n" +
                "    protected String ADMIN = \"ADMIN,\";\n" +
                "\n" +
                "\n" +
                "}\n")
    }

    String hydrateMainClassFile(){

        payload.append('package com.techvvs.'+createAppVO.appName+';\n' +
                '\n' +
                'import org.springframework.boot.SpringApplication;\n' +
                'import org.springframework.boot.autoconfigure.SpringBootApplication;\n' +
                '\n' +
                '\n' +
                '@SpringBootApplication\n' +
                'public class '+createAppVO.appName.toUpperCase()+'Application {\n' +
                '\n' +
                '\tpublic static void main(String[] args) {\n' +
                '\t\tSpringApplication.run('+createAppVO.appName.toUpperCase()+'Application.class, args);\n' +
                '\t}\n' +
                '\n' +
                '}\n')
        return payload
    }


    void writeContentToLicenseFile(){

        payload.append("Proprietary License\n" +
                "\n" +
                "Copyright (c) 2024 techvvs\n" +
                "\n" +
                "This software is property of techvvs\n")

    }

    void writeCodeToPomFile(){

        payload.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "\t<modelVersion>4.0.0</modelVersion>\n" +
                "\t<parent>\n" +
                "\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t<artifactId>spring-boot-starter-parent</artifactId>\n" +
                "\t\t<version>2.5.4</version>\n" +
                "\t\t<relativePath/> <!-- lookup parent from repository -->\n" +
                "\t</parent>\n" +
                "\t<packaging>jar</packaging>\n" +
                "\t<groupId>com.techvvs</groupId>\n" +
                "\t<artifactId>"+createAppVO.appName+"</artifactId>\n" +
                "\t<version>0.0.1-SNAPSHOT</version>\n" +
                "\t<name>"+createAppVO.appName+"</name>\n" +
                "\t<description>Track Student enrollment </description>\n" +
                "\t<properties>\n" +
                "\t\t<java.version>11</java.version>\n" +
                "\t\t<maven.compiler.source>11</maven.compiler.source>\n" +
                "\t\t<maven.compiler.target>11</maven.compiler.target>\n" +
                "\t</properties>\n" +
                "\t<dependencies>\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\t\t <!-- this dependency is needed to bring in sporing boot embedded webs server   -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-web</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- this is needed to connect to mysql database-->\n" +
                "<!--\t\t<dependency>-->\n" +
                "<!--\t\t\t<groupId>mysql</groupId>-->\n" +
                "<!--\t\t\t<artifactId>mysql-connector-java</artifactId>-->\n" +
                "<!--\t\t\t<scope>runtime</scope>-->\n" +
                "<!--\t\t\t<version>8.0.13</version>-->\n" +
                "<!--\t\t</dependency>-->\n" +
                "\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-test</artifactId>\n" +
                "\t\t\t<scope>test</scope>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- provides integration with tyhmeleaf -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.thymeleaf.extras</groupId>\n" +
                "\t\t\t<artifactId>thymeleaf-extras-springsecurity5</artifactId>\n" +
                "\t\t\t<version>3.1.0.RC1</version>\n" +
                "\t\t</dependency>\n" +
                "\t\t\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-thymeleaf</artifactId>\n" +
                "\t\t\t<version>2.5.4</version>\n" +
                "\t\t</dependency>\n" +
                "\t\t\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<!-- JPA Data (Repositories, Entities, Hibernate, etc..) -->\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-data-jpa</artifactId>\n" +
                "\t\t\t<version>2.4.0</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<!-- JSON Web Token Support -->\n" +
                "\t\t\t<groupId>io.jsonwebtoken</groupId>\n" +
                "\t\t\t<artifactId>jjwt</artifactId>\n" +
                "\t\t\t<version>0.7.0</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<!-- Starter for using Spring Security -->\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-security</artifactId>\n" +
                "\t\t\t<version>2.7.5</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.h2database</groupId>\n" +
                "\t\t\t<artifactId>h2</artifactId>\n" +
                "\t\t\t<scope>runtime</scope>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- to support pagination -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.data</groupId>\n" +
                "\t\t\t<artifactId>spring-data-commons</artifactId>\n" +
                "\t\t\t<version>2.3.2.RELEASE</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310 -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.fasterxml.jackson.datatype</groupId>\n" +
                "\t\t\t<artifactId>jackson-datatype-jsr310</artifactId>\n" +
                "\t\t\t<version>2.14.1</version>\n" +
                "\t\t</dependency>\n" +
                "\t\t\n" +
                "\t\t<!-- This is needed for using twilio and sending out sms messages -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.twilio.sdk</groupId>\n" +
                "\t\t\t<artifactId>twilio</artifactId>\n" +
                "\t\t\t<version>8.11.0</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- This is needed for using sendgrid and sending out emails -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.sendgrid</groupId>\n" +
                "\t\t\t<artifactId>sendgrid-java</artifactId>\n" +
                "\t\t\t<version>4.10.1</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- https://mvnrepository.com/artifact/javax.mail/javax.mail-api -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>javax.mail</groupId>\n" +
                "\t\t\t<artifactId>javax.mail-api</artifactId>\n" +
                "\t\t\t<version>1.6.2</version>\n" +
                "\t\t</dependency>\n" +
                "\t\t\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.sun.mail</groupId>\n" +
                "\t\t\t<artifactId>javax.mail</artifactId>\n" +
                "\t\t\t<version>1.6.2</version>\n" +
                "\t\t</dependency>\n" +
                "\t\t\n" +
                "\t\t<!-- Java texting api -->\n" +
                "\t\t<!-- https://mvnrepository.com/artifact/com.textmagic.sdk/textmagic-java-sdk -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.textmagic.sdk</groupId>\n" +
                "\t\t\t<artifactId>textmagic-java-sdk</artifactId>\n" +
                "\t\t\t<version>2.0.2456</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- https://mvnrepository.com/artifact/commons-io/commons-io -->\n" +
                "\t\t<!-- need this for downloading files - specifically writing file to response -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>commons-io</groupId>\n" +
                "\t\t\t<artifactId>commons-io</artifactId>\n" +
                "\t\t\t<version>2.11.0</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<!-- This reloads thymeleaf automatically -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-devtools</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\t\t\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.google.guava</groupId>\n" +
                "\t\t\t<artifactId>guava</artifactId>\n" +
                "\t\t\t<version>31.1-jre</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>io.github.floverfelt</groupId>\n" +
                "\t\t\t<artifactId>find-and-replace-maven-plugin</artifactId>\n" +
                "\t\t\t<version>1.1.0</version>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\n" +
                "\t\t<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools -->\n" +
                "<!--\t\t<dependency>-->\n" +
                "<!--\t\t\t<groupId>org.springframework.boot</groupId>-->\n" +
                "<!--\t\t\t<artifactId>spring-boot-devtools</artifactId>-->\n" +
                "<!--\t\t\t<version>2.5.5</version>-->\n" +
                "<!--\t\t</dependency>-->\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.codehaus.groovy</groupId>\n" +
                "\t\t\t<artifactId>groovy-all</artifactId>\n" +
                "\t\t\t<version>3.0.15</version>\n" +
                "\t\t\t<type>pom</type>\n" +
                "\t\t</dependency>\n" +
                "\t\t<!--\t\t<dependency>-->\n" +
                "\t\t<!--\t\t\t<groupId>org.codehaus.groovy</groupId>-->\n" +
                "\t\t<!--\t\t\t<artifactId>groovy-backports-compat23</artifactId>-->\n" +
                "\t\t<!--\t\t\t<version>2.4.5</version>-->\n" +
                "\t\t<!--\t\t</dependency>-->\n" +
                "\t\t<!-- https://mvnrepository.com/artifact/org.spockframework/spock-core -->\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.spockframework</groupId>\n" +
                "\t\t\t<artifactId>spock-core</artifactId>\n" +
                "\t\t\t<version>2.4-M1-groovy-4.0</version>\n" +
                "\t\t\t<scope>test</scope>\n" +
                "\t\t</dependency>\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.codehaus.groovy</groupId>\n" +
                "\t\t\t<artifactId>groovy-eclipse-batch</artifactId>\n" +
                "\t\t\t<version>3.0.17-02</version>\n" +
                "\t\t</dependency>\n" +
                "\t</dependencies>\n" +
                "\t\n" +
                "\t<build>\n" +
                "\t\t<plugins>\n" +
                "\t\t\t<plugin>\n" +
                "\t\t\t<artifactId>maven-compiler-plugin</artifactId>\n" +
                "\t\t\t<version>3.11.0</version><!-- 3.6.2 is the minimum -->\n" +
                "\t\t\t<configuration>\n" +
                "\t\t\t\t<compilerId>groovy-eclipse-compiler</compilerId>\n" +
                "\t\t\t</configuration>\n" +
                "\t\t\t\t<dependencies>\n" +
                "\t\t\t\t\t<dependency>\n" +
                "\t\t\t\t\t\t<groupId>org.codehaus.groovy</groupId>\n" +
                "\t\t\t\t\t\t<artifactId>groovy-eclipse-compiler</artifactId>\n" +
                "\t\t\t\t\t\t<version>3.9.0</version>\n" +
                "\t\t\t\t\t</dependency>\n" +
                "\t\t\t\t</dependencies>\n" +
                "\t\t\t</plugin>\n" +
                "\t\t\t<plugin>\n" +
                "\t\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t\t<artifactId>spring-boot-maven-plugin</artifactId>\n" +
                "\t\t\t</plugin>\n" +
                "\t\t\t<!--\t\t\tThis will clean the database -->\n" +
                "\t\t\t<plugin>\n" +
                "\t\t\t\t<artifactId>maven-clean-plugin</artifactId>\n" +
                "\t\t\t\t<version>3.1.0</version>\n" +
                "\t\t\t\t<configuration>\n" +
                "\t\t\t\t\t<filesets>\n" +
                "\t\t\t\t\t\t<fileset>\n" +
                "\t\t\t\t\t\t\t<directory>data</directory>\n" +
                "\t\t\t\t\t\t\t<includes>\n" +
                "\t\t\t\t\t\t\t\t<include>**.mv.db</include>\n" +
                "\t\t\t\t\t\t\t</includes>\n" +
                "<!--\t\t\t\t\t\t\t<excludes>-->\n" +
                "<!--\t\t\t\t\t\t\t\t<exclude>**/important.log</exclude>-->\n" +
                "<!--\t\t\t\t\t\t\t\t<exclude>**/another-important.log</exclude>-->\n" +
                "<!--\t\t\t\t\t\t\t</excludes>-->\n" +
                "\t\t\t\t\t\t\t<followSymlinks>false</followSymlinks>\n" +
                "\t\t\t\t\t\t</fileset>\n" +
                "\t\t\t\t\t</filesets>\n" +
                "\t\t\t\t</configuration>\n" +
                "\t\t\t</plugin>\n" +
                "\n" +
                "\t\t\t<!-- if you run this it will replace all the uri references to local  -->\n" +
                "\t\t\t<plugin>\n" +
                "\t\t\t\t<groupId>io.github.floverfelt</groupId>\n" +
                "\t\t\t\t<artifactId>find-and-replace-maven-plugin</artifactId>\n" +
                "\t\t\t\t<executions>\n" +
                "\t\t\t\t\t<execution>\n" +
                "\t\t\t\t\t\t<id>exec</id>\n" +
                "\t\t\t\t\t\t<phase>package</phase>\n" +
                "\t\t\t\t\t\t<goals>\n" +
                "\t\t\t\t\t\t\t<goal>find-and-replace</goal>\n" +
                "\t\t\t\t\t\t</goals>\n" +
                "\t\t\t\t\t\t<configuration>\n" +
                "\t\t\t\t\t\t\t<!-- Possible replacementType values: file-contents, filenames, directory-names. To run for multiple types, pass the values as a CSV list. -->\n" +
                "\t\t\t\t\t\t\t<replacementType>file-contents</replacementType>\n" +
                "\t\t\t\t\t\t\t<baseDir>src/</baseDir>\n" +
                "\t\t\t\t\t\t\t<findRegex>https://"+createAppVO.appName+".techvvs.io</findRegex>\n" +
                "\t\t\t\t\t\t\t<replaceValue>http://localhost:8080</replaceValue>\n" +
                "\t\t\t\t\t\t\t<recursive>true</recursive>\n" +
                "\t\t\t\t\t\t</configuration>\n" +
                "\t\t\t\t\t</execution>\n" +
                "\t\t\t\t</executions>\n" +
                "\t\t\t</plugin>\n" +
                "\t\t\t\n" +
                "\t\t\t<!-- replace all hostname files when going to do prod build -->\n" +
                "<!--\t\t\t<plugin>-->\n" +
                "<!--\t\t\t\t<groupId>com.google.code.maven-replacer-plugin</groupId>-->\n" +
                "<!--\t\t\t\t<artifactId>replacer</artifactId>-->\n" +
                "<!--\t\t\t\t<version>1.5.3</version>-->\n" +
                "<!--\t\t\t\t<executions>-->\n" +
                "<!--\t\t\t\t\t<execution>-->\n" +
                "<!--\t\t\t\t\t\t<phase>generate-sources</phase>-->\n" +
                "<!--\t\t\t\t\t\t<goals>-->\n" +
                "<!--\t\t\t\t\t\t\t<goal>replace</goal>-->\n" +
                "<!--\t\t\t\t\t\t</goals>-->\n" +
                "<!--\t\t\t\t\t</execution>-->\n" +
                "<!--\t\t\t\t</executions>-->\n" +
                "<!--\t\t\t\t<configuration>-->\n" +
                "<!--\t\t\t\t\t<includes>-->\n" +
                "<!--\t\t\t\t\t\t<include>src/main/java/com/techvvs/"+createAppVO.appName+"/*</include>-->\n" +
                "<!--\t\t\t\t\t</includes>-->\n" +
                "<!--\t\t\t\t\t<replacements>-->\n" +
                "<!--\t\t\t\t\t\t<replacement>-->\n" +
                "<!--\t\t\t\t\t\t\t<token>@https://"+createAppVO.appName+".techvvs.io@</token>-->\n" +
                "<!--\t\t\t\t\t\t\t<value>https://"+createAppVO.appName+".techvvs.io</value>-->\n" +
                "<!--\t\t\t\t\t\t</replacement>-->\n" +
                "<!--\t\t\t\t\t</replacements>-->\n" +
                "<!--\t\t\t\t</configuration>-->\n" +
                "<!--\t\t\t</plugin>-->\n" +
                "\t\t</plugins>\n" +
                "\t</build>\n" +
                "\n" +
                "</project>\n")

    }

    String buildDisplayedColumns(){
        StringBuilder sb = new StringBuilder()
        sb.append('    displayedColumns = [\'')
        createCrudVO.fieldObjects.each { FieldVO item ->
            sb.append(item.fieldName.contains('id')?" 'ID', ": ("'"+item.fieldName+"',")) // if contains id, return ID, otherwise name it the fieldName
        }
        sb.append('\'];\n')
        //sb.append('    displayedColumns = [\'ID\', \'name\', \'description\', \'price\'];\n')
        return sb.toString()
    }

    String buildDisplayedColumnsForType(){
        StringBuilder sb = new StringBuilder()
        sb.append('    displayedColumns = [\'')
        createCrudTypeVO.fieldObjects.each { FieldVO item ->
            sb.append(item.fieldName.contains('id')?" 'ID', ":("'"+item.fieldName+"',")) // if contains id, return ID, otherwise name it the fieldName
        }
        sb.append('\'];\n')
        //sb.append('    displayedColumns = [\'ID\', \'name\', \'description\', \'price\'];\n')
        return sb.toString()
    }

    void buildTsDataTable(){
        payload.append("export let settings = {\n" +
                "\n" +
                "  // generate these columns with codegen\n" +
                "  columns: {\n" +
                buildTsDataTableSettings() +
                "  },\n" +
                "  edit: {\n" +
                "    editButtonContent: '<i class=\"ti-pencil text-info m-r-10\"></i>',\n" +
                "    saveButtonContent: '<i class=\"ti-save text-success m-r-10\"></i>',\n" +
                "    cancelButtonContent: '<i class=\"ti-close text-danger\"></i>'\n" +
                "  },\n" +
                "  delete: {\n" +
                "    deleteButtonContent: '<i class=\"ti-trash text-danger m-r-10\"></i>',\n" +
                "    saveButtonContent: '<i class=\"ti-save text-success m-r-10\"></i>',\n" +
                "    cancelButtonContent: '<i class=\"ti-close text-danger\"></i>'\n" +
                "  }\n" +
                "};")
    }

    String buildTsDataTableSettings(){
        StringBuilder sb = new StringBuilder()
        createCrudVO.fieldObjects.each { FieldVO item ->
            sb.append("  "+item.fieldName+": "+"{\n") // start
            if(item.fieldName.contains("id") || item.fieldName.contains("ID")){
                sb.append("      title: 'ID',\n")
                sb.append("      filter: false\n")
            } else {
                sb.append("      title: '"+upperCase(item.fieldName)+"',\n")
                sb.append("      filter: true\n")
            }
            sb.append("    },\n") // end
        }
        return sb.toString()
    }

















    void buildJpaInterface(){

        payload.append("package genre.base.auth.repository\n" +
                "\n" +
                "import genre.base.auth.model."+upperCase(createCrudVO.nameOfObject)+"VO;\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "import java.util.List;\n"+
                "\n" +
                "import jakarta.transaction.Transactional\n" +
                "\n" +
                "public interface "+upperCase(createCrudVO.nameOfObject)+"Repository extends JpaRepository<"+upperCase(createCrudVO.nameOfObject)+"VO, Integer> {\n" +
                "\n" +
                "    @Transactional\n" +
                "    void deleteByName(String name);\n" +
                "\n" +
                "    List<"+upperCase(createCrudVO.nameOfObject)+"VO> findAll();\n" +
                "\n" +
                "\n" +
                "}")
    }


    void buildRestController(){
        payload.append("package genre.base.auth.controller\n" +
                "\n" +
                "import genre.base.auth.model."+upperCase(createCrudVO.nameOfObject)+"VO\n" +
                "import genre.base.auth.repository."+upperCase(createCrudVO.nameOfObject)+"Repository\n" +
                "import com.fasterxml.jackson.databind.JsonNode\n" +
                "import com.fasterxml.jackson.databind.ObjectMapper\n" +
                "import io.swagger.annotations.Api\n" +
                "import io.swagger.annotations.ApiOperation\n" +
                "import org.slf4j.Logger\n" +
                "import org.slf4j.LoggerFactory\n" +
                "import org.springframework.beans.factory.annotation.Autowired\n" +
                "import org.springframework.web.bind.annotation.GetMapping\n" +
                "import org.springframework.web.bind.annotation.PostMapping\n" +
                "import org.springframework.web.bind.annotation.RequestBody\n" +
                "import org.springframework.web.bind.annotation.RequestMapping\n" +
                "import org.springframework.web.bind.annotation.RestController\n" +
                "\n" +
                "@RestController\n" +
                "@RequestMapping(\"/"+createCrudVO.nameOfObject+"\")\n" +
                "@Api(tags = \""+createCrudVO.nameOfObject+"\")\n" +
                "class "+upperCase(createCrudVO.nameOfObject)+"Controller {\n" +
                "\n" +
                "    @Autowired\n" +
                "    "+upperCase(createCrudVO.nameOfObject)+"Repository "+createCrudVO.nameOfObject+"Repository\n" +
                "\n" +
                "    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();\n" +
                "\n" +
                "    private final Logger logger = LoggerFactory.getLogger(this.getClass())\n" +
                "\n" +
                "\n" +
                "    @GetMapping(\"/getAll\")\n" +
                "    String getAll() {\n" +
                "\n" +
                "        logger.info(\""+upperCase(createCrudVO.nameOfObject)+"Controller.getAll HIT!!!\")\n" +
                "        String payload = executeGetAll()\n" +
                "        return payload\n" +
                "    }\n" +
                "\n" +
                "    String executeGetAll(){\n" +
                "        return mapper.writeValueAsString("+createCrudVO.nameOfObject+"Repository.findAll())\n" +
                "    }\n" +
                "\n" +
                "    @PostMapping(\"/edit\")\n" +
                "    String edit(@RequestBody String json) {\n" +
                "\n" +
                "        logger.info(\""+upperCase(createCrudVO.nameOfObject)+"Controller.edit HIT!!!\")\n" +
                "\n" +
                "        logger.info(\"json: \",json.toString())\n" +
                "\n" +
                "        JsonNode root = mapper.readTree(json);\n" +
                "        "+upperCase(createCrudVO.nameOfObject)+"VO "+createCrudVO.nameOfObject+"VO = mapper.treeToValue(root, "+upperCase(createCrudVO.nameOfObject)+"VO.class)\n" +
                "\n" +
                "        String payload = executeEdit("+createCrudVO.nameOfObject+"VO)\n" +
                "\n" +
                "        return payload\n" +
                "    }\n" +
                "\n" +
                "    String executeEdit("+upperCase(createCrudVO.nameOfObject)+"VO "+createCrudVO.nameOfObject+"VO){\n" +
                "        return mapper.writeValueAsString("+createCrudVO.nameOfObject+"Repository.save("+createCrudVO.nameOfObject+"VO))\n" +
                "    }\n" +
                "\n" +
                "    @PostMapping(\"/delete\")\n" +
                "    String delete(@RequestBody String json) {\n" +
                "\n" +
                "        logger.info(\""+upperCase(createCrudVO.nameOfObject)+"Controller.delete HIT!!!\")\n" +
                "\n" +
                "        logger.info(\"json: \",json.toString())\n" +
                "\n" +
                "        JsonNode root = mapper.readTree(json);\n" +
                "        "+upperCase(createCrudVO.nameOfObject)+"VO "+createCrudVO.nameOfObject+"VO = mapper.treeToValue(root, "+upperCase(createCrudVO.nameOfObject)+"VO.class)\n" +
                "\n" +
                "        String payload = executeDelete("+createCrudVO.nameOfObject+"VO)\n" +
                "\n" +
                "        return payload\n" +
                "    }\n" +
                "\n" +
                "    String executeDelete("+upperCase(createCrudVO.nameOfObject)+"VO "+createCrudVO.nameOfObject+"VO){\n" +
                "        return "+createCrudVO.nameOfObject+"Repository.delete("+createCrudVO.nameOfObject+"VO)\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "}")

    }



    //child
    void buildChildTypeHTMLPage(){
        payload.append('<div class="example-container mat-elevation-z8">\n' +
                '    <div class="example-header">\n' +
                '\n' +
                '\n' +
                '        <h3 style="margin-top: 5px;margin-left: 5px;">'+upperCase(createCrudVO.nameOfObject)+'Type</h3>\n' +
                '\n' +
                '        <span class="text-danger" *ngIf="!toggleAddUi && !toggleEditUi">\n' +
                '            <mat-form-field style="margin-top: 5px;margin-left: 5px;">\n' +
                '                <input matInput (keyup)="applyFilter($event.target.value)" placeholder="Filter">\n' +
                '            </mat-form-field>\n' +
                '            <button mat-raised-button (click)="showAdd'+upperCase(createCrudVO.nameOfObject)+'Type()" color="primary">Add New</button>\n' +
                '        </span>\n' +
                '\n' +
                '        <span class="text-danger" *ngIf="toggleAddUi">\n' +
                buildEditUiDataTable(true)+
                '\n' +
                '                <button style="" mat-raised-button (click)="hideAdd'+upperCase(createCrudVO.nameOfObject)+'Type()" color="red">Cancel</button>\n' +
                '                <button mat-raised-button (click)="add'+upperCase(createCrudVO.nameOfObject)+'Type()" color="primary">Add '+upperCase(createCrudVO.nameOfObject)+'Type</button>\n' +
                '\n' +
                '        </span>\n' +
                '        </div>\n' +
                '\n' +
                '        <span class="text-danger" *ngIf="toggleEditUi">\n' +
                '\n' +
                '\n' +
                '            <div class="container">\n' +
                '                <br>\n' +
                '            <button (click)="delete'+upperCase(createCrudVO.nameOfObject)+'Type()">delete?</button>\n' +
                '            <br>\n' +
                '                <br>\n' +
                '\n' +
                buildEditUiDataTable(true)+
                '\n' +
                '                <button style="" mat-raised-button (click)="hideAdd'+upperCase(createCrudVO.nameOfObject)+'()" color="red">Cancel</button>\n' +
                '                <button mat-raised-button (click)="add'+upperCase(createCrudVO.nameOfObject)+'()" color="primary">Add '+upperCase(createCrudVO.nameOfObject)+'</button>\n'+


        '            </div>\n' +
                '        </span>\n' +
                '\n' +
                '    </div>\n' +
                '    <mat-table #table [dataSource]="dataSource" matSort>\n' +


                buildMatDataTable(true)+


                '        <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>\n' +
                '        <mat-row *matRowDef="let row; columns: displayedColumns;" (click) = "rowClicked(row)" ></mat-row>\n' +
                '\n' +
                '    </mat-table>\n' +
                '    <mat-paginator [length]="5" [pageSize]="3" [pageSizeOptions]="[5, 10, 25]">\n' +
                '    </mat-paginator>\n')
    }


    //parent
    void buildParentTypeHTMLPage(){
        payload.append('<div class="example-container mat-elevation-z8">\n' +
                '    <div class="example-header">\n' +
                '\n' +
                '\n' +
                '        <h3 style="margin-top: 5px;margin-left: 5px;">'+upperCase(createCrudVO.nameOfObject)+'</h3>\n' +
                '\n' +
                '        <span class="text-danger" *ngIf="!toggleAddUi && !toggleEditUi">\n' +
                '            <mat-form-field style="margin-top: 5px;margin-left: 5px;">\n' +
                '                <input matInput (keyup)="applyFilter($event.target.value)" placeholder="Filter">\n' +
                '            </mat-form-field>\n' +
                '            <button mat-raised-button (click)="showAdd'+upperCase(createCrudVO.nameOfObject)+'()" color="primary">Add New</button>\n' +
                '        </span>\n' +
                '\n' +
                '        <span class="text-danger" *ngIf="toggleAddUi">\n' +
                '            <div class="container">\n' +
                '\n' +
                // this is the add ui, but its same thing as edit ui
                buildEditUiDataTable(false)+

                '\n' +
                '                <button style="" mat-raised-button (click)="hideAdd'+upperCase(createCrudVO.nameOfObject)+'Type()" color="red">Cancel</button>\n' +
                '                <button mat-raised-button (click)="add'+upperCase(createCrudVO.nameOfObject)+'Type()" color="primary">Add '+upperCase(createCrudVO.nameOfObject)+'Type</button>\n' +
                '\n' +

                '        </span>\n' +
                '\n' +
                '        <span class="text-danger" *ngIf="toggleEditUi">\n' +
                '\n' +
                '\n' +
                '            <div class="container">\n' +
                '                <br>\n' +
                '            <button (click)="delete'+upperCase(createCrudVO.nameOfObject)+'()">delete?</button>\n' +
                '            <br>\n' +
                '                <br>\n' +
                '\n' +
                // this is the edit ui, but its same thing as add ui
                buildEditUiDataTable(false)+
                '            </div>\n' +
                '        </span>\n' +
                '\n' +
                '    </div>\n' +
                '    <mat-table #table [dataSource]="dataSource" matSort>\n' +
                '\n' +
                buildMatDataTable(false)+

                '        <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>\n' +
                '        <mat-row *matRowDef="let row; columns: displayedColumns;" (click) = "rowClicked(row)" ></mat-row>\n' +
                '    </mat-table>\n' +
                '    <mat-paginator [length]="5" [pageSize]="3" [pageSizeOptions]="[5, 10, 25]">\n' +
                '    </mat-paginator>\n' +
                '</div>')
    }

    String buildChildMatDataTable(){
        StringBuilder sb = new StringBuilder()
        String name = null

        createCrudTypeVO.fieldObjects.each { FieldVO item ->

            if(item.fieldName.contains("id")||item.fieldName.contains("ID")){
                sb.append('        <ng-container matColumnDef="ID">')
                sb.append('            <mat-header-cell *matHeaderCellDef> ID </mat-header-cell>')
            } else {
                sb.append('        <ng-container matColumnDef="'+item.fieldName+'">')
                sb.append('            <mat-header-cell *matHeaderCellDef> '+item.fieldName+' </mat-header-cell>')

            }

            sb.append('            <mat-cell *matCellDef="let element"> {{element.'+item.fieldName+'}}')
            sb.append('            </mat-cell>')
            sb.append('        </ng-container>')
        }

        return sb.toString()
    }


    String buildMatDataTable(boolean type){


        StringBuilder sb = new StringBuilder()
        String name = null

        if(!type){
            createCrudVO.fieldObjects.each { FieldVO item ->

                if(item.fieldName.contains("id")||item.fieldName.contains("ID")){
                    sb.append('        <ng-container matColumnDef="ID">')
                    sb.append('            <mat-header-cell *matHeaderCellDef> ID </mat-header-cell>')
                } else {
                    sb.append('        <ng-container matColumnDef="'+item.fieldName+'">')
                    sb.append('            <mat-header-cell *matHeaderCellDef> '+item.fieldName+' </mat-header-cell>')

                }

                sb.append('            <mat-cell *matCellDef="let element"> {{element.'+item.fieldName+'}}')
                sb.append('            </mat-cell>')
                sb.append('        </ng-container>')
            }
        } else if (type){

            createCrudTypeVO.fieldObjects.each { FieldVO item ->

                if(item.fieldName.contains("id")||item.fieldName.contains("ID")){
                    sb.append('        <ng-container matColumnDef="ID">')
                    sb.append('            <mat-header-cell *matHeaderCellDef> ID </mat-header-cell>')
                } else {
                    sb.append('        <ng-container matColumnDef="'+item.fieldName+'">')
                    sb.append('            <mat-header-cell *matHeaderCellDef> '+item.fieldName+' </mat-header-cell>')

                }
                sb.append('            <mat-cell *matCellDef="let element"> {{element.'+item.fieldName+'}}')
                sb.append('            </mat-cell>')
                sb.append('        </ng-container>')
            }

        }

        return sb.toString()
    }


    String buildEditUiDataTable(boolean type){

        StringBuilder sb = new StringBuilder()
        String name = null

        if(!type){
            createCrudVO.fieldObjects.each { FieldVO item ->
                if(item.fieldName.contains("id")||item.fieldName.contains("ID")){
                    //do nothing
                } else{
                    sb.append('                <mat-form-field>')
                    sb.append('                    <input matInput placeholder="'+item.fieldName+'" [(ngModel)]="model.'+item.fieldName+'">')
                    sb.append('                </mat-form-field>')
                }
            }

            createCrudTypeVO.fieldObjects.each { FieldVO item ->
                if(item.fieldName.contains("name")){
                    name = item.fieldName
                }
            }
        } else if(type){
            createCrudTypeVO.fieldObjects.each { FieldVO item ->
                if(item.fieldName.contains("id")||item.fieldName.contains("ID")){
                    //do nothing
                } else{
                    sb.append('                <mat-form-field>')
                    sb.append('                    <input matInput placeholder="'+item.fieldName+'" [(ngModel)]="model.'+item.fieldName+'">')
                    sb.append('                </mat-form-field>')
                }
                sb.append('</div>')
            }

            createCrudTypeVO.fieldObjects.each { FieldVO item ->
                if(item.fieldName.contains("name")){
                    name = item.fieldName
                }
            }
        }



        if(!type){
            sb.append(

                    '\n' +
                            '                <mat-form-field>\n' +
                            '                  <mat-select placeholder="'+createCrudTypeVO.nameOfObject+' type id" [(ngModel)]="model.'+createCrudTypeVO.child_name_of_object+'">\n' +
                            '                    <mat-option *ngFor="let element of allChildModelTypes" [value]="element">\n' +
                            '                      {{ element.'+ (name != null?name:createCrudTypeVO.fieldObjects[1].fieldName) +' }}\n' + // grab the second field in the list if name isnt there
                            '                    </mat-option>\n' +
                            '                  </mat-select>\n' +
                            '                </mat-form-field>\n' +
                            '                </div>\n')

        } else if(type){

            // no select dropdown needed
        }


        return sb.toString()
    }


    String buildChildDataTable(){
        String name = null
        StringBuilder sb = new StringBuilder()

        sb.append('                 <!--&lt;!&ndash;The content below is only a placeholder and can be replaced.&ndash;&gt;-->\n')
        createCrudTypeVO.fieldObjects.each { FieldVO item ->
            if(item.fieldName.contains("id")
                    ||item.fieldName.contains("ID")
                    ||item.fieldName.contains("VO")){
                //do nothing
            } else{
                sb.append('                <mat-form-field>')
                sb.append('                    <input matInput placeholder="'+item.fieldName+'" [(ngModel)]="model.'+item.fieldName+'">')
                sb.append('                </mat-form-field>')
            }
        }
        return sb.toString()
    }

    // child object
    void buildTsChildTypeCrudPage(){
        payload.append('import {ActivatedRoute, Router} from "@angular/router";\n' +
                'import {AfterViewInit, Component, OnInit, ViewChild} from "@angular/core";\n' +
                'import {AuthService} from "../_helpers/auth.service";\n' +
                'import {TokenStorage} from "../_helpers/token.storage";\n' +
                'import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";\n' +
                'import {'+upperCase(createCrudVO.nameOfObject)+', '+upperCase(createCrudVO.nameOfObject)+'Type, User} from "../_models/index";\n' +
                'import {OrganizationService, UserService} from "../_services/index";\n' +
                'import {Organization} from "../cart/index";\n' +
                '\n' +
                '@Component({\n' +
                '\n' +
                '  templateUrl: \''+createCrudVO.nameOfObject+'Type.component.html\'\n' +
                '\n' +
                '})\n' +
                'export class '+upperCase(createCrudVO.nameOfObject)+'TypeComponent implements AfterViewInit,OnInit{\n' +
                'constructor(private router: Router, private authService: AuthService,private orgService: OrganizationService,private userService: UserService, private tokenStorage: TokenStorage, private route: ActivatedRoute) {}\n' +
                '\n' +
                '\n' +
                '\n' +
                '    // start generics\n' +
                '\n' +
                '    // these ones will change based on the Domain object\n' +
                '    modelList = new Array<'+upperCase(createCrudVO.nameOfObject)+'Type>();\n' +
                '    model = new '+upperCase(createCrudVO.nameOfObject)+'Type(); // this will be the object that is being edited in scope in html.\n' +
                '    childmodel:any; // child object of page\n' +
                '    allChildModelTypes = {}; // to store all types coming from server  (/childtype/getAll)\n' +
                '    newChildModel = new '+upperCase(createCrudVO.nameOfObject)+'Type(); // for creating new instances of objects\n' +
                buildDisplayedColumnsForType()+
                '    selectedItem:'+upperCase(createCrudVO.nameOfObject)+';\n' +
                '\n' +
                '\n' +
                '    // these ones will stay the same regardless of domain object\n' +
                '    toggleAddUi = false;\n' +
                '    toggleEditUi = false;\n' +
                '    loading = false;\n' +
                '    error = \'\';\n' +
                '    loggedInOrganization = new Organization();\n' +
                '    totalInventoryValue = 0;\n' +
                '    @ViewChild(MatPaginator) paginator: MatPaginator;\n' +
                '    @ViewChild(MatSort) sort: MatSort;\n' +
                '\n' +
                '    dataSource = new MatTableDataSource();\n' +
                '    loggedInUser = new User();\n' +
                '    loggedInOrg = new Organization();\n' +
                '\n' +
                '\n' +
                '    // end generics\n' +
                '\n' +
                '\n' +
                '    /**\n' +
                '     * Set the paginator after the view init since this component will\n' +
                '     * be able to query its view for the initialized paginator.\n' +
                '     */\n' +
                '\n' +
                '    // START GENERICS\n' +
                '    // NOTE: any variable within generics tags should either be\n' +
                '    ngOnInit(){\n' +
                '\n' +
                '        console.log("Inside ngOnInit");\n' +
                '        this.loggedInOrganization = this.tokenStorage.getLoggedInOrganization();\n' +
                '        console.log("this.loggedInOrganization",this.loggedInOrganization);\n' +
                '\n' +
                '    }\n' +
                '\n' +
                '    ngAfterViewInit() {\n' +
                '        console.log("Inside ngAfterViewInit");\n' +
                '\n' +
                '        this.dataSource.data = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set;\n' +
                '        this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set;\n' +
                '        this.allChildModelTypes = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // bind product types to UI\n' +
                '        console.log(this.dataSource.data);\n' +
                '        this.updateTableSortAndPageination();\n' +
                '\n' +
                '        console.log(\'this.model:\',this.model);\n' +
                '        console.log(\'this.modelList:\',this.modelList);\n' +
                '    }\n' +
                '\n' +
                '    applyFilter(filterValue: string) {\n' +
                '        filterValue = filterValue.trim(); // Remove whitespace\n' +
                '        filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches\n' +
                '        this.dataSource.filter = filterValue;\n' +
                '    }\n' +
                '\n' +
                '    // TODO: transition to detail view here\n' +
                '    rowClicked(row: any): void {\n' +
                '        this.selectedItem = row;\n' +
                '        this.model = row; // bind clicked row into the model\n' +
                '        this.hideAdd'+upperCase(createCrudVO.nameOfObject)+'Type(); // show product details when product is clicked\n' +
                '        this.showEdit'+upperCase(createCrudVO.nameOfObject)+'Type(); // show edit ui\n' +
                '        this.RenderDataTableForChildType();\n' +
                '        console.log(row);\n' +
                '    }\n' +
                '\n' +
                '    showEdit'+upperCase(createCrudVO.nameOfObject)+'Type(){\n' +
                '        this.toggleEditUi = true\n' +
                '    }\n' +
                '\n' +
                '    hideEdit'+upperCase(createCrudVO.nameOfObject)+'Type(){\n' +
                '        this.toggleEditUi = false\n' +
                '    }\n' +
                '\n' +
                '    showAdd'+upperCase(createCrudVO.nameOfObject)+'Type(){\n' +
                '        this.model = new '+upperCase(createCrudVO.nameOfObject)+'Type(); // reset with blank object\n' +
                '        this.toggleAddUi = true\n' +
                '    }\n' +
                '    hideAdd'+upperCase(createCrudVO.nameOfObject)+'Type(){\n' +
                '        this.toggleAddUi = false\n' +
                '    }\n' +
                '\n' +
                '    RenderDataTableForChildType() {\n' +
                '        this.dataSource.data = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set;\n' +
                '        this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set;\n' +
                '        console.log(\'this.modelList:\',this.modelList);\n' +
                '        this.allChildModelTypes = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // bind product types to UI\n' +
                '        console.log(this.dataSource.data);\n' +
                '        this.updateTableSortAndPageination();\n' +
                '    }\n' +
                '\n' +
                '    // This method is the exact same as what runs in ngAfterViewInit()\n' +
                '    // We need this here as well to update data table after CRUD operations so user doesnt have to refresh page\n' +
                '    RenderDataTable() {\n' +
                '        this.dataSource.data = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set;\n' +
                '        this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set;\n' +
                '        console.log(\'this.modelList:\',this.modelList);\n' +
                '        this.allChildModelTypes = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // bind product types to UI\n' +
                '        console.log(this.dataSource.data);\n' +
                '        this.updateTableSortAndPageination();\n' +
                '    }\n' +
                '\n' +
                '    updateTableSortAndPageination(){\n' +
                '        this.dataSource.paginator = this.paginator;\n' +
                '        this.dataSource.sort = this.sort;\n' +
                '    };\n' +
                '    \n' +
                '\n' +
                '\n' +
                '    delete'+upperCase(createCrudVO.nameOfObject)+'Type(){\n' +
                '        \n' +
                '        if(confirm("Are you sure to delete "+this.model.name+"?")) {\n' +
                '            console.log(\'model: \' + this.model);\n' +
                '\n' +
                '            this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set.forEach( (item, index) => {\n' +
                '                console.log("FOUND A MATCH TO DELETE");\n' +
                '                if(item === this.model) this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set.splice(index,1); // delete from array\n' +
                '            });\n' +
                '\n' +
                '            this.orgService.editObject(this.loggedInOrganization).subscribe(\n' +
                '                //         });\n' +
                '                data => {\n' +
                '                    this.loggedInOrganization = data;\n' +
                '                    this.tokenStorage.saveLoggedInOrganization(JSON.stringify(data)); // save it into session storage for reference on other pages\n' +
                '                    this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // update the list being displayed on ui\n' +
                '\n' +
                '\n' +
                '                    this.hideEdit'+upperCase(createCrudVO.nameOfObject)+'Type(); // now that add is done, close ui for add'+upperCase(createCrudVO.nameOfObject)+'\n' +
                '                    this.RenderDataTable(); // bind the returned data back into the table\n' +
                '                },\n' +
                '                error => {\n' +
                '                    this.error = error;\n' +
                '                    this.loading = false;\n' +
                '                });\n' +
                '        }\n' +
                '\n' +
                '\n' +
                '    }\n' +
                '\n' +
                '\n' +
                '    edit'+upperCase(createCrudVO.nameOfObject)+'Type(){\n' +
                '\n' +
                '       console.log(\'model: \' + this.model);\n' +
                '\n' +
                '        this.orgService.editObject(this.loggedInOrganization).subscribe(\n' +
                '            //         });\n' +
                '            data => {\n' +
                '                this.loggedInOrganization = data;\n' +
                '                this.tokenStorage.saveLoggedInOrganization(JSON.stringify(data)); // save it into session storage for reference on other pages\n' +
                '                this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // update the list being displayed on ui\n' +
                '\n' +
                '                this.hideEdit'+upperCase(createCrudVO.nameOfObject)+'Type();\n' +
                '\n' +
                '                this.hideAdd'+upperCase(createCrudVO.nameOfObject)+'Type(); // now that add is done, close ui for add'+upperCase(createCrudVO.nameOfObject)+'\n' +
                '                this.RenderDataTable(); // bind the returned data back into the table\n' +
                '            },\n' +
                '            error => {\n' +
                '                this.error = error;\n' +
                '                this.loading = false;\n' +
                '            });\n' +
                '\n' +
                '\n' +
                '    }\n' +
                '\n' +
                '    add'+upperCase(createCrudVO.nameOfObject)+'Type() {\n' +
                '        console.log(\'CALLING ADD PRODUCTYPE\');\n' +
                '        console.log(\'model: \' + this.model);\n' +
                '        this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set.push(this.model); // push newModel instance into\n' +
                '        this.orgService.editObject(this.loggedInOrganization).subscribe(\n' +
                '            //         });\n' +
                '            data => {\n' +
                '                this.loggedInOrganization = data;\n' +
                '                this.tokenStorage.saveLoggedInOrganization(JSON.stringify(data)); // save it into session storage for reference on other pages\n' +
                '                this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // update the list being displayed on ui\n' +
                '\n' +
                '\n' +
                '                this.hideAdd'+upperCase(createCrudVO.nameOfObject)+'Type(); // now that add is done, close ui for add'+upperCase(createCrudVO.nameOfObject)+'\n' +
                '                this.RenderDataTable(); // bind the returned data back into the table\n' +
                '            },\n' +
                '            error => {\n' +
                '                this.error = error;\n' +
                '                this.loading = false;\n' +
                '            });\n' +
                '    }\n' +
                '\n' +
                '\n' +
                '    // END GENERICS\n' +
                '\n' +
                '    \n' +
                '}\n' +
                '\n' +
                '')





    }


    // parent object
    void buildTsParentCrudPage(){


        payload.append('import {ActivatedRoute, Router} from "@angular/router";\n' +
                'import {AfterViewInit, Component, OnInit, ViewChild} from "@angular/core";\n' +
                'import {AuthService} from "../_helpers/auth.service";\n' +
                'import {TokenStorage} from "../_helpers/token.storage";\n' +
                'import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";\n' +
                'import {'+upperCase(createCrudVO.nameOfObject)+', '+upperCase(createCrudVO.nameOfObject)+'Type, User} from "../_models/index";\n' +
                'import {OrganizationService, UserService} from "../_services/index";\n' +
                'import {Organization} from "../cart/index";\n' +
                '@Component({\n' +
                '\n' +
                '  templateUrl: \''+createCrudVO.nameOfObject+'.component.html\'\n' +
                '})\n' +
                'export class '+upperCase(createCrudVO.nameOfObject)+'Component implements AfterViewInit,OnInit{\n' +
                'constructor(private router: Router, private authService: AuthService,private orgService: OrganizationService,private userService: UserService, private tokenStorage: TokenStorage, private route: ActivatedRoute) {}\n' +
                '\n' +
                '    // start generics\n' +
                '\n' +
                '    // these ones will change based on the Domain object\n' +
                '    modelList = new Array<'+upperCase(createCrudVO.nameOfObject)+'>();\n' +
                '    model = new '+upperCase(createCrudVO.nameOfObject)+'(); // this will be the object that is being edited in scope in html.\n' +
                '    childmodel:any; // child object of page\n' +
                '    allChildModelTypes = {}; // to store all types coming from server  (/childtype/getAll)\n' +
                '    newChildModel = new '+upperCase(createCrudVO.nameOfObject)+'Type(); // for creating new instances of objects\n' +
                buildDisplayedColumns()+
                '    selectedItem:'+upperCase(createCrudVO.nameOfObject)+';\n' +
                '\n' +
                '\n' +
                '    // these ones will stay the same regardless of domain object\n' +
                '    toggleAddUi = false;\n' +
                '    toggleEditUi = false;\n' +
                '    loading = false;\n' +
                '    error = \'\';\n' +
                '    loggedInOrganization = new Organization();\n' +
                '    totalInventoryValue = 0;\n' +
                '    @ViewChild(MatPaginator) paginator: MatPaginator;\n' +
                '    @ViewChild(MatSort) sort: MatSort;\n' +
                '\n' +
                '    // dataSource = new MatTableDataSource(ELEMENT_DATA);\n' +
                '    dataSource = new MatTableDataSource();\n' +
                '    loggedInUser = new User();\n' +
                '    loggedInOrg = new Organization();\n' +
                '\n' +
                '\n' +
                '    // end generics\n' +
                '\n' +
                '\n' +
                '    /**\n' +
                '     * Set the paginator after the view init since this component will\n' +
                '     * be able to query its view for the initialized paginator.\n' +
                '     */\n' +
                '\n' +
                '    // START GENERICS\n' +
                '    // NOTE: any variable within generics tags should either be\n' +
                '    ngOnInit(){\n' +
                '\n' +
                '        console.log("Inside ngOnInit");\n' +
                '        this.loggedInOrganization = this.tokenStorage.getLoggedInOrganization();\n' +
                '        console.log("this.loggedInOrganization",this.loggedInOrganization);\n' +
                '        this.model.'+createCrudVO.nameOfObject+'_type_id = new '+upperCase(createCrudVO.nameOfObject)+'Type();  // initialize to avoid nulls on html\n' +
                '\n' +
                '    }\n' +
                '\n' +
                '    ngAfterViewInit() {\n' +
                '        console.log("Inside ngAfterViewInit");\n' +
                '\n' +
                '        this.dataSource.data = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set;\n' +
                '        this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set;\n' +
                '        this.allChildModelTypes = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // bind '+createCrudVO.nameOfObject+' types to UI\n' +
                '        console.log(this.dataSource.data);\n' +
                '        this.updateTableSortAndPageination();\n' +
                '\n' +
                '        console.log(\'this.model:\',this.model);\n' +
                '        console.log(\'this.modelList:\',this.modelList);\n' +
                '        console.log(\'this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set:\',this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set);\n' +
                '    }\n' +
                '\n' +
                '    applyFilter(filterValue: string) {\n' +
                '        filterValue = filterValue.trim(); // Remove whitespace\n' +
                '        filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches\n' +
                '        this.dataSource.filter = filterValue;\n' +
                '    }\n' +
                '\n' +
                '    rowClicked(row: any): void {\n' +
                '        this.selectedItem = row;\n' +
                '        this.model = row; // bind clicked row into the model  NOTE: this wont have the child type data, only the ids of the child type data.  need to hydrate it\n' +
                '\n' +
                '        // TODO: add this loop to the code generator\n' +
                '        this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set.forEach(item=>{\n' +
                '            if(this.model.'+createCrudVO.nameOfObject+'_type_id.'+createCrudVO.nameOfObject+'_type_id == item.'+createCrudVO.nameOfObject+'_type_id) this.model.'+createCrudVO.nameOfObject+'_type_id = item\n' +
                '        });\n' +
                '\n' +
                '        this.hideAdd'+upperCase(createCrudVO.nameOfObject)+'(); // show '+createCrudVO.nameOfObject+' details when '+createCrudVO.nameOfObject+' is clicked\n' +
                '        this.showEdit'+upperCase(createCrudVO.nameOfObject)+'(); // show edit ui\n' +
                '        this.RenderDataTableForChildType();\n' +
                '        console.log(row);\n' +
                '    }\n' +
                '\n' +
                '    showEdit'+upperCase(createCrudVO.nameOfObject)+'(){\n' +
                '        this.toggleEditUi = true\n' +
                '    }\n' +
                '\n' +
                '    hideEdit'+upperCase(createCrudVO.nameOfObject)+'(){\n' +
                '        this.toggleEditUi = false\n' +
                '    }\n' +
                '\n' +
                '    showAdd'+upperCase(createCrudVO.nameOfObject)+'(){\n' +
                '        this.model = new '+upperCase(createCrudVO.nameOfObject)+'(); // reset with blank object\n' +
                '        this.model.'+createCrudVO.nameOfObject+'_type_id = new '+upperCase(createCrudVO.nameOfObject)+'Type();  // initialize to avoid nulls on html\n' +
                '        this.toggleAddUi = true\n' +
                '    }\n' +
                '    hideAdd'+upperCase(createCrudVO.nameOfObject)+'(){\n' +
                '        this.toggleAddUi = false\n' +
                '    }\n' +
                '\n' +
                '    RenderDataTableForChildType() {\n' +
                '        this.dataSource.data = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set;\n' +
                '        this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set;\n' +
                '        console.log(\'this.modelList:\',this.modelList);\n' +
                '        this.allChildModelTypes = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // bind '+createCrudVO.nameOfObject+' types to UI\n' +
                '        console.log(this.dataSource.data);\n' +
                '        console.log(\'this.allChildModelTypes: \',this.allChildModelTypes);\n' +
                '        this.updateTableSortAndPageination();\n' +
                '    }\n' +
                '\n' +
                '    // This method is the exact same as what runs in ngAfterViewInit()\n' +
                '    // We need this here as well to update data table after CRUD operations so user doesnt have to refresh page\n' +
                '    RenderDataTable() {\n' +
                '        this.dataSource.data = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set;\n' +
                '        this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set;\n' +
                '        console.log(\'this.modelList:\',this.modelList);\n' +
                '        this.allChildModelTypes = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_type_set; // bind '+createCrudVO.nameOfObject+' types to UI\n' +
                '        console.log(this.dataSource.data);\n' +
                '        this.updateTableSortAndPageination();\n' +
                '    }\n' +
                '\n' +
                '    updateTableSortAndPageination(){\n' +
                '        this.dataSource.paginator = this.paginator;\n' +
                '        this.dataSource.sort = this.sort;\n' +
                '    };\n' +
                '\n' +
                '\n' +
                '    delete'+upperCase(createCrudVO.nameOfObject)+'(){\n' +
                '\n' +
                '        // todo: implement logic for an edit\n' +
                '        console.log(\'CALLING DELETE PRODUCT\');\n' +
                '\n' +
                '        if(confirm("Are you sure to delete "+this.model.name+"?")) {\n' +
                '            console.log(\'model: \' + this.model);\n' +
                '\n' +
                '            this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set.forEach( (item, index) => {\n' +
                '                console.log("FOUND A MATCH TO DELETE");\n' +
                '                if(item === this.model) this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set.splice(index,1); // delete from array\n' +
                '            });\n' +
                '\n' +
                '            this.orgService.editObject(this.loggedInOrganization).subscribe(\n' +
                '                //         });\n' +
                '                data => {\n' +
                '                    this.loggedInOrganization = data;\n' +
                '                    this.tokenStorage.saveLoggedInOrganization(JSON.stringify(data)); // save it into session storage for reference on other pages\n' +
                '                    this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set; // update the list being displayed on ui\n' +
                '\n' +
                '\n' +
                '                    this.hideEdit'+upperCase(createCrudVO.nameOfObject)+'(); // now that add is done, close ui for add'+upperCase(createCrudVO.nameOfObject)+'\n' +
                '                    this.RenderDataTable(); // bind the returned data back into the table\n' +
                '                },\n' +
                '                error => {\n' +
                '                    this.error = error;\n' +
                '                    this.loading = false;\n' +
                '                });\n' +
                '        }\n' +
                '\n' +
                '\n' +
                '    }\n' +
                '\n' +
                '\n' +
                '    edit'+upperCase(createCrudVO.nameOfObject)+'(){\n' +
                '\n' +
                '        console.log(\'model: \' + this.model);\n' +
                '\n' +
                '        this.orgService.editObject(this.loggedInOrganization).subscribe(\n' +
                '            //         });\n' +
                '            data => {\n' +
                '                this.loggedInOrganization = data;\n' +
                '                this.tokenStorage.saveLoggedInOrganization(JSON.stringify(data)); // save it into session storage for reference on other pages\n' +
                '                this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set; // update the list being displayed on ui\n' +
                '\n' +
                '                this.hideEdit'+upperCase(createCrudVO.nameOfObject)+'(); // TODO: make sure this is only instance of a hide in the codegen\n' +
                '                this.RenderDataTable(); // bind the returned data back into the table\n' +
                '            },\n' +
                '            error => {\n' +
                '                this.error = error;\n' +
                '                this.loading = false;\n' +
                '            });\n' +
                '\n' +
                '\n' +
                '    }\n' +
                '\n' +
                '    add'+upperCase(createCrudVO.nameOfObject)+'() {\n' +
                '        console.log(\'CALLING ADD PRODUCT\');\n' +
                '        console.log(\'model: \' + this.model);\n' +
                '        this.model.'+createCrudVO.nameOfObject+'_id = 0// TODO: add this to generator\n' +
                '        this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set.push(this.model); // push newModel instance into\n' +
                '        this.orgService.editObject(this.loggedInOrganization).subscribe(\n' +
                '            //         });\n' +
                '            data => {\n' +
                '                this.loggedInOrganization = data;\n' +
                '                this.tokenStorage.saveLoggedInOrganization(JSON.stringify(data)); // save it into session storage for reference on other pages\n' +
                '                this.modelList = this.loggedInOrganization.'+createCrudVO.nameOfObject+'_set; // update the list being displayed on ui\n' +
                '\n' +
                '\n' +
                '                this.hideAdd'+upperCase(createCrudVO.nameOfObject)+'(); // now that add is done, close ui for add'+upperCase(createCrudVO.nameOfObject)+'\n' +
                '                this.RenderDataTable(); // bind the returned data back into the table\n' +
                '            },\n' +
                '            error => {\n' +
                '                this.error = error;\n' +
                '                this.loading = false;\n' +
                '            });\n' +
                '    }\n' +
                '\n' +
                '    \n' +
                '    // END GENERICS\n' +
                '\n' +
                '    \n' +
                '\n' +
                '}\n' +
                '\n' +
                '')

    }

    StringBuilder addGenericFields(){

        genericFields.fieldObjects.each { FieldVO item ->
            payload.append("    // generic fields below\n" +
                    "    @Temporal(TemporalType.TIMESTAMP)\n" +
                    "    @JsonProperty\n" +
                           item.dataType + " "+ item.fieldName)
        }

        return payload
    }

    void loadGenericFields(){
        genericFields.fieldObjects.addAll(
                new FieldVO(fieldName: "createTimeStamp", dataType: "java.util.Date"),
                new FieldVO(fieldName: "updateTimeStamp", dataType: "java.util.Date"))
    }


    void buildTypeScriptFile(){

        payload.append("import {ActivatedRoute, Router} from \"@angular/router\";\n" +
                "import {AuthService} from \"../../core/auth.service\";\n" +
                "import {TokenStorage} from \"../../core/token.storage\";\n" +
                "import {Component} from \"@angular/core\";\n" +
                "import {LocalDataSource} from \"ng2-smart-table\"; // this is where the data comes from\n")

        payload.append("import * as tableData from \"./"+createCrudVO.nameOfObject+"-data-table\";\n")
        payload.append("import {"+upperCase(createCrudVO.nameOfObject)+"Service} from \"../service/"+createCrudVO.nameOfObject+".service\";\n")


        payload.append("@Component({\n" +
                "  templateUrl: './"+createCrudVO.nameOfObject+".component.html'\n" +
                "})\n")

        payload.append("export class "+upperCase(createCrudVO.nameOfObject)+"Component {\n")

        payload.append("constructor(private router: Router, private authService: AuthService, private "+createCrudVO.nameOfObject+"Service: "+upperCase(createCrudVO.nameOfObject)+"Service, private token: TokenStorage, private route: ActivatedRoute) {}\n")

        payload.append("settings = tableData.settings;\n" +
                "  source = new LocalDataSource(); // this is the table data that we will fill with data from the api server\n" +
                "\n" +
                "  // this holds a list of messages to alert the user of something on a page (wrong password, validation issues, etc.)\n" +
                "  messageList: string;\n" +
                "  errorMessageDisplay: string;\n" +
                "\n" +
                "  ngOnInit(): void {\n" +
                "\n" +
                "    this.loadTableData();\n" +
                "\n" +
                "    this.source.onUpdated().subscribe( item => {\n" +
                "      this."+createCrudVO.nameOfObject+"Service.editObject(item).subscribe(data => {\n" +
                "        console.log(\"GOT DATA BACK FROM EDIT\"+data);\n" +
                "        this.loadTableData(); // load the table data from server\n" +
                "      });\n" +
                "      }\n" +
                "    );\n" +
                "\n" +
                "    this.source.onAdded().subscribe( item => {\n" +
                "        this."+createCrudVO.nameOfObject+"Service.editObject(item).subscribe(data => {\n" +
                "          console.log(\"GOT DATA BACK FROM ADD\"+data);\n" +
                "          this.loadTableData();\n" +
                "        });\n" +
                "      }\n" +
                "    );\n" +
                "\n" +
                "    this.source.onRemoved().subscribe( item => {\n" +
                "        this."+createCrudVO.nameOfObject+"Service.deleteObject(item).subscribe(data => {\n" +
                "          console.log(\"GOT DATA BACK FROM DELETE\"+data);\n" +
                "          this.loadTableData(); // load the table data from server\n" +
                "        });\n" +
                "      }\n" +
                "    );\n" +
                "\n" +
                "\n" +
                "    // subscribe to route parameters\n" +
                "    this.route.queryParams.subscribe(queryParams => {\n" +
                "      this.messageList = queryParams['messageList'];\n" +
                "      console.log(queryParams['messageList']);\n" +
                "      // only check for errors if this list has something in it\n" +
                "      if(this.messageList != null){\n" +
                "        this.checkForErrors()\n" +
                "      }\n" +
                "    });\n" +
                "    console.log('Init "+upperCase(createCrudVO.nameOfObject)+"Component');\n" +
                "  }\n" +
                "\n" +
                "  // this should be depreciated and we should use the build in error module that comes with this app\n" +
                "  checkForErrors(){\n" +
                "    console.log('Check for errors coming back from backend api server');\n" +
                "    console.log(this.messageList);\n" +
                "    this.messageList = this.messageList.replace(/[\\[\\]\"]+/g, ''); // get rid of any quotes from the backend server to make msg look nice on ui\n" +
                "    this.errorMessageDisplay = this.messageList;\n" +
                "  }\n" +
                "\n" +
                "  loadTableData(){\n" +
                "    this."+createCrudVO.nameOfObject+"Service.getAll().subscribe( arrayofdata => {\n" +
                "        console.log(\"Items from server: \"+arrayofdata);\n" +
                "        this.source.load(arrayofdata); // bind the server data into the table source data\n" +
                "      }\n" +
                "    );\n" +
                "  }\n" +
                "\n" +
                "}\n")

        payload.append("// define the data structure coming back from the api server\n" +
                "export class "+upperCase(createCrudVO.nameOfObject)+" {\n" +
                buildObjectJson()
                +
                "}\n")

    }

    String buildObjectJson(){
        StringBuilder sb = new StringBuilder()
        // for each fieldVo item append some html
            createCrudVO.fieldObjects.each { FieldVO item ->
                sb.append("  "+item.fieldName+": "+item.jsonDataType+";\n")
            }
        return sb.toString()

    }

    String buildObjectJpaEntity(){
        StringBuilder sb = new StringBuilder()

        sb = jpaEntityBuilder.buildJpaEntity(createCrudVO, sb)

        // add the generic timestamp/update timestamp fields
//        sb = jpaEntityBuilder.buildGenericFields(sb)

        payload = sb

        return payload.toString()

    }




    void buildTypeScriptServiceFile(){

        payload.append("import {Injectable} from '@angular/core';\n" +
                "import {Observable} from 'rxjs/Observable';\n" +
                "import { HttpClient, HttpHeaders } from '@angular/common/http';\n" +
                "import {AppComponent} from 'app/app.component';\n" +
                "import {"+upperCase(createCrudVO.nameOfObject)+"} from \"../"+createCrudVO.nameOfObject+"/"+createCrudVO.nameOfObject+".component\";\n" +
                "\n" +
                "@Injectable()\n" +
                "export class "+upperCase(createCrudVO.nameOfObject)+"Service {\n" +
                "\n" +
                "  constructor(private http: HttpClient) {\n" +
                "  }\n" +
                "\n" +
                "  getAll(): Observable<"+upperCase(createCrudVO.nameOfObject)+"[]> {\n" +
                "    console.log('"+createCrudVO.nameOfObject+"/getAll ::');\n" +
                "    return this.http.get<"+upperCase(createCrudVO.nameOfObject)+"[]>(AppComponent.getServiceHost()+'/"+createCrudVO.nameOfObject+"/getAll');\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "  editObject(item): Observable<any> {\n" +
                "    console.log('"+createCrudVO.nameOfObject+"/edit ::');\n" +
                "    const modCreds = JSON.stringify(item);\n" +
                "    return this.http.post<any>(AppComponent.getServiceHost()+'/"+createCrudVO.nameOfObject+"/edit', modCreds);\n" +
                "  }\n" +
                "\n" +
                "  deleteObject(item): Observable<any> {\n" +
                "    console.log('"+createCrudVO.nameOfObject+"/delete ::');\n" +
                "    const modCreds = JSON.stringify(item);\n" +
                "    return this.http.post<any>(AppComponent.getServiceHost()+'/"+createCrudVO.nameOfObject+"/delete', modCreds);\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "}")

    }

    // generic, same for every crud table
    void addGenericAngularTable(){
        payload.append("<div class=\"row\">\n" +
                "    <div class=\"col-12\">\n" +
                "        <div class=\"card\">\n" +
                "            <div class=\"card-body\">\n" +
                "                <h4 class=\"card-title\">Filterable Table</h4>\n" +
                "                <h6 class=\"card-subtitle\">Smart data table library with sorting, filtering, pagination & add/edit/delete functions.</h6>\n" +
                "                <div class=\"table table-responsive smart-table\">\n" +
                "                    <ng2-smart-table [settings]=\"settings\" [source]=\"source\" class=\"\"></ng2-smart-table>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>")
    }

    static String upperCase(String string){
        String cap = string.substring(0, 1).toUpperCase() + string.substring(1);
        return cap
    }
    

}
