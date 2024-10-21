package apicalendario.apicalendario.aplicacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import apicalendario.apicalendario.core.interfaces.repositorios.ITipoRepositorio;
import apicalendario.apicalendario.core.interfaces.servicios.ITipoService;
import apicalendario.apicalendario.dominio.Tipo;

@Service
public class TipoService implements ITipoService {

    private ITipoRepositorio repositorio;

    public TipoService(ITipoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Tipo> AllLista() {
        return repositorio.findAll();
    }

    @Override
    public Tipo ObtenerTipo(Long id) {
        Optional<Tipo> tOptional = repositorio.findById(id);
        return tOptional.orElse(null);
    }


    @Override
    public Tipo AgregarTipo(Tipo tipo) {
        tipo.setId(0);
        return repositorio.save(tipo);
    }

    @Override
    public Tipo ModificarTipo(Tipo tipo) {
        Optional<Tipo> optTipo = repositorio.findById(tipo.getId());
        if (optTipo.isPresent()) {
            return repositorio.save(tipo);
        } else {
            return null;
        }
    }

    @Override
    public Boolean Eliminar(Long id) {
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
