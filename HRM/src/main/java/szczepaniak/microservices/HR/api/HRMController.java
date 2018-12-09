package szczepaniak.microservices.HR.api;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import szczepaniak.microservices.HR.dto.Absence;
import szczepaniak.microservices.HR.dto.Assigment;
import szczepaniak.microservices.HR.dto.Employee;
import szczepaniak.microservices.HR.dto.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HRMController
{
    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;
    String url;
    @GetMapping("/getEmployees")
    public String getEmployees(){
         url = findApplicationUrl("Employee")+"employees";
        return restTemplate.getForObject(url, String.class);
    }

    @PostMapping("/takeAbsence")
    public String takeAbsence(@RequestBody Absence absence){
         url = findApplicationUrl("EmployeeAvailability")+"absences";
        return restTemplate.postForObject(url, absence, String.class);
    }

    @GetMapping("/getWorkplaceOfEmployee/{id}")
    public String getWorkplaceOfEmployee(@PathVariable("id") Long id){
        url = findApplicationUrl("OfficeMenagment")+"/workplaces/employee/"+id;

        return restTemplate.getForObject(url, String.class);

    }

    @PostMapping("/signAssigment/{id}")
    public String sign(@PathVariable("id") Long id){
        String url = findApplicationUrl("TaskManager") + "/assigments/sign/"+id;
        return restTemplate.postForObject(url, Assigment.class, String.class);
    }

    @PostMapping("/addTask")
    public String addTask(@RequestBody Task task){
        url = findApplicationUrl("TaskManager")+"/tasks";
        return restTemplate.postForObject(url, task, String.class);
    }

    private String findApplicationUrl(String applicationName){
        Application application = eurekaClient.getApplication(applicationName);
        List<InstanceInfo> instances = application.getInstances();
        InstanceInfo instanceInfo = instances.iterator().next();
        String hostname = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
        return "http://"+hostname+":"+port;
    }

}
