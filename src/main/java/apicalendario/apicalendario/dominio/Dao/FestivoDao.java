package apicalendario.apicalendario.dominio.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivoDao {
    private int anio;
    List<Festivo> festivos;
}
