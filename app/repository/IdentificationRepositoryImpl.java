package repository;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import models.Identification;

import java.util.List;
import java.util.stream.Collectors;

public class IdentificationRepositoryImpl implements IdentificationRepository {

    private final static String SELECT_IDENTIFICATIONS_ORDER_BY_SLA_QUERY = "" +
            "SELECT i.* FROM identification i " +
            "JOIN company c " +
            "ON i.company_id = c.id " +
            "ORDER BY c.sla_percentage / c.sla_time DESC, i.waiting_time DESC, c.current_sla_percentage ASC ";

    @Override
    public void save(Identification identification) {
        identification.save();
    }

    @Override
    public List<Identification> getIdentificationsOrderBySLA() {
        // Query rows
        List<SqlRow> rows = Ebean.createSqlQuery(SELECT_IDENTIFICATIONS_ORDER_BY_SLA_QUERY).findList();
        // Map list of rows to list of Identification instances
        return rows.stream().map(row -> new Identification(
                row.getLong("id"),
                row.getString("name"),
                row.getTimestamp("time").toLocalDateTime(),
                row.getInteger("waiting_time"),
                row.getLong("company_id")
        )).collect(Collectors.toList());
    }
}
