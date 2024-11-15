package apicalendario.apicalendario.aplicacion;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import apicalendario.apicalendario.core.interfaces.repositorios.ICalendarioRepositorio;
import apicalendario.apicalendario.core.interfaces.repositorios.ITipoRepositorio;
import apicalendario.apicalendario.dominio.Calendario;
import apicalendario.apicalendario.dominio.Tipo;
import apicalendario.apicalendario.dominio.Dao.Festivo;
import apicalendario.apicalendario.dominio.Dao.FestivoDao;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.sql.Date;
import java.util.Locale;

import java.util.stream.Collectors;

@Service
public class CalendarioService {

    private final WebClient webClient;
    private final ICalendarioRepositorio calenRepo;
    private final ITipoRepositorio tipoRepo;

    @Autowired
    public CalendarioService(WebClient.Builder webClientBuilder, ICalendarioRepositorio calenRepo,
            ITipoRepositorio tipoRepo) {
        this.webClient = webClientBuilder.baseUrl("http://172.18.0.4:3030/festivos").build();

        this.calenRepo = calenRepo;
        this.tipoRepo = tipoRepo;
    }

    public Mono<FestivoDao> obtenerAnosRelacionados(String anio) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/obtener/{anio}").build(anio))
                .retrieve() // Para obtener la respuesta
                .bodyToMono(FestivoDao.class);
    }

    @Transactional
    public boolean procesarCalendario(String anio) {

        FestivoDao festivoDao = obtenerAnosRelacionados(anio).block();
        if (festivoDao == null || festivoDao.getFestivos().isEmpty()) {
            return false;
        }

        List<Festivo> festivos = festivoDao.getFestivos();
        Map<LocalDate, String> festivosMap = festivos.stream()
                .collect(Collectors.toMap(Festivo::getFecha, Festivo::getNombre));

        LocalDate inicioAnio = LocalDate.of(Integer.parseInt(anio), 1, 1);
        LocalDate finAnio = LocalDate.of(Integer.parseInt(anio), 12, 31);

        for (LocalDate fecha = inicioAnio; !fecha.isAfter(finAnio); fecha = fecha.plusDays(1)) {
            Tipo tipoDia;
            String descripcion;

            String diaDeLaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.of("es", "CO"));

            if (festivosMap.containsKey(fecha)) {

                tipoDia = tipoRepo.findByTipo("Día Laboral");
                descripcion = diaDeLaSemana;

            } else if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {

                tipoDia = tipoRepo.findByTipo("Fin de Semana");
                descripcion = diaDeLaSemana;
            } else {

                tipoDia = tipoRepo.findByTipo("Día festivo");
                descripcion = diaDeLaSemana;
            }

            Calendario calendario = new Calendario();
            calendario.setTipo(tipoDia);
            calendario.setFecha(fecha);

            calendario.setDescripcion(descripcion);
            calenRepo.save(calendario);
        }

        return true;
    }

    public List<Calendario> listarCalendarioPorAnio(String anio) {
        return calenRepo.findByFechaYear(anio);
    }

}
