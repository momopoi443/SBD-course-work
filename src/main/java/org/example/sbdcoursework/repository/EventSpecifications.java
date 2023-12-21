package org.example.sbdcoursework.repository;

import org.example.sbdcoursework.entity.event.Event;
import org.example.sbdcoursework.entity.event.EventType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EventSpecifications {

    public static Specification<Event> hasCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<Event> inDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get("date"),
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );
    }

    public static Specification<Event> hasTypes(List<EventType> types) {
        return (root, query, criteriaBuilder) -> root.get("type").in(types);
    }

    public static Specification<Event> hasOrganizerId(UUID organizerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("organizerId"), organizerId);
    }
}