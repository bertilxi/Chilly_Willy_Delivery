package dam.isi.frsf.utn.edu.ar.delivery.constants

import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants.localPath

object OrderActivityConstants {
    val CONTENT_ORDER_ITEMS = 687
    val CONTENT_FLAVORS = 711
    val CONTENT_ADDINS = 572
    val CONTENT_CONTAINERS = 798
    private val staticPath = localPath!! + "static/"
    val strawberryPath = staticPath + "frutilla.png"
    val vanillaPath = staticPath + "vainilla.png"
    val cherryPath = staticPath + "cereza.png"
    val dulceDeLechePath = staticPath + "dulce_de_leche.png"
}
