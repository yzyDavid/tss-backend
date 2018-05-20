package tss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tss.entities.CourseEntity;
import tss.entities.UserEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Map<Operators, String> opMap = new HashMap<>();

    public QueryService() {
        opMap.put(Operators.EQ, " = ");
        opMap.put(Operators.NE, " <> ");
        opMap.put(Operators.GT, " > ");
        opMap.put(Operators.GE, " >= ");
        opMap.put(Operators.LT, " < ");
        opMap.put(Operators.LE, " <= ");
        opMap.put(Operators.LIKE, " LIKE ");
    }

    public <T> List<T> query(Class<T> entity, NameValuePair[] pairs) {
        String tableName = entity.getAnnotation(Table.class).name();
        String[] whereClauses = new String[pairs.length];
        Object[] values = new Object[pairs.length];
        //List<String> joinTables = new ArrayList<>();
        try {
            int i = 0;
            for (NameValuePair pair : pairs) {
                if (pair.value == null) {
                    continue;
                }
                Field field = entity.getField(pair.name);

                // Get column name and value
                Column column = field.getAnnotation(Column.class);
                String cName;
                if (column != null && column.name().length() > 0) {
                    cName = column.name();
                } else {
                    JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                    if (joinColumn != null && joinColumn.name().length() > 0) {
                        cName = joinColumn.name();
                    } else {
                        cName = field.getName();
                    }
                }
                whereClauses[i] = cName + opMap.get(pair.op) + "?";
                values[i] = pair.value;
                i++;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // Get sql
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        sql.append(" WHERE ");
        for (String whereClause : whereClauses) {
            sql.append(whereClause);
            sql.append(", ");
        }
        sql.replace(sql.length() - 2, sql.length(), ";");

        return jdbcTemplate.query(sql.toString(), values,
                new BeanPropertyRowMapper<>(entity));
    }

    public List<UserEntity> queryUsers(String uid, String name, Short deptId) {
        NameValuePair[] pairs = new NameValuePair[3];
        pairs[0] = new NameValuePair("uid", uid, Operators.LIKE);
        pairs[1] = new NameValuePair("name", name, Operators.LIKE);
        pairs[2] = new NameValuePair("department", deptId, Operators.EQ);
        return query(UserEntity.class, pairs);
    }

    public List<CourseEntity> queryCourses(String cid, String name, Short deptId) {
        NameValuePair[] pairs = new NameValuePair[3];
        pairs[0] = new NameValuePair("cid", cid, Operators.LIKE);
        pairs[1] = new NameValuePair("name", name, Operators.LIKE);
        pairs[2] = new NameValuePair("department", deptId, Operators.EQ);
        return query(CourseEntity.class, pairs);
    }
}

enum Operators {EQ, NE, GT, GE, LT, LE, LIKE}

class NameValuePair {
    final String name;
    final Object value;
    final Operators op;

    public NameValuePair(String name, Object value, Operators op) {
        this.name = name;
        this.value = value;
        this.op = op;
    }
}



