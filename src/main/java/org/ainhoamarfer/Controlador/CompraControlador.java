package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Repositorio.Interfaz.ICompraRepo;
import org.ainhoamarfer.Vista.SteamVista;

public class CompraControlador {

    /*
    Realizar compra
    Procesar pago
    Consultar historial de compras (Ficheros)
    Consultar detalles de compra
    Solicitar reembolso
    Generar factura (Ficheros)
     */

    private ICompraRepo repo;
    private SteamVista vista;

    public CompraControlador(ICompraRepo repo, SteamVista vista) {
        this.repo = repo;
        this.vista = vista;
    }
}
