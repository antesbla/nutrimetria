package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.model.DatosFijosFicha;
import com.java.repository.DatosFicTecRepository;
import com.java.service.DatosFicTecService;

@Service
public class DatosFicTecServiceImpl implements DatosFicTecService {

	@Autowired
    private final DatosFicTecRepository repository;

    public DatosFicTecServiceImpl(DatosFicTecRepository repository) {
        this.repository = repository;
    }

    @Override
    public DatosFijosFicha obtener() {
        return repository.findById(1)
                .orElseThrow(() -> new RuntimeException("No se encontraron los datos fijos (ID=1)"));
    }

    @Override
    @Transactional
    public void actualizarCampo(String campo, String nuevoValor) {
        DatosFijosFicha datos = obtener();

        switch (campo) {
            case "nombreEmpresa" -> datos.setNombreEmpresa(nuevoValor);
            case "direccion" -> datos.setDireccion(nuevoValor);
            case "procesado" -> datos.setProcesado(nuevoValor);
            case "envasado" -> datos.setEnvasado(nuevoValor);
            case "textoOgm" -> datos.setTextoOgm(nuevoValor);
            case "loteadoDescripcion" -> datos.setLoteadoDescripcion(nuevoValor);
            case "microbiolog" -> datos.setMicrobiolog(nuevoValor);
            case "etiquetadoBase" -> datos.setEtiquetadoBase(nuevoValor);
            case "textoOtros" -> datos.setTextoOtros(nuevoValor);
            case "clasifLegal" -> datos.setClasifLegal(nuevoValor);
            case "reglasLoteado" -> datos.setReglasLoteado(nuevoValor);
            default -> throw new IllegalArgumentException("Campo no reconocido: " + campo);
        }

        repository.save(datos);
    }

    @Override
    @Transactional
    public void actualizarCompleto(DatosFijosFicha datosActualizados) {
        DatosFijosFicha existentes = obtener();

        existentes.setNombreEmpresa(datosActualizados.getNombreEmpresa());
        existentes.setDireccion(datosActualizados.getDireccion());
        existentes.setProcesado(datosActualizados.getProcesado());
        existentes.setEnvasado(datosActualizados.getEnvasado());
        existentes.setTextoOgm(datosActualizados.getTextoOgm());
        existentes.setLoteadoDescripcion(datosActualizados.getLoteadoDescripcion());
        existentes.setMicrobiolog(datosActualizados.getMicrobiolog());
        existentes.setEtiquetadoBase(datosActualizados.getEtiquetadoBase());
        existentes.setTextoOtros(datosActualizados.getTextoOtros());
        existentes.setClasifLegal(datosActualizados.getClasifLegal());
        existentes.setReglasLoteado(datosActualizados.getReglasLoteado());

        repository.save(existentes);
    }
}
