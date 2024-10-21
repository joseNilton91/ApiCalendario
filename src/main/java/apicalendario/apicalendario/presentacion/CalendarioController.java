package apicalendario.apicalendario.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apicalendario.apicalendario.aplicacion.CalendarioService;
import apicalendario.apicalendario.dominio.Calendario;
import apicalendario.apicalendario.dominio.Dao.FestivoDao;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/calendario")

public class CalendarioController {

    @Autowired
    private final CalendarioService calendarioService;

    public CalendarioController(CalendarioService calendarioService) {
        this.calendarioService = calendarioService;
    }

    @GetMapping("/festivos/obtener/{anio}")
    public Mono<FestivoDao> obtenerFestivos(@PathVariable String anio) {
        return calendarioService.obtenerAnosRelacionados(anio);
    }

    @GetMapping("/calendario/generar/{anio}")
    public Mono<Boolean> procesarCalendario(@PathVariable String anio) {
        boolean resultado = calendarioService.procesarCalendario(anio);
        return Mono.just(resultado);
    }

    @GetMapping("calendario/listar/{anio}")
    public List<Calendario> obtenerCalendario(@PathVariable String anio) {
        return calendarioService.listarCalendarioPorAnio(anio);
    }

}
