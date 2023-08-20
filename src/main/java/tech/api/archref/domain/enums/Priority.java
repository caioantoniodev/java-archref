package tech.api.archref.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Priority {
    NONE("1"),
    LOW("2"),
    MEDIUM("3"),
    HIGH("4");

    private final String value;

    public static Optional<Priority> fromValue(String value) {
        return Stream.of(values())
                .filter(type -> type.value.equals(value))
                .findFirst();
    }

    public static Optional<Priority> fromName(String name) {
        return Stream.of(values())
                .filter(type -> type.name().equals(name))
                .findFirst();
    }

}
