package apicalendario.apicalendario.core.interfaces.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import apicalendario.apicalendario.dominio.Calendario;

@Repository

public interface ICalendarioRepositorio extends JpaRepository<Calendario, Long> {
    
    @Query("SELECT c FROM Calendario c WHERE YEAR(c.fecha) = :anio")
    List<Calendario> findByFechaYear(@Param("anio") String anio);
}

