package dam.isi.frsf.utn.edu.ar.delivery.utility

object Formatter {
    fun <T> buildStringFromList(list: List<T>?): String {
        if (list == null) {
            return ""
        }
        val stringBuilder = StringBuilder()
        for (i in list.indices) {
            stringBuilder.append(list[i].toString())
            if (i < list.size - 2) {
                stringBuilder.append(", ")
            } else if (i == list.size - 2) {
                stringBuilder.append(" y ")
            }
        }
        return stringBuilder.toString()
    }
}
