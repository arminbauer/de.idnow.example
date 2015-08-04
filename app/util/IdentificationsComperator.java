package util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Dominik Ecker on 04.08.2015.
 */
public class IdentificationsComperator implements Comparator<JsonNode> {

    private HashMap<Integer, JsonNode> companyHashMap;

    public IdentificationsComperator(HashMap<Integer, JsonNode> companyHashMap){
        this.companyHashMap = companyHashMap;
    }

    @Override
    public int compare(JsonNode node1, JsonNode node2) {
        int waitOfNode1 = node1.get("waiting_time").asInt();
        int waitOfNode2 = node2.get("waiting_time").asInt();
        if(waitOfNode1 < waitOfNode2){
            return 1;
        } else if (waitOfNode1 > waitOfNode2){
            return -1;
        // Waittime is identical, check Current_SLA_percentage
        } else{
            JsonNode companyOfNode1 = companyHashMap.get(node1.get("companyid").asInt());
            JsonNode companyOfNode2 = companyHashMap.get(node2.get("companyid").asInt());
            float currentSlaPercentageOfNode1 = companyOfNode1.get("current_sla_percentage").floatValue();
            float currentSlaPercentageOfNode2 = companyOfNode2.get("current_sla_percentage").floatValue();
            if(currentSlaPercentageOfNode1 < currentSlaPercentageOfNode2){
                return -1;
            } else if( currentSlaPercentageOfNode1 > currentSlaPercentageOfNode2){
                return 1;
            // Current_SLA_percentage is identical, check SLA_time
            } else {
                int slaTimeOfNode1 = companyOfNode1.get("sla_time").asInt();
                int slaTimeOfNode2 = companyOfNode2.get("sla_time").asInt();
                if(slaTimeOfNode1 < slaTimeOfNode2){
                    return -1;
                } else if(slaTimeOfNode1 > slaTimeOfNode2){
                    return 1;
                // SLA_time is identical too, return 0
                } else{
                    return 0;
                }
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
