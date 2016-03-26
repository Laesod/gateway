package com.rest.filesManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by aautushk on 12/17/2015.
 */
@Component
@RestController
@RequestMapping("/filesManagement")
public class        FilesRest {
    @Value("${fileManager.Host}")
    private String fileManagerHost;

    @Value("${fileManager.Port}")
    private String fileManagerPort;

    @Value("${fileManager.getPresignedUrlUrl}")
    private String fileManagerGetPresignedUrlUrl;

    @RequestMapping(value = "/generatePresignedUrlForS3", method = RequestMethod.GET)
    public JsonNode generatePresignedUrlForS3(@RequestParam(value = "s3ObjectKey") String objectKey) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + fileManagerHost + ":" + fileManagerPort + fileManagerGetPresignedUrlUrl;
        String urlParameters = "?objectKey=" + objectKey;

        url += urlParameters;

        String jsonStr = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonStr);

        return jsonNode;
    }
}
