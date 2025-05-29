package com.primeng.primeng.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeng.primeng.exceptions.AppException;
import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.models.db.*;
import com.primeng.primeng.util.Fecha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DBRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public <T> Result<T> findAll(Class<T> cls, Query query) {
        Result<T> result = new Result<>();
        Pagination pagination = query.getPagination();
        long totalItems = count(cls, query);
        int totalPages = 1;
        if (!query.isDisabledPagination()) {
            totalPages = (int) Math.ceil((double) totalItems / pagination.getPerPage());
        }
        pagination.setTotalItems(totalItems);
        pagination.setTotalPages(totalPages);
        if (pagination.getCurrentPage() > 1) {
            pagination.setPrevPage(pagination.getCurrentPage() - 1);
        }
        if (pagination.getCurrentPage() < totalPages) {
            pagination.setNextPage(pagination.getCurrentPage() + 1);
        }
        result.setPagination(pagination);
        if (pagination.getCurrentPage() > totalPages) {
            return result;
        }
        // Data
        result.setData(find(cls, query));
        return result;
    }

    public <T> Long count(Class<T> cls, Query query) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<T> root = cr.from(cls);
        cr.select(cb.count(root));
        // Where
        Predicate predicate = where(query, cb, root);
        cr.where(predicate);
        // Result
        return entityManager.createQuery(cr).getSingleResult();
    }

    private <T> List<T> find(Class<T> cls, Query query) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(cls);
        Root<T> root = cr.from(cls);
        // Where
        Predicate predicate = where(query, cb, root);
        cr.where(predicate);
        // Order By
        List<Order> orderBy = new ArrayList<>();
        for (Sort sort : query.getSorter()) {
            switch (sort.getOrder().toLowerCase()) {
                case "asc" -> orderBy.add(cb.asc(root.get(sort.getField())));
                case "desc" -> orderBy.add(cb.desc(root.get(sort.getField())));
                default -> throw new BadRequestException("Order no valido. " + sort);
            }
        }
        cr.orderBy(orderBy);
        // Result
        if (query.isDisabledPagination()) {
            return entityManager.createQuery(cr).getResultList();
        } else {
            return entityManager.createQuery(cr) //
                    .setFirstResult((query.getPagination().getCurrentPage() - 1) * query.getPagination().getPerPage()) // Offset
                    .setMaxResults(query.getPagination().getPerPage()) // Limit
                    .getResultList();
        }
    }

    private <T> Predicate where(Query query, CriteriaBuilder cb, Root<T> root) {
        Predicate predicate = cb.conjunction();
        for (Filter filter : query.getFilters()) {
            String type = filter.getType();
            String field = filter.getField();
            String operator = filter.getOperator();
            String value = filter.getValue();
            try {
                switch (type) {
                    // STRING
                    case "string" -> {
                        String literal = operator.replace("_", "%");
                        switch (operator) {
                            case "lk", "lk_", "_lk", "_lk_" -> literal = literal.replace("lk", value);
                            case "nlk", "nlk_", "_nlk", "_nlk_" -> literal = literal.replace("nlk", value);
                            default -> throw new BadRequestException("Operator no valido. " + filter);
                        }
                        Expression<String> keyUnaccent = cb.function("unaccent", String.class, root.get(field));
                        Expression<String> keyUpper = cb.function("upper", String.class, keyUnaccent);
                        Expression<String> valUnaccent = cb.function("unaccent", String.class, cb.literal(literal));
                        Expression<String> valUpper = cb.function("upper", String.class, valUnaccent);
                        if (operator.contains("nlk")) {
                            predicate = cb.and(predicate, cb.notLike(keyUpper, valUpper));
                        } else {
                            predicate = cb.and(predicate, cb.like(keyUpper, valUpper));
                        }
                    }
                    // NUMBER
                    case "number" -> {
                        if (!EsNumero(value)) {
                            throw new BadRequestException("Value no valido: Número requerido. " + filter);
                        }
                        Integer numero = Integer.parseInt(value);
                        switch (operator) {
                            case "=" -> predicate = cb.and(predicate, cb.equal(root.get(field), numero));
                            case "<" -> predicate = cb.and(predicate, cb.lessThan(root.get(field), numero));
                            case ">" -> predicate = cb.and(predicate, cb.greaterThan(root.get(field), numero));
                            case "<=" -> predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(field), numero));
                            case ">=" ->
                                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(field), numero));
                            case "!=" -> predicate = cb.and(predicate, cb.notEqual(root.get(field), numero));
                            default -> throw new BadRequestException("Operator no valido. " + filter);
                        }
                    }
                    // DATE
                    case "date" -> {
                        Date fecha = value.length() == 10 ? Fecha.fromIsoDate(value) : Fecha.fromIsoDateTime(value);
                        switch (operator) {
                            case "=" -> predicate = cb.and(predicate, cb.equal(root.get(field), fecha));
                            case "<" -> predicate = cb.and(predicate, cb.lessThan(root.get(field), fecha));
                            case ">" -> predicate = cb.and(predicate, cb.greaterThan(root.get(field), fecha));
                            case "<=" -> predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(field), fecha));
                            case ">=" -> predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(field), fecha));
                            case "!=" -> predicate = cb.and(predicate, cb.notEqual(root.get(field), fecha));
                            default -> throw new BadRequestException("Operator no valido. " + filter);
                        }
                    }
                    // TIME
                    case "time" -> {
                        LocalTime hora = LocalTime.parse(value);
                        switch (operator) {
                            case "=" -> predicate = cb.and(predicate, cb.equal(root.get(field), hora));
                            case "<" -> predicate = cb.and(predicate, cb.lessThan(root.get(field), hora));
                            case ">" -> predicate = cb.and(predicate, cb.greaterThan(root.get(field), hora));
                            case "<=" -> predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(field), hora));
                            case ">=" -> predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(field), hora));
                            case "!=" -> predicate = cb.and(predicate, cb.notEqual(root.get(field), hora));
                            default -> throw new BadRequestException("Operator no válido. " + filter);
                        }
                    }
                    // BOOLEAN
                    case "boolean" -> {
                        if (operator.equals("=")) {
                            if (value.equals("true") || value.equals("false")) {
                                predicate = cb.and(predicate, cb.equal(root.get(field), Boolean.parseBoolean(value)));
                            } else {
                                throw new BadRequestException("Value no valido: Boleano requerido. " + filter);
                            }
                        } else {
                            throw new BadRequestException("Operator no valido. " + filter);
                        }
                    }
                    // IS NULL
                    case "isnull" -> {
                        if (operator.equals("=")) {
                            switch (value) {
                                case "true" -> predicate = cb.and(predicate, cb.isNull(root.get(field)));
                                case "false" -> predicate = cb.and(predicate, cb.isNotNull(root.get(field)));
                                default ->
                                        throw new BadRequestException("Value no valido: Boleano requerido. " + filter);
                            }
                        } else {
                            throw new BadRequestException("Operator no valido. " + filter);
                        }
                    }
                    // LIST
                    case "list" -> {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            Object[] values = mapper.readValue(value, Object[].class);
                            switch (operator) {
                                case "in" -> predicate = cb.and(predicate, root.get(field).in(values));
                                case "notin" -> predicate = cb.and(predicate, root.get(field).in(values).not());
                                default -> throw new BadRequestException("Operator no valido. " + filter);
                            }
                        } catch (JsonProcessingException e) {
                            throw new AppException(e.getMessage());
                        }
                    }
                    default -> throw new BadRequestException("Type no valido. " + filter);
                }
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Field no valida. " + filter);
            } catch (Exception e) {
                throw new BadRequestException("Valores no validos. " + filter);
            }
        }
        return predicate;
    }

    public boolean EsNumero(String var) {
        if (var == null || var.isEmpty()) {
            return false; // O true, dependiendo de si un string vacío/nulo debe ser considerado válido
        }
        try {
            Integer.parseInt(var); // Intenta parsear a un entero
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
