package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class FichaService {

    private final FichaRepository fichaRepository;
    private final ContratistaRepository contratistaRepository;
    private final ProyectoRepository proyectoRepository;
    private final AdminRepository adminRepository;
    private final CapitalHumanoRepository capitalHumanoRepository;

    public List<Ficha> getAll() {
        return fichaRepository.findAll();
    }

    public Ficha getById(Integer id) {
        return fichaRepository.findById(id).orElse(null);
    }

    public Ficha save(Ficha ficha) {
        if (ficha.getContratista() != null &&
                !contratistaRepository.existsById(ficha.getContratista().getIdContratista())) {
            throw new RuntimeException("Contratista no encontrado");
        }
        if (ficha.getProyecto() != null &&
                !proyectoRepository.existsById(ficha.getProyecto().getIdProyecto())) {
            throw new RuntimeException("Proyecto no encontrado");
        }
        if (ficha.getAdmin() != null &&
                !adminRepository.existsById(ficha.getAdmin().getIdAdmin())) {
            throw new RuntimeException("Admin no encontrado");
        }
        if (ficha.getCapitalHumano() != null &&
                !capitalHumanoRepository.existsById(ficha.getCapitalHumano().getIdCapHum())) {
            throw new RuntimeException("Capital Humano no encontrado");
        }

        return fichaRepository.save(ficha);
    }

    public void delete(Integer id) {
        fichaRepository.deleteById(id);
    }

    public Ficha update(Integer id, Ficha datos) {
        Ficha fichaExistente = getById(id);
        if (fichaExistente == null) return null;

        if (datos.getContratista() != null) {
            fichaExistente.setContratista(datos.getContratista());
        }
        if (datos.getProyecto() != null) {
            fichaExistente.setProyecto(datos.getProyecto());
        }
        if (datos.getAdmin() != null) {
            fichaExistente.setAdmin(datos.getAdmin());
        }
        if (datos.getCapitalHumano() != null) {
            fichaExistente.setCapitalHumano(datos.getCapitalHumano());
        }

        return fichaRepository.save(fichaExistente);
    }

    public List<Ficha> getByProyecto(Integer idProyecto) {
        return fichaRepository.findByProyectoIdProyecto(idProyecto);
    }

    public List<Ficha> getByContratista(Integer idContratista) {
        return fichaRepository.findByContratistaIdContratista(idContratista);
    }

    public List<Ficha> getByAdmin(Integer idAdmin) {
        return fichaRepository.findByAdminIdAdmin(idAdmin);
    }

    public List<Ficha> getByCapitalHumano(Integer idCapHum) {
        return fichaRepository.findByCapitalHumanoIdCapHum(idCapHum);
    }
}