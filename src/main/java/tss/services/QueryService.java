package tss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tss.entities.CourseEntity;
import tss.entities.UserEntity;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class QueryService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Map<Operators, String> opMap = new HashMap<>();
    private static RowMapperFactory rowMapperFactory = new RowMapperFactory();

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
        if(entity.getAnnotation(Entity.class) == null) {
            return null;
        }
        List<String> whereClauses = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        String tableName;

        Table tableAnnotaion = entity.getAnnotation(Table.class);
        if(tableAnnotaion != null && tableAnnotaion.name().length() > 0) {
            tableName = tableAnnotaion.name();
        } else {
            tableName = entity.getSimpleName();
        }

        for (NameValuePair pair : pairs) {
            if (pair.value == null) {
                continue;
            }
            whereClauses.add(pair.name + opMap.get(pair.op) + "?");
            Object value;
            if(pair.op.equals(Operators.LIKE) && pair.value instanceof String) {
                value = "%"+(pair.value).toString()+"%";
            } else {
                value = pair.value;
            }
            values.add(value);
        }

        // Get sql
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        sql.append(" WHERE ");
        for (String whereClause : whereClauses) {
            sql.append(whereClause);
            sql.append(" AND ");
        }
        sql.replace(sql.length() - 5, sql.length(), ";");
        return jdbcTemplate.query(sql.toString(), values.toArray(), rowMapperFactory.getRowMapper(entity));
    }

    @Transactional(rollbackFor = {})
    public List<UserEntity> queryUsers(String uid, String name, Short deptId, Short typeId) {
        NameValuePair[] pairs = new NameValuePair[4];
        pairs[0] = new NameValuePair("user_id", uid, Operators.LIKE);
        pairs[1] = new NameValuePair("user_name", name, Operators.LIKE);
        pairs[2] = new NameValuePair("department_id", deptId, Operators.EQ);
        pairs[3] = new NameValuePair("group_id", typeId, Operators.EQ);
        List<UserEntity> res = query(UserEntity.class, pairs);
        return query(UserEntity.class, pairs);
    }

    public List<CourseEntity> queryCourses(String cid, String name, Short deptId) {
        NameValuePair[] pairs = new NameValuePair[3];
        pairs[0] = new NameValuePair("id", cid, Operators.LIKE);
        pairs[1] = new NameValuePair("name", name, Operators.LIKE);
        pairs[2] = new NameValuePair("department_id", deptId, Operators.EQ);
        return query(CourseEntity.class, pairs);
    }
}

class RowMapperFactory {
    private Map<Class, RowMapper> factory = new HashMap<>();

    class EntityRowMapper<T> implements RowMapper<T> {
        private Class<T> entityClass;
        private Map<String, String> nameMap = new HashMap<>();
        private Set<String> foreignKeys = new HashSet<>();

        EntityRowMapper(Class<T> entityClass) {
            this.entityClass = entityClass;
            Field[] fields = entityClass.getDeclaredFields();
            for(Field field : fields) {
                if(field.getType().equals(Set.class) || field.getType().equals(List.class)) {
                    continue;
                }
                String fName = field.getName();
                String cName = fName;
                Column column = field.getAnnotation(Column.class);
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                if(column == null && joinColumn == null) {
                    String getterName = "get"+fName.substring(0, 1).toUpperCase()+fName.substring(1, fName.length());
                    try {
                        Method getter = entityClass.getMethod(getterName);
                        column = getter.getAnnotation(Column.class);
                        joinColumn = getter.getAnnotation(JoinColumn.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(column != null && column.name().length() > 0) {
                    cName = column.name();
                } else if(joinColumn != null && joinColumn.name().length() > 0){
                    cName = joinColumn.name();
                    foreignKeys.add(cName);
                }
                nameMap.put(fName, cName);
            }
        }

        @Nullable
        @Override
        public T mapRow(ResultSet rs, int rowNum) throws SQLException {
            T entity = null;
            try {
                entity = entityClass.newInstance();
                Field[] fields = entityClass.getDeclaredFields();
                for(Field field : fields) {
                    String cName = nameMap.get(field.getName());
                    if(cName != null && !foreignKeys.contains(cName)) {
                        field.setAccessible(true);
                        try {
                            field.set(entity, rs.getObject(cName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return entity;
        }
    }

    public <T> RowMapper<T> getRowMapper(Class<T> entity) {
        RowMapper<T> rowMapper = factory.get(entity);
        if (rowMapper == null) {
            rowMapper = new EntityRowMapper<>(entity);
            factory.put(entity, rowMapper);
        }
        return rowMapper;
    }

}


