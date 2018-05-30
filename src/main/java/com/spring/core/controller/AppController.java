package com.spring.core.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/")
public class AppController {

    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonParser parser = new JsonParser();

    @RequestMapping(value = {"/index","/"})
    @ResponseBody
    public String index(){
        return "Hello Spring MVC";
    }

    @RequestMapping("/read")
    @ResponseBody
    public void readFile(){
        ClassLoader  loader = getClass().getClassLoader();
        File file = new File(loader.getResource("package.json").getFile());
        try {
            JsonArray array =  new JsonArray();
         Object o = mapper.readValue(file,Object.class);
         String json = mapper.writeValueAsString(o);
         JsonObject object  =  parser.parse(json).getAsJsonObject();
         array.add(object);

            for (JsonElement element:array) {
                System.out.println("element : "+element);
                JsonArray x = (JsonArray)element.getAsJsonObject().get("phoneNumbers");
                for (JsonElement jsonElement:x){
                    System.out.println("contact: "+jsonElement);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
