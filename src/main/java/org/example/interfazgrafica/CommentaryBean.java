package org.example.interfazgrafica;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Comentario;
import model.Project;
import model.Projecto;
import model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.RectangularShape;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Comentario> commentary;
    private int page;
    private int totalPage;
    private Comentario selectedCommentary = new Comentario();
    private static final Logger logger = LogManager.getLogger(ProjectBean.class);
    private int lastId = 0;
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @PostConstruct // Se ejecuta después de que se haya creado el bean
    public void init() {
        getComentarioAPI(1);
        if (!commentary.isEmpty()) {
            lastId = commentary.get(commentary.size() - 1).getComentario_id();
        }
    }

    public void getComentarioAPI(int pages) {
        commentary = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/Comentario?page=" + pages))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Response apiResponse = mapper.readValue(response.body(), Response.class);
                commentary = apiResponse.getData();
                page = apiResponse.getPage();
                totalPage = apiResponse.getTotalPages();
                System.out.println(commentary);
            } else {
                logger.error("Error al obtener comentarios: " + response.statusCode());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }


    public String selectCommentary(Comentario commentary) {
        this.selectedCommentary = commentary;
        return "detalleComentario?faces-redirect=true";
    }

    public String saveCommentary() {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/Comentario/save"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(selectedCommentary)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                logger.info("Comentario guardado correctamente");
                return "comentarioGuardado"; // Navega a una página de confirmación
            } else {
                logger.error("Error al guardar el comentario: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error al guardar el comentario", e);
            return null;
        }
    }

}
