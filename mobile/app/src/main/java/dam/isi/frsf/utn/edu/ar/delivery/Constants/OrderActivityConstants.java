package dam.isi.frsf.utn.edu.ar.delivery.Constants;

import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class OrderActivityConstants {
    public static final int CONTENT_ORDER_ITEMS = 687;
    public static final int CONTENT_FLAVORS = 711;
    public static final int CONTENT_ADDINS = 572;
    private static final String localPath = DataService.localPath;
    private static final String staticPath = localPath + "static/";
    public static final String strawberryPath = staticPath + "frutilla.png";
    public static final String vanillaPath = staticPath + "vainilla.png";
    public static final String cherryPath = staticPath + "cereza.png";
    public static final String dulceDeLechePath = staticPath + "dulce_de_leche.png";
}
