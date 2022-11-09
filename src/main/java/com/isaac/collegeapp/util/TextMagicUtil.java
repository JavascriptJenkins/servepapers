package com.isaac.collegeapp.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.model.TokenDAO;
import com.isaac.collegeapp.modelnonpersist.TextMagicVO;
import com.squareup.okhttp.Call;
import com.textmagic.sdk.*;
import com.textmagic.sdk.api.TextMagicApi;
import com.textmagic.sdk.auth.ApiKeyAuth;
import com.textmagic.sdk.auth.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
public class TextMagicUtil {


    @Autowired
    Environment env;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();


    // todo: enforce country code
    public String sendValidationText(SystemUserDAO systemUserDAO, String token){

//        curl -X POST "https://rest.textmagic.com/api/v2/messages" -H "accept: */*" -H "X-TM-Key: <apikey>" -H "X-TM-Username: techvvsvvs" -H "Content-Type: application/json" -d "{\"text\":\"hey\",\"phones\":\"55555\"}"

        RestTemplate restTemplate = new RestTemplate();


        TextMagicVO textMagicVO = new TextMagicVO();
        textMagicVO.setText("Validate your techvvs account with token: "+token);
        textMagicVO.setPhones(systemUserDAO.getPhone());

        String reqBody = "";
        try{
            reqBody = mapper.writeValueAsString(textMagicVO);
        } catch(JsonProcessingException ex) {
            System.out.println("JsonProcessingException: "+ex.getMessage());
        }

        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-TM-Key", env.getProperty("textmagic.api.key"));
        headers.set("X-TM-Username", "techvvsvvs");

        HttpEntity<String> request =
                new HttpEntity<String>(reqBody, headers);

        String result = "";
        try{
            result =
                    restTemplate.postForObject("https://rest.textmagic.com/api/v2/messages", request, String.class);
        }catch(Exception ex){
            System.out.println("Exception sending request to textmagic: "+ex.getMessage());
        }


        try{
            JsonNode root = mapper.readTree(result);
            System.out.println("result: "+mapper.writeValueAsString(root));

        } catch(Exception ex){
            System.out.println("Exception mapping the result from textmagic: "+ex.getMessage());
        }





        return "success";

    }


    public String createAndSendNewPhoneToken(SystemUserDAO systemUserDAO){
        // save a token value to a username and then send a text message
        String tokenval = String.valueOf(secureRandom.nextInt(1000000));
        String result = "";
        try {
            TokenDAO tokenDAO = new TokenDAO();
            tokenDAO.setUsermetadata(systemUserDAO.getEmail());
            tokenDAO.setToken(tokenval);
            tokenDAO.setTokenused(0);
            tokenDAO.setCreatetimestamp(LocalDateTime.now());
            tokenDAO.setUpdatedtimestamp(LocalDateTime.now());
            tokenRepo.save(tokenDAO);

        } catch(Exception ex){
            System.out.println("error inserting token into database");
        } finally{
            // only send the text message after everything else went smoothly
            // todo : check result of this
            result = sendValidationText(systemUserDAO, tokenval);
        }


        return "success";


    }





}
