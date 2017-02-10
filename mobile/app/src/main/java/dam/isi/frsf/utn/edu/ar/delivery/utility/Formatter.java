package dam.isi.frsf.utn.edu.ar.delivery.utility;

import java.util.List;

public class Formatter {
    static public <T> String buildStringFromList(List<T> list){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            stringBuilder.append(list.get(i).toString());
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
