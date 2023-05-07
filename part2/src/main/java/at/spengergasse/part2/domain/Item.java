package at.spengergasse.part2.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    private Long id;
    private LocalDateTime production;
    private int length;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "item")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ListenedItem> listenedItems;
}