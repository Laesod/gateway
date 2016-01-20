package com.rest.tasksManagement;

import com.dto.tasksManagement.TaskRequestDto;
import com.dto.tasksManagement.TaskRequestForRestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

/**
 * Created by aautushk on 12/3/2015.
 */
@Component
@RestController
@RequestMapping("/tasksManagement")
public class TasksRest {
    @Value("${tasksManagerRest.Host}")
    private String tasksManagerRestHost;

    @Value("${tasksManagerRest.Port}")
    private String tasksManagerRestPort;

    @Value("${tasksManagerRest.getTasksUrl}")
    private String tasksManagerRestGetTasksUrl;

    @Value("${tasksManagerRest.createTaskUrl}")
    private String tasksManagerRestCreateTaskUrl;

    @Value("${tasksManagerRest.changeTaskUrl}")
    private String tasksManagerRestChangeTaskUrl;

    @Value("${tasksManagerRest.deleteTaskUrl}")
    private String tasksManagerRestDeleteTaskUrl;

    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    private String prepareGetTasksParams(Pageable pageable,Principal user, List<String>  project, List<String>  type, List<String> status, String description){
        String urlParameters = "?size=" + pageable.getPageSize() + "&page=" + pageable.getPageNumber();

        if (pageable.getSort() != null) {
            Iterator<Sort.Order> iterator = pageable.getSort().iterator();

            while (iterator.hasNext()) {
                Sort.Order sortOrder = iterator.next();
                urlParameters += "&sort=" + sortOrder.getProperty();
                urlParameters += "," + sortOrder.getDirection();
            }
        }

        if (project != null) {
            Iterator<String> iterator = project.iterator();

            while (iterator.hasNext()) {
                String projectValue = iterator.next();
                urlParameters += "&project=" + projectValue;
            }
        }

        if (type != null) {
            Iterator<String> iterator = type.iterator();

            while (iterator.hasNext()) {
                String typeValue = iterator.next();
                urlParameters += "&type=" + typeValue;
            }
        }

        if (status != null) {
            Iterator<String> iterator = status.iterator();

            while (iterator.hasNext()) {
                String statusValue = iterator.next();
                urlParameters += "&status=" + statusValue;
            }
        }

        if (description != null) {
            urlParameters += "&description=" + description;
        }

        urlParameters += "&createdByUser=" + user.getName();

        return urlParameters;
    };

    @RequestMapping(value = "/getTasks", method = RequestMethod.GET)
    public JsonNode getTasks(@PageableDefault Pageable pageable, Principal user,
           @RequestParam(value = "project", required = false) List<String> project,
           @RequestParam(value = "type", required = false) List<String> type,
           @RequestParam(value = "status", required = false) List<String> status,
           @RequestParam(value = "description", required = false) String description) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + tasksManagerRestHost + ":" + tasksManagerRestPort + tasksManagerRestGetTasksUrl;



        String urlParameters = this.prepareGetTasksParams(pageable, user, project, type, status, description);

        url += urlParameters;

        // Note: Page page = restTemplate.getForObject(url, Page.class) doesn't work due to serialization issue
        // raw jsonString is used instead
        String jsonStr = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonStr);

        return jsonNode;
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createTask(@Valid @RequestBody TaskRequestDto taskRequestDto, Principal user) {

        TaskRequestForRestDto taskRequestForRestDto = new TaskRequestForRestDto();
        taskRequestForRestDto = modelMapper.map(taskRequestDto, TaskRequestForRestDto.class);
        taskRequestForRestDto.setUserName(user.getName());
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + tasksManagerRestHost + ":" + tasksManagerRestPort + tasksManagerRestCreateTaskUrl;

        return restTemplate.postForObject(url, taskRequestForRestDto, ResponseEntity.class);
    }

    @RequestMapping(value = "/changeTask/{taskGuid}", method = RequestMethod.PUT)
    public ResponseEntity changeTask(@PathVariable String taskGuid, @Valid @RequestBody TaskRequestDto taskRequestDto, Principal user) throws JsonProcessingException {
        TaskRequestForRestDto taskRequestForRestDto = new TaskRequestForRestDto();
        taskRequestForRestDto = modelMapper.map(taskRequestDto, TaskRequestForRestDto.class);
        taskRequestForRestDto.setUserName(user.getName());

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + tasksManagerRestHost + ":" + tasksManagerRestPort + tasksManagerRestChangeTaskUrl + "/" + taskGuid;

        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");

        String jsonStr = mapper.writeValueAsString(taskRequestForRestDto);
        HttpEntity requestEntity = new HttpEntity(jsonStr, headers);


        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseEntity.class);
    }

    @RequestMapping(value = "/deleteTask/{taskGuid}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable String taskGuid, Principal user) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + tasksManagerRestHost + ":" + tasksManagerRestPort + tasksManagerRestDeleteTaskUrl + "/" + taskGuid;
        String urlParameters = "?createdByUser=" + user.getName();
        url += urlParameters;

        return restTemplate.exchange(url, HttpMethod.DELETE, null, ResponseEntity.class);
    }

    @RequestMapping(value = "/generateReport", method = RequestMethod.GET)
    public void generateReport(@PageableDefault Pageable pageable, Principal user,
                             @RequestParam(value = "project", required = false) List<String> project,
                             @RequestParam(value = "type", required = false) List<String> type,
                             @RequestParam(value = "status", required = false) List<String> status,
                             @RequestParam(value = "description", required = false) String description,
                             HttpServletResponse response) throws IOException {

        String csvFileName = "Tasks.csv";

        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] header = { "Project", "Type", "Status", "Description", "CreatedAt"};

        csvWriter.writeHeader(header);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + tasksManagerRestHost + ":" + tasksManagerRestPort + tasksManagerRestGetTasksUrl;

        String urlParameters = this.prepareGetTasksParams(pageable, user, project, type, status, description);

        url += urlParameters;

        // Note: Page page = restTemplate.getForObject(url, Page.class) doesn't work due to serialization issue
        // raw jsonString is used instead
        String jsonStr = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonStr);


        //logic to generate List of entities based on jsonNode is missing here ...

//        if (taskEntities != null) {
//            for (TaskEntity taskEntity : taskEntities.getContent()) {
//                csvWriter.write(taskEntity, header);
//            }
//        }
//        csvWriter.close();
    }
}
