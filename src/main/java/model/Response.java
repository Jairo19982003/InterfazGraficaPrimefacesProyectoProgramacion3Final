package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private List<Comentario> data = new ArrayList<>();
    private int page;
    private int totalPages;
}
