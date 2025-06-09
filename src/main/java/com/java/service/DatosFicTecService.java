package com.java.service;

import com.java.model.DatosFijosFicha;

public interface DatosFicTecService {

    DatosFijosFicha obtener();

    void actualizarCampo(String campo, String nuevoValor);

    void actualizarCompleto(DatosFijosFicha datosActualizados);
}
