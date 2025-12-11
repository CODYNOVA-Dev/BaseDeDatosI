package com.example.demo.controler;

import com.example.demo.dto.FichaCompletaDto;
import com.example.demo.dto.FichaDto;
import com.example.demo.model.*;
import com.example.demo.service.FichaService;
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
public class FichaController {

    private final FichaService fichaService;
    private final TrabajadorService trabajadorService;

    private static final Map<String, String> ESTADOS_MAP = new HashMap<>();
    private static final Map<String, String> ESPECIALIDADES_MAP = new HashMap<>();

    static {
        // Estados
        ESTADOS_MAP.put("HIDALGO", "Hidalgo");
        ESTADOS_MAP.put("CDMX", "CDMX");
        ESTADOS_MAP.put("PUEBLA", "Puebla");
        ESTADOS_MAP.put("Hidalgo", "Hidalgo");
        ESTADOS_MAP.put("CDMX", "CDMX");
        ESTADOS_MAP.put("Puebla", "Puebla");

        // Especialidades
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

    @GetMapping("/fichas")
    public ResponseEntity<List<FichaDto>> getAll() {
        log.info("GET /fichas - Obteniendo todas las fichas");
        List<Ficha> fichas = fichaService.getAll();

        if (fichas.isEmpty()) {
            log.info("No hay fichas en la base de datos");
            return ResponseEntity.noContent().build();
        }

        log.info("Encontradas {} fichas", fichas.size());
        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/fichas")
    public ResponseEntity<FichaDto> create(@RequestBody FichaDto dto) {
        log.info("POST /fichas - Creando nueva ficha");

        try {
            Ficha ficha = convertToEntity(dto);
            Ficha savedFicha = fichaService.save(ficha);

            if (dto.getTrabajadoresIds() != null && !dto.getTrabajadoresIds().isEmpty()) {
                List<Trabajador> trabajadores = dto.getTrabajadoresIds().stream()
                        .map(trabajadorService::getById)
                        .filter(t -> t != null)
                        .peek(t -> t.setFicha(savedFicha))
                        .collect(Collectors.toList());

                trabajadorService.saveAll(trabajadores);
                savedFicha.setTrabajadores(trabajadores);
            }

            log.info("Ficha creada exitosamente con ID: {}", savedFicha.getIdFicha());
            return ResponseEntity.ok(convertToDto(savedFicha));

        } catch (Exception e) {
            log.error("Error al crear ficha: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> getById(@PathVariable Integer id) {
        log.info("GET /fichas/{} - Buscando ficha por ID", id);
        Ficha ficha = fichaService.getById(id);
        if (ficha == null) {
            log.warn("Ficha con ID {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(ficha));
    }

    @PutMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> update(@PathVariable Integer id, @RequestBody FichaDto dto) {
        log.info("PUT /fichas/{} - Actualizando ficha", id);

        Ficha entity = convertToEntity(dto);
        Ficha actualizado = fichaService.update(id, entity);

        if (actualizado == null) {
            log.warn("Ficha con ID {} no encontrada para actualizar", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Ficha ID {} actualizada exitosamente", id);
        return ResponseEntity.ok(convertToDto(actualizado));
    }

    @DeleteMapping("/fichas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("DELETE /fichas/{} - Eliminando ficha", id);
        Ficha ficha = fichaService.getById(id);
        if (ficha == null) {
            log.warn("Ficha con ID {} no encontrada para eliminar", id);
            return ResponseEntity.notFound().build();
        }
        fichaService.delete(id);
        log.info("Ficha ID {} eliminada exitosamente", id);
        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // ENDPOINTS PARA LA APP ANDROID (igual que trabajadores)
    // =====================================================

    @GetMapping("/fichas/filtros")
    public ResponseEntity<List<FichaDto>> getFichasFiltradas(
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "especialidad", required = false) String especialidad) {

        log.info("GET /fichas/filtros - Estado: {}, Especialidad: {}", estado, especialidad);

        String estadoReal = convertirEstado(estado);
        String especialidadReal = convertirEspecialidad(especialidad);

        log.debug("Filtros convertidos: estado={}->{}, especialidad={}->{}",
                estado, estadoReal, especialidad, especialidadReal);

        List<Ficha> fichas = fichaService.getAll();

        if (estadoReal != null && !estadoReal.isEmpty()) {
            fichas = fichas.stream()
                    .filter(f -> estadoReal.equalsIgnoreCase(f.getFichaEstado()))
                    .collect(Collectors.toList());
        }

        if (especialidadReal != null && !especialidadReal.isEmpty()) {
            fichas = fichas.stream()
                    .filter(f -> especialidadReal.equalsIgnoreCase(f.getFichaEspecialidad()))
                    .collect(Collectors.toList());
        }

        if (fichas.isEmpty()) {
            log.info("No se encontraron fichas con filtros: estado={}, especialidad={}",
                    estadoReal, especialidadReal);
            return ResponseEntity.noContent().build();
        }

        log.info("Encontradas {} fichas con los filtros aplicados", fichas.size());
        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/fichas/estado/{estado}")
    public ResponseEntity<List<FichaDto>> getFichasPorEstado(
            @PathVariable("estado") String estado) {

        log.info("GET /fichas/estado/{} - Buscando por estado", estado);

        String estadoReal = convertirEstado(estado);
        List<Ficha> fichas = fichaService.getAll().stream()
                .filter(f -> estadoReal.equalsIgnoreCase(f.getFichaEstado()))
                .collect(Collectors.toList());

        if (fichas.isEmpty()) {
            log.info("No se encontraron fichas en el estado: {}", estadoReal);
            return ResponseEntity.noContent().build();
        }

        log.info("Encontradas {} fichas en el estado {}", fichas.size(), estadoReal);
        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/fichas/especialidad/{especialidad}")
    public ResponseEntity<List<FichaDto>> getFichasPorEspecialidad(
            @PathVariable("especialidad") String especialidad) {

        log.info("GET /fichas/especialidad/{} - Buscando por especialidad", especialidad);

        String especialidadReal = convertirEspecialidad(especialidad);
        List<Ficha> fichas = fichaService.getAll().stream()
                .filter(f -> especialidadReal.equalsIgnoreCase(f.getFichaEspecialidad()))
                .collect(Collectors.toList());

        if (fichas.isEmpty()) {
            log.info("No se encontraron fichas con especialidad: {}", especialidadReal);
            return ResponseEntity.noContent().build();
        }

        log.info("Encontradas {} fichas con especialidad {}", fichas.size(), especialidadReal);
        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/fichas/diagnostico")
    public ResponseEntity<Map<String, Object>> diagnostico() {
        log.info("GET /fichas/diagnostico - Ejecutando diagnóstico");

        Map<String, Object> resultado = new HashMap<>();
        List<Ficha> todas = fichaService.getAll();

        resultado.put("total_fichas", todas.size());
        resultado.put("estados_disponibles", ESTADOS_MAP);
        resultado.put("especialidades_disponibles", ESPECIALIDADES_MAP);

        Map<String, Long> porEstado = todas.stream()
                .collect(Collectors.groupingBy(Ficha::getFichaEstado, Collectors.counting()));
        resultado.put("fichas_por_estado", porEstado);

        Map<String, Long> porEspecialidad = todas.stream()
                .collect(Collectors.groupingBy(Ficha::getFichaEspecialidad, Collectors.counting()));
        resultado.put("fichas_por_especialidad", porEspecialidad);

        List<Map<String, Object>> listaFichas = todas.stream()
                .map(f -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", f.getIdFicha());
                    map.put("estado", f.getFichaEstado());
                    map.put("especialidad", f.getFichaEspecialidad());
                    map.put("contratista_id", f.getContratista() != null ? f.getContratista().getIdContratista() : null);
                    map.put("proyecto_id", f.getProyecto() != null ? f.getProyecto().getIdProyecto() : null);
                    map.put("trabajadores_count", f.getTrabajadores() != null ? f.getTrabajadores().size() : 0);
                    return map;
                })
                .collect(Collectors.toList());
        resultado.put("fichas", listaFichas);

        log.info("Diagnóstico completado. Total fichas: {}", todas.size());
        return ResponseEntity.ok(resultado);
    }

    // =====================================================
    // MÉTODOS PRIVADOS
    // =====================================================

    private FichaDto convertToDto(Ficha ficha) {
        return FichaDto.builder()
                .idFicha(ficha.getIdFicha())
                .idContratista(ficha.getContratista() != null ? ficha.getContratista().getIdContratista() : null)
                .idProyecto(ficha.getProyecto() != null ? ficha.getProyecto().getIdProyecto() : null)
                .fichaEstado(ficha.getFichaEstado())
                .fichaEspecialidad(ficha.getFichaEspecialidad())
                .trabajadoresIds(
                        ficha.getTrabajadores() != null ?
                                ficha.getTrabajadores().stream()
                                        .map(Trabajador::getIdTrabajador)
                                        .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    private Ficha convertToEntity(FichaDto dto) {
        Ficha ficha = new Ficha();

        if (dto.getIdContratista() != null) {
            Contratista contratista = new Contratista();
            contratista.setIdContratista(dto.getIdContratista());
            ficha.setContratista(contratista);
        }

        if (dto.getIdProyecto() != null) {
            Proyecto proyecto = new Proyecto();
            proyecto.setIdProyecto(dto.getIdProyecto());
            ficha.setProyecto(proyecto);
        }

        ficha.setFichaEstado(dto.getFichaEstado());
        ficha.setFichaEspecialidad(dto.getFichaEspecialidad());

        return ficha;
    }

    private String convertirEstado(String estado) {
        if (estado == null) {
            return null;
        }
        String convertida = ESTADOS_MAP.get(estado);
        if (convertida != null) {
            return convertida;
        }
        String upper = estado.toUpperCase();
        if (upper.contains("HIDALGO")) {
            return "Hidalgo";
        } else if (upper.contains("CDMX") || upper.contains("CIUDAD DE MEXICO") || upper.contains("CIUDAD DE MÉXICO")) {
            return "CDMX";
        } else if (upper.contains("PUEBLA")) {
            return "Puebla";
        }
        return estado;
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
    // En FichaController.java, agrega este endpoint
    @GetMapping("/fichas/completas/filtros")
    public ResponseEntity<List<FichaCompletaDto>> getFichasCompletasFiltradas(
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "especialidad", required = false) String especialidad) {

        log.info("GET /fichas/completas/filtros - Estado: {}, Especialidad: {}", estado, especialidad);

        List<Ficha> fichas = fichaService.getAll();

        // Aplicar filtros
        String estadoReal = convertirEstado(estado);
        String especialidadReal = convertirEspecialidad(especialidad);

        if (estadoReal != null && !estadoReal.isEmpty()) {
            fichas = fichas.stream()
                    .filter(f -> estadoReal.equalsIgnoreCase(f.getFichaEstado()))
                    .collect(Collectors.toList());
        }

        if (especialidadReal != null && !especialidadReal.isEmpty()) {
            fichas = fichas.stream()
                    .filter(f -> especialidadReal.equalsIgnoreCase(f.getFichaEspecialidad()))
                    .collect(Collectors.toList());
        }

        if (fichas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FichaCompletaDto> dtos = fichas.stream()
                .map(this::convertToCompletaDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    private FichaCompletaDto convertToCompletaDto(Ficha ficha) {
        FichaCompletaDto dto = new FichaCompletaDto();
        dto.setIdFicha(ficha.getIdFicha());
        dto.setFichaEstado(ficha.getFichaEstado());
        dto.setFichaEspecialidad(ficha.getFichaEspecialidad());

        // Datos del contratista
        if (ficha.getContratista() != null) {
            dto.setNombreContratista(ficha.getContratista().getNombreContratista());
            dto.setDescripcionContratista(ficha.getContratista().getDescripcionContratista());
            dto.setTelefonoContratista(ficha.getContratista().getTelefono());
            dto.setCorreoContratista(ficha.getContratista().getCorreo());
            dto.setEspecialidadContratista(ficha.getContratista().getEspecialidad());
        }

        // Datos del proyecto
        if (ficha.getProyecto() != null) {
            dto.setNombreProyecto(ficha.getProyecto().getNombreProyecto());
            dto.setLugarProyecto(ficha.getProyecto().getLugarProyecto());
            dto.setTipoProyecto(ficha.getProyecto().getTipoProyecto());
        }

        // Equipo de trabajo - ¡CORREGIDO! usa getNombre() no getNombreTrabajador()
        if (ficha.getTrabajadores() != null && !ficha.getTrabajadores().isEmpty()) {
            String equipo = ficha.getTrabajadores().stream()
                    .map(t -> t.getNombreTrabajador() + " (" + t.getEspecialidadTrabajador() + ")")
                    .collect(Collectors.joining(", "));
            dto.setEquipoTrabajo(equipo);
        } else {
            dto.setEquipoTrabajo("Sin trabajadores asignados");
        }

        return dto;
    }
}