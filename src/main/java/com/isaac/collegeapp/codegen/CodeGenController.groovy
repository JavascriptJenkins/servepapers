package com.isaac.collegeapp.codegen

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.isaac.collegeapp.model.SystemUserDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// This controller is used to generate front-end and back-end code for CRUD pages
@RestController
@RequestMapping("/codegen")
class CodeGenController {

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();;

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler

    @PostMapping("/createCrud")
    String createCrud(@RequestBody String json) {

        logger.info("CodeGenController.createCrud HIT!!!")

        logger.info("json: ",json.toString())

        JsonNode root = mapper.readTree(json);
        SystemUserDAO userVO = mapper.treeToValue(root, User.class)

        String payload = executeCreateAccount(userVO)

        return payload
    }

    String executeCreateCrud(SystemUserDAO userVO){

//        userVO.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
        String token = userService.createAccount(userVO)
        logger.info("token: ",token.toString())
//        com.isaac.collegeapp.security.Token token1 = new Token(token: token)

        // break my off a thread brah gotta send async emails
//        threadPoolTaskScheduler.schedule(new ExecuteCreateCrudTask(userService: userService,
//                emailManager: emailManager,
//                jwtTokenProvider: jwtTokenProvider,
//                email: userVO.getEmail(),
//                username: userVO.getUsername(),
//                emailType: TaskConstants.VALIDATION_EMAIL)
//                ,new Date())
        // send a token email to validate the email address used for sign up
        //sendEmailValidationToken(userVO.getEmail())

        return mapper.writeValueAsString(token1)
    }




}
