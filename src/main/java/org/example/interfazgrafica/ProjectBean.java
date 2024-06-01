package org.example.interfazgrafica;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.Project;
import model.Projecto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
@Getter
@Setter
public class ProjectBean implements Serializable {

    private final static long serialVersionUID = 1L;

    private List<Project> projects;
    private Project selectedProject;
    private static final Logger logger = LogManager.getLogger(ProjectBean.class);
    private int lastId = 0;
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @PostConstruct // Se ejecuta después de que se haya creado el bean
    public void init() {
        getProjectApi();
        if (!projects.isEmpty()) {
            lastId = projects.get(projects.size() - 1).getProyecto_id();
        }
    }

    public void getProjectApi() {
        projects = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/Project"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Project[] projectArray = mapper.readValue(response.body(), Project[].class);
                projects.addAll(Arrays.asList(projectArray));
                System.out.println(projects);
            } else {
                logger.error("Error al obtener comentarios: " + response.statusCode());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }


    public String selectProject(Project project) {
        this.selectedProject = project;
        return "detalleProyecto?faces-redirect=true";
    }


}
