//package org.example.interfazgrafica;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import jakarta.annotation.PostConstruct;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Named;
//import lombok.Getter;
//import lombok.Setter;
//import model.Project;
//import model.Response;
//import model.ResponseAPI;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Named
//@RequestScoped
//@Getter
//@Setter
//public class APIBean {
//
//    private final static long serialVersionUID = 1L;
//
//    private List<ResponseAPI> responseAPIS;
//    private ResponseAPI selectedResponseAPI;
//    private static final Logger logger = LogManager.getLogger(ProjectBean.class);
//    private int lastId = 0;
//    private static final HttpClient httpClient = HttpClient.newHttpClient();
//    private static final ObjectMapper mapper = new ObjectMapper()
//            .registerModule(new JavaTimeModule())
//            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//    @PostConstruct // Se ejecuta después de que se haya creado el bean
//    public void init() {
//        getComentarioAPI();
//    }
//
//
//    public void getComentarioAPI() {
//        responseAPIS = new ArrayList<>();
//        try {
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("http://localhost:8081/Comentario?page="))
//                    .GET()
//                    .build();
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            if (response.statusCode() == 200) {
//                ResponseAPI[] responseArray = mapper.readValue(response.body(), ResponseAPI[].class);
//                responseAPIS.addAll(Arrays.asList(responseArray));
//                System.out.println(responseAPIS);
//            } else {
//                logger.error("Error al obtener comentarios: " + response.statusCode());
//            }
//        } catch (Exception e) {
//            logger.error(e);
//        }
//    }
//
//
//}
