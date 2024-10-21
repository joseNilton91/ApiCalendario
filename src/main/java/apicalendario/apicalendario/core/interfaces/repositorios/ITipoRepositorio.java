package apicalendario.apicalendario.core.interfaces.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import apicalendario.apicalendario.dominio.Tipo;

@Repository
public interface ITipoRepositorio extends JpaRepository<Tipo, Long>{

    Tipo findByTipo(String tipo);

}
