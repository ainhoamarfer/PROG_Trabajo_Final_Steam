package Controlador;

import Repositorio.Interfaz.ICompraRepo;
import Vista.SteamVista;

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
