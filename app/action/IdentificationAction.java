package action;

import java.util.ArrayList;
import java.util.List;
import model.Identification;

public class IdentificationAction
{
    private static List<Identification> identifications = new ArrayList<>();

    public Identification addIdentification(Identification identification){
        if(!identifications.contains(identification)){
            identifications.add(identification);
            return identification;
        }
        return null;
    }

    public List<Identification> pendingIdentifications(){
        return identifications;
    }

    public void clearIdentifications(){
        identifications.clear();
    }
}
