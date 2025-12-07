package com.example.demo.controler;

import com.example.demo.dto.TrabajadorDto;
import com.example.demo.model.Trabajador;
import com.example.demo.service.TrabajadorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    private static final Map<String, String> ESPECIALIDADES_MAP = new HashMap<>();

    static {
        ESPECIALIDADES_MAP.put("OBRA", "Obra");
        ESPECIALIDADES_MAP.put("REMODELACION", "Remodelación");
        ESPECIALIDADES_MAP.put("VENTA", "Venta de Mobiliario");
        ESPECIALIDADES_MAP.put("INSTALACION", "Instalación de Mobiliario");
        ESPECIALIDADES_MAP.put("OBRA_SIMPLE", "Obra");
        ESPECIALIDADES_MAP.put("REMODELACION_SIMPLE", "Remodelación");
        ESPECIALIDADES_MAP.put("VENTA_SIMPLE", "Venta de Mobiliario");
        ESPECIALIDADES_MAP.put("INSTALACION_SIMPLE", "Instalación de Mobiliario");
        ESPECIALIDADES_MAP.put("Obra", "Obra");
        ESPECIALIDADES_MAP.put("Remodelación", "Remodelación");
        ESPECIALIDADES_MAP.put("Venta de Mobiliario", "Venta de Mobiliario");
        ESPECIALIDADES_MAP.put("Instalación de Mobiliario", "Instalación de Mobiliario");
    }

    @GetMapping("/trabajadores")
    public ResponseEntity<List<TrabajadorDto>> getAll() {
        log.info("GET /trabajadores - Obteniendo todos los trabajadores");
        List<Trabajador> lista = trabajadorService.getAll();

        if (lista.isEmpty()) {
            log.info("No hay trabajadores en la base de datos");
            return ResponseEntity.noContent().build();
        }

        log.info("Encontrados {} trabajadores", lista.size());
        return ResponseEntity.ok(
                lista.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<TrabajadorDto> getById(@PathVariable("id") Integer id) {
        log.info("GET /trabajadores/{} - Buscando trabajador por ID", id);
        Trabajador trabajador = trabajadorService.getById(id);
        if (trabajador == null) {
            log.warn("Trabajador con ID {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(trabajador));
    }

    @GetMapping("/trabajadores/nss/{nss}")
    public ResponseEntity<TrabajadorDto> getByNss(@PathVariable("nss") String nss) {
        log.info("GET /trabajadores/nss/{} - Buscando trabajador por NSS", nss);
        Trabajador trabajador = trabajadorService.getByNss(nss);
        if (trabajador == null) {
            log.warn("Trabajador con NSS {} no encontrado", nss);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(trabajador));
    }

    @PostMapping("/trabajadores")
    public ResponseEntity<TrabajadorDto> create(@RequestBody TrabajadorDto dto) {
        log.info("POST /trabajadores - Creando nuevo trabajador: {}", dto.getNombreTrabajador());

        if (trabajadorService.existsByNss(dto.getNssTrabajador())) {
            log.warn("NSS {} ya existe en la base de datos", dto.getNssTrabajador());
            return ResponseEntity.badRequest().body(null);
        }

        Trabajador trabajador = convertToEntity(dto);
        Trabajador saved = trabajadorService.save(trabajador);
        log.info("Trabajador creado exitosamente con ID: {}", saved.getIdTrabajador());

        return ResponseEntity.ok(convertToDto(saved));
    }

    @PostMapping("/trabajadores/lote")
    public ResponseEntity<List<TrabajadorDto>> createBatch(@RequestBody List<TrabajadorDto> dtos) {
        log.info("POST /trabajadores/lote - Creando {} trabajadores", dtos.size());

        List<Trabajador> trabajadores = dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        trabajadorService.saveAll(trabajadores);
        log.info("{} trabajadores creados exitosamente", trabajadores.size());

        List<TrabajadorDto> result = trabajadores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PutMapping("/trabajadores/{id}")
    public ResponseEntity<TrabajadorDto> update(@PathVariable("id") Integer id, @RequestBody TrabajadorDto dto) {
        log.info("PUT /trabajadores/{} - Actualizando trabajador", id);
        Trabajador trabajador = convertToEntity(dto);
        Trabajador actualizado = trabajadorService.update(id, trabajador);

        if (actualizado == null) {
            log.warn("Trabajador con ID {} no encontrado para actualizar", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Trabajador ID {} actualizado exitosamente", id);
        return ResponseEntity.ok(convertToDto(actualizado));
    }

    @DeleteMapping("/trabajadores/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        log.info("DELETE /trabajadores/{} - Eliminando trabajador", id);
        Trabajador trabajador = trabajadorService.getById(id);
        if (trabajador == null) {
            log.warn("Trabajador con ID {} no encontrado para eliminar", id);
            return ResponseEntity.notFound().build();
        }
        trabajadorService.delete(id);
        log.info("Trabajador ID {} eliminado exitosamente", id);
        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // ENDPOINTS PARA LA APP ANDROID
    // =====================================================

    @GetMapping("/trabajadores/filtros")
    public ResponseEntity<List<TrabajadorDto>> getTrabajadoresFiltrados(
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "especialidad", required = false) String especialidad) {

        log.info("GET /trabajadores/filtros - Estado: {}, Especialidad: {}", estado, especialidad);

        String especialidadReal = convertirEspecialidad(especialidad);
        log.debug("Especialidad convertida: {} -> {}", especialidad, especialidadReal);

        List<Trabajador> trabajadores = trabajadorService.getFiltrados(estado, especialidadReal);

        if (trabajadores.isEmpty()) {
            log.info("No se encontraron trabajadores con filtros: estado={}, especialidad={}", estado, especialidadReal);
            return ResponseEntity.noContent().build();
        }

        log.info("Encontrados {} trabajadores con los filtros aplicados", trabajadores.size());
        List<TrabajadorDto> dtos = trabajadores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/trabajadores/estado/{estado}")
    public ResponseEntity<List<TrabajadorDto>> getTrabajadoresPorEstado(
            @PathVariable("estado") String estado) {

        log.info("GET /trabajadores/estado/{} - Buscando por estado", estado);

        List<Trabajador> trabajadores = trabajadorService.getByEstado(estado);

        if (trabajadores.isEmpty()) {
            log.info("No se encontraron trabajadores en el estado: {}", estado);
            return ResponseEntity.noContent().build();
        }

        log.info("Encontrados {} trabajadores en el estado {}", trabajadores.size(), estado);
        List<TrabajadorDto> dtos = trabajadores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/trabajadores/especialidad/{especialidad}")
    public ResponseEntity<List<TrabajadorDto>> getTrabajadoresPorEspecialidad(
            @PathVariable("especialidad") String especialidad) {

        log.info("GET /trabajadores/especialidad/{} - Buscando por especialidad", especialidad);

        String especialidadReal = convertirEspecialidad(especialidad);
        log.debug("Especialidad convertida: {} -> {}", especialidad, especialidadReal);

        List<Trabajador> trabajadores = trabajadorService.getByEspecialidad(especialidadReal);

        if (trabajadores.isEmpty()) {
            log.info("No se encontraron trabajadores con especialidad: {}", especialidadReal);
            return ResponseEntity.noContent().build();
        }

        log.info("Encontrados {} trabajadores con especialidad {}", trabajadores.size(), especialidadReal);
        List<TrabajadorDto> dtos = trabajadores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/trabajadores/diagnostico")
    public ResponseEntity<Map<String, Object>> diagnostico() {
        log.info("GET /trabajadores/diagnostico - Ejecutando diagnóstico");

        Map<String, Object> resultado = new HashMap<>();
        List<Trabajador> todos = trabajadorService.getAll();

        resultado.put("total_trabajadores", todos.size());
        resultado.put("especialidades_disponibles", ESPECIALIDADES_MAP);

        Map<String, Long> porEstado = todos.stream()
                .collect(Collectors.groupingBy(Trabajador::getEstadoTrabajador, Collectors.counting()));
        resultado.put("trabajadores_por_estado", porEstado);

        Map<String, Long> porEspecialidad = todos.stream()
                .collect(Collectors.groupingBy(Trabajador::getEspecialidadTrabajador, Collectors.counting()));
        resultado.put("trabajadores_por_especialidad", porEspecialidad);

        List<Map<String, Object>> listaTrabajadores = todos.stream()
                .map(t -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", t.getIdTrabajador());
                    map.put("nombre", t.getNombreTrabajador());
                    map.put("estado", t.getEstadoTrabajador());
                    map.put("especialidad", t.getEspecialidadTrabajador());
                    map.put("nss", t.getNssTrabajador());
                    map.put("descripcion", t.getDescripcionTrabajador());
                    return map;
                })
                .collect(Collectors.toList());
        resultado.put("trabajadores", listaTrabajadores);

        log.info("Diagnóstico completado. Total trabajadores: {}", todos.size());
        return ResponseEntity.ok(resultado);
    }

    // =====================================================
    // MÉTODOS PRIVADOS
    // =====================================================

    private TrabajadorDto convertToDto(Trabajador trabajador) {
        return TrabajadorDto.builder()
                .idTrabajador(trabajador.getIdTrabajador())
                .nssTrabajador(trabajador.getNssTrabajador())
                .nombreTrabajador(trabajador.getNombreTrabajador())
                .especialidadTrabajador(trabajador.getEspecialidadTrabajador())
                .estadoTrabajador(trabajador.getEstadoTrabajador())
                .descripcionTrabajador(trabajador.getDescripcionTrabajador())
                .build();
    }

    private Trabajador convertToEntity(TrabajadorDto dto) {
        return Trabajador.builder()
                .idTrabajador(dto.getIdTrabajador())
                .nssTrabajador(dto.getNssTrabajador())
                .nombreTrabajador(dto.getNombreTrabajador())
                .especialidadTrabajador(dto.getEspecialidadTrabajador())
                .estadoTrabajador(dto.getEstadoTrabajador())
                .descripcionTrabajador(dto.getDescripcionTrabajador())
                .build();
    }

    private String convertirEspecialidad(String especialidad) {
        if (especialidad == null) {
            return null;
        }
        String convertida = ESPECIALIDADES_MAP.get(especialidad);
        if (convertida != null) {
            return convertida;
        }
        String upper = especialidad.toUpperCase();
        if (upper.contains("INSTALACION") || upper.contains("INSTALACIÓN")) {
            return "Instalación de Mobiliario";
        } else if (upper.contains("VENTA")) {
            return "Venta de Mobiliario";
        } else if (upper.contains("REMODELACION") || upper.contains("REMODELACIÓN")) {
            return "Remodelación";
        } else if (upper.contains("OBRA")) {
            return "Obra";
        }
        return especialidad;
    }
}