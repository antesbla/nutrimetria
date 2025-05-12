package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.DTO.MateriasProveedorRelProvDTO;
import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProveedor;
import com.java.model.modeloRelProveedor;
import com.java.repository.MateriasPrimasRepository;
import com.java.repository.RelProveedorRepository;
import com.java.service.MateriasPrimasService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MateriasPrimasServiceImpl implements MateriasPrimasService {

    @Autowired
    private MateriasPrimasRepository repositorioMaterias;
    @Autowired
    private RelProveedorRepository repositorioRelMateria;

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
    
    public List<MateriasProveedorRelProvDTO> datosMatP(){
    	List <modeloMateriasPrimas> materias = repositorioMaterias.findAll();
    	List <MateriasProveedorRelProvDTO> datos = new ArrayList<MateriasProveedorRelProvDTO>();
    	
    	for (modeloMateriasPrimas m : materias) {
    		modeloRelProveedor rel = repositorioRelMateria.findByMateriaPrima(m);
    		modeloProveedor p = (rel != null) ? rel.getProveedor() : null;
    		

            datos.add(new MateriasProveedorRelProvDTO(
                m.getNombre(),
                (p != null) ? p.getNombre() : "Sin proveedor",
                rel.getPrecio(),
                rel.getKcal(),
                rel.getHidratos(),
                rel.getAzucares(),
                rel.getGrasas(),
                rel.getSaturadas(),
                rel.getProteinas(),
                rel.getSal(),
                rel.getFibra()
            ));
        }

        return datos;
    }
}
