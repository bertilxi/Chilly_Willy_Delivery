package dam.isi.frsf.utn.edu.ar.delivery.utility;

import java.util.List;

/**
 * Created by andres on 05/02/2017.
 */

public class Formatter {
    static public String buildStringFromList(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            stringBuilder.append(list.get(i));
            if(i < list.size() - 2){
                stringBuilder.append(", ");
            }
            else if(i == list.size() - 2){
                stringBuilder.append(" y ");
            }
        }
        return stringBuilder.toString();
    }
}
