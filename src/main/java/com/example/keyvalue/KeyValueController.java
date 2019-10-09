package com.example.keyvalue;

import com.example.keyvalue.model.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.List;

@RestController
public class KeyValueController {

    @Autowired
    private KeyValueService keyValueService;


    @RequestMapping(value = "/createPath", method = RequestMethod.GET)
    public void CreatePath() throws IOException {
        keyValueService.createPath();
    }

    @RequestMapping(value = "/getPath", method = RequestMethod.GET)
    @ResponseBody
    public boolean getPath(@RequestParam(required = false) String pathName) throws IOException {
        String reqParam =  URLDecoder.decode(pathName, "UTF-8");
        return keyValueService.getPath(reqParam);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getPath(@RequestBody List<KeyValue> keyValue) throws IOException {
        String statement = keyValueService.checkFileSize(keyValue);
        if(statement.equalsIgnoreCase("true")) {
            boolean IsKeyExists = false;
            IsKeyExists = keyValueService.checkKeyAlreadyexists(keyValue);
            if (!IsKeyExists) {
                keyValueService.createOperation(keyValue);
                return "KeyValue added Successfully";
            }
            else
                return "Key Already Exists";
        }
        else
        return statement;
    }

    @RequestMapping(value = "/getJSONObject/{key}", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getJSONObject(@PathVariable String key) throws IOException, ParseException {
        return keyValueService.getKeyValues(key);
    }

    @RequestMapping(value = "/DeleteKey/{key}", method = RequestMethod.DELETE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String DeleteJSONObject(@PathVariable String key) throws IOException, ParseException {
         return keyValueService.deleteKeyValues(key);
    }
}
