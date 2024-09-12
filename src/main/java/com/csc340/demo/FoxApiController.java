package com.csc340.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FoxApiController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/fox")
    public Object getFox() {
        try {
            String url = "https://randomfox.ca/floof/";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            Fox fox = new Fox(root.get("image").asText(), root.get("link").asText());

            return fox;

        } catch (JsonProcessingException ex) {
            Logger.getLogger(FoxApiController.class.getName()).log(Level.SEVERE,
                    null, ex);
            return "error in /fox";

        }
    }
}
