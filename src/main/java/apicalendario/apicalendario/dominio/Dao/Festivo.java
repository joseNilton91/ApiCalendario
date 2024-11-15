package apicalendario.apicalendario.dominio.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Festivo {
    private String nombre;
    private LocalDate fecha;
}
