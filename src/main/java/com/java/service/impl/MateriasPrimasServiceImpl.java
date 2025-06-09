package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.DTO.MateriaPrimaCompletaDTO;
import com.java.DTO.MateriasProveedorRelProvDTO;
import com.java.model.RelAlergenoId;
import com.java.model.RelIngredienteId;
import com.java.model.RelProveedorId;
import com.java.model.modeloAlergeno;
import com.java.model.modeloIngredientes;
import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProveedor;
import com.java.model.modeloRelAlergeno;
import com.java.model.modeloRelIngrediente;
import com.java.model.modeloRelProveedor;
import com.java.repository.AlergenoRepository;
import com.java.repository.MateriasPrimasRepository;
import com.java.repository.ProveedorRepository;
import com.java.repository.RelAlergenoRepository;
import com.java.repository.RelIngredienteRepository;
import com.java.repository.RelProveedorRepository;
import com.java.service.IngredientesService;
import com.java.service.MateriasPrimasService;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MateriasPrimasServiceImpl implements MateriasPrimasService {

    @Autowired private MateriasPrimasRepository repositorioMaterias;
    @Autowired private ProveedorRepository repositorioProveedor;
    @Autowired private RelProveedorRepository repositorioRelProveedor;
    @Autowired private AlergenoRepository repositorioAlergeno;
    @Autowired private RelAlergenoRepository repositorioRelAlergeno;
    @Autowired private RelIngredienteRepository repositorioRelIngredientes;
    @Autowired private IngredientesService servicioIngredientes;
    
    @Override
    public List<modeloMateriasPrimas> findAll() {
        return repositorioMaterias.findAll();
    }

    @Override
    public modeloMateriasPrimas findById(int id) {
        return repositorioMaterias.findById(id).orElse(null);
    }

    @Override
    public modeloMateriasPrimas save(modeloMateriasPrimas materiaPrima) {
        return repositorioMaterias.save(materiaPrima);
    }

    @Override
    public void deleteById(int id) {
        repositorioMaterias.deleteById(id);
    }
    
    public List<MateriasProveedorRelProvDTO> datosMatP() {
        List<modeloMateriasPrimas> materias = repositorioMaterias.findAll();
        List<MateriasProveedorRelProvDTO> datos = new ArrayList<>();

        for (modeloMateriasPrimas m : materias) {
            List<modeloRelProveedor> relaciones = repositorioRelProveedor.findAllByMateriaPrimaId(m.getId());

            if (relaciones.isEmpty()) continue; // Evita errores si no hay relación

            modeloRelProveedor prov = relaciones.get(0); // Aquí podrías filtrar si hay múltiples

            modeloProveedor p = prov.getProveedor();

            datos.add(new MateriasProveedorRelProvDTO(
                m.getId(),
                m.getNombre(),
                (p != null) ? p.getNombre() : "Sin proveedor",
                prov.getPrecio(),
                prov.getKcal(),
                prov.getHidratos(),
                prov.getAzucares(),
                prov.getGrasas(),
                prov.getSaturadas(),
                prov.getProteinas(),
                prov.getSal(),
                prov.getFibra(),
                m.getUnidad_medida()
            ));
        }

        return datos;
    }

    
    public void guardarMateriaPrimaDesdeDTO(MateriaPrimaCompletaDTO dto) {
        // 1. Crear y guardar la materia prima
        modeloMateriasPrimas materia = new modeloMateriasPrimas();
        materia.setNombre(dto.getNombre());
        materia.setUnidad_medida(dto.getUnidadMedida());

        modeloMateriasPrimas materiaGuardada = repositorioMaterias.save(materia);

        // 2. Obtener proveedor
        modeloProveedor proveedor = repositorioProveedor.findById(dto.getIdProveedor())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // 3. Crear relación proveedor-materia con ID compuesto
        RelProveedorId relId = new RelProveedorId(materiaGuardada.getId(), proveedor.getId());

        modeloRelProveedor relProveedor = new modeloRelProveedor();
        relProveedor.setId(relId); // ✅ ¡esto era lo que faltaba!
        relProveedor.setMateriaPrima(materiaGuardada);
        relProveedor.setProveedor(proveedor);
        relProveedor.setPrecio(dto.getPrecio());
        relProveedor.setKcal(dto.getKcal());
        relProveedor.setHidratos(dto.getHidratos());
        relProveedor.setAzucares(dto.getAzucares());
        relProveedor.setGrasas(dto.getGrasas());
        relProveedor.setSaturadas(dto.getSaturadas());
        relProveedor.setProteinas(dto.getProteinas());
        relProveedor.setSal(dto.getSal());
        relProveedor.setFibra(dto.getFibra());

        repositorioRelProveedor.save(relProveedor); // ❌ Fallaba aquí si no ponías setId

        // 4. Guardar alérgenos
        for (MateriaPrimaCompletaDTO.AlérgenoDTO alergenoDTO : dto.getAlergenos()) {
            modeloAlergeno alergeno = repositorioAlergeno.findByNombre(alergenoDTO.getNombre())
                .orElseThrow(() -> new RuntimeException("Alérgeno no encontrado: " + alergenoDTO.getNombre()));

            modeloRelAlergeno relAlergeno = new modeloRelAlergeno();
            RelAlergenoId relAlergenoId = new RelAlergenoId(alergeno.getId(), materiaGuardada.getId());
            relAlergeno.setId(relAlergenoId);
            relAlergeno.setAlergeno(alergeno);
            relAlergeno.setMateria(materiaGuardada);
            relAlergeno.setCantidad(alergenoDTO.getTipo());

            repositorioRelAlergeno.save(relAlergeno);
        }

        // 5. Guardar ingredientes
        for (MateriaPrimaCompletaDTO.IngredienteDTO ingDto : dto.getIngredientes()) {
            modeloIngredientes ingrediente = servicioIngredientes.findByNombre(ingDto.getNombre());

            if (ingrediente == null) {
                ingrediente = new modeloIngredientes();
                ingrediente.setNombre(ingDto.getNombre());
                ingrediente.setPorcentaje(ingDto.getPorcentaje());
                ingrediente = servicioIngredientes.save(ingrediente);
            }

            modeloRelIngrediente rel = new modeloRelIngrediente();
            RelIngredienteId relIngredienteId = new RelIngredienteId();
            relIngredienteId.setIngrediente(ingrediente.getId());
            relIngredienteId.setMateriaPrima(materiaGuardada.getId());

            rel.setId(relIngredienteId);
            rel.setIngrediente(ingrediente);
            rel.setMateriaPrima(materiaGuardada);

            repositorioRelIngredientes.save(rel);
        }
    }

    
    public MateriaPrimaCompletaDTO obtenerDTOCompletoPorId(int id) {
        modeloMateriasPrimas materia = repositorioMaterias.findById(id).orElse(null);
        List<modeloRelProveedor> relaciones = repositorioRelProveedor.findAllByMateriaPrimaId(id);

        if (relaciones.isEmpty()) {
            throw new RuntimeException("No hay relación proveedor-materia para ID: " + id);
        }
        modeloRelProveedor prov = relaciones.get(0);
        if (materia == null) return null;

        MateriaPrimaCompletaDTO dto = new MateriaPrimaCompletaDTO();
        dto.setId(materia.getId());
        dto.setNombre(materia.getNombre());
        dto.setPrecio(prov != null ? prov.getPrecio() : 0.0);
        dto.setIdProveedor(prov.getProveedor().getId());

        dto.setKcal(prov.getKcal());
        dto.setHidratos(prov.getHidratos());
        dto.setAzucares(prov.getAzucares());
        dto.setGrasas(prov.getGrasas());
        dto.setSaturadas(prov.getSaturadas());
        dto.setProteinas(prov.getProteinas());
        dto.setSal(prov.getSal());
        dto.setFibra(prov.getFibra());

        List<MateriaPrimaCompletaDTO.AlérgenoDTO> alergenos = repositorioRelAlergeno.findByMateria_Id(id)
            .stream()
            .map(al -> {
                MateriaPrimaCompletaDTO.AlérgenoDTO dtoAl = new MateriaPrimaCompletaDTO.AlérgenoDTO();
                dtoAl.setNombre(al.getAlergeno().getNombre());
                dtoAl.setTipo((int) al.getCantidad());
                return dtoAl;
            }).toList();
        dto.setAlergenos(alergenos);

        List<MateriaPrimaCompletaDTO.IngredienteDTO> ingredientes = repositorioRelIngredientes.findByMateriaPrima_Id(id)
        	    .stream()
        	    .map(rel -> {
        	        MateriaPrimaCompletaDTO.IngredienteDTO dtoIng = new MateriaPrimaCompletaDTO.IngredienteDTO();
        	        dtoIng.setNombre(rel.getIngrediente().getNombre());
        	        dtoIng.setPorcentaje(rel.getIngrediente().getPorcentaje());
        	        return dtoIng;
        	    }).toList();

        dto.setIngredientes(ingredientes);

        return dto;
    }

    
    public modeloMateriasPrimas findByNombre(String nombre) {
        return repositorioMaterias.findByNombre(nombre);
    }
    
    @Transactional
    public void actualizarMateriaPrimaDesdeDTO(MateriaPrimaCompletaDTO dto) {
        modeloMateriasPrimas materia = repositorioMaterias.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("Materia prima no encontrada con ID: " + dto.getId()));

        materia.setNombre(dto.getNombre());
        materia.setUnidad_medida(dto.getUnidadMedida());
        repositorioMaterias.save(materia);

        modeloProveedor proveedor = repositorioProveedor.findById(dto.getIdProveedor())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + dto.getIdProveedor()));

        // Crear ID compuesto
        RelProveedorId relId = new RelProveedorId(proveedor.getId(), materia.getId());

        modeloRelProveedor relProveedor = repositorioRelProveedor.findById(relId)
            .orElseGet(() -> {
                modeloRelProveedor nuevo = new modeloRelProveedor();
                nuevo.setId(relId);
                nuevo.setMateriaPrima(materia);
                nuevo.setProveedor(proveedor);
                return nuevo;
            });

        relProveedor.setPrecio(dto.getPrecio());
        relProveedor.setKcal(dto.getKcal());
        relProveedor.setHidratos(dto.getHidratos());
        relProveedor.setAzucares(dto.getAzucares());
        relProveedor.setGrasas(dto.getGrasas());
        relProveedor.setSaturadas(dto.getSaturadas());
        relProveedor.setProteinas(dto.getProteinas());
        relProveedor.setSal(dto.getSal());
        relProveedor.setFibra(dto.getFibra());

        repositorioRelProveedor.save(relProveedor);

        // Alergenos
        repositorioRelAlergeno.deleteByMateria_Id(materia.getId());

        for (MateriaPrimaCompletaDTO.AlérgenoDTO alergenoDTO : dto.getAlergenos()) {
            modeloAlergeno alergeno = repositorioAlergeno.findByNombre(alergenoDTO.getNombre())
                .orElseThrow(() -> new RuntimeException("Alérgeno no encontrado: " + alergenoDTO.getNombre()));

            modeloRelAlergeno relAlergeno = new modeloRelAlergeno();
            RelAlergenoId relAlergenoId = new RelAlergenoId(alergeno.getId(), materia.getId());

            relAlergeno.setId(relAlergenoId);
            relAlergeno.setMateria(materia);
            relAlergeno.setAlergeno(alergeno);
            relAlergeno.setCantidad(alergenoDTO.getTipo());

            repositorioRelAlergeno.save(relAlergeno);
        }

        // Ingredientes
        repositorioRelIngredientes.deleteByMateriaPrima_Id(materia.getId());

        for (MateriaPrimaCompletaDTO.IngredienteDTO ingDTO : dto.getIngredientes()) {
            modeloIngredientes ingrediente = servicioIngredientes.findByNombre(ingDTO.getNombre());

            if (ingrediente == null) {
                ingrediente = new modeloIngredientes();
                ingrediente.setNombre(ingDTO.getNombre());
                ingrediente.setPorcentaje(ingDTO.getPorcentaje());
                ingrediente = servicioIngredientes.save(ingrediente);
            }

            modeloRelIngrediente rel = new modeloRelIngrediente();
            RelIngredienteId relIngredienteId = new RelIngredienteId();
            relIngredienteId.setIngrediente(ingrediente.getId());
            relIngredienteId.setMateriaPrima(materia.getId());

            rel.setId(relIngredienteId);
            rel.setIngrediente(ingrediente);
            rel.setMateriaPrima(materia);

            repositorioRelIngredientes.save(rel);
        }
    }
}
