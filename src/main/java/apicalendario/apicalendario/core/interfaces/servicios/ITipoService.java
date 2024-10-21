package apicalendario.apicalendario.core.interfaces.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import apicalendario.apicalendario.dominio.Tipo;

@Service
public interface ITipoService {
    public List<Tipo> AllLista();

    public Tipo ObtenerTipo(Long id);

    public Tipo AgregarTipo(Tipo tipo);

    public Tipo ModificarTipo(Tipo tipo);

    public Boolean Eliminar(Long id);
}
