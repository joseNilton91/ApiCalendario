package apicalendario.apicalendario.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import apicalendario.apicalendario.core.interfaces.servicios.ITipoService;
import apicalendario.apicalendario.dominio.Tipo;

@RestController
@RequestMapping("/Tipodia")
public class TipoController {

    @Autowired
    private ITipoService Service;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Tipo> AllLista() {
        return Service.AllLista();
    }

    @RequestMapping(value = "/obtener/{id}", method = RequestMethod.GET)
    public Tipo ObtenerTipo(@PathVariable long id) {
        return Service.ObtenerTipo(id);
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public Tipo AgregarTipo(@RequestBody Tipo tipo) {
        tipo.setId(0);
        return Service.AgregarTipo(tipo);
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public Tipo modificar(@RequestBody Tipo tipo) {
        if (Service.ObtenerTipo(tipo.getId()) != null) {
            return Service.AgregarTipo(tipo);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
    public Boolean eliminar(@PathVariable long id) {
        return Service.Eliminar(id);
    }
}
