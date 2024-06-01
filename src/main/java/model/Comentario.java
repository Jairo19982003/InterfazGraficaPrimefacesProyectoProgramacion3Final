package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {

    private Integer comentario_id;
    private String comentario;
    private LocalDateTime fecha = LocalDateTime.now();
    private String nombreUser;
    private String emailUser;
    private Project project = new Project();


}

