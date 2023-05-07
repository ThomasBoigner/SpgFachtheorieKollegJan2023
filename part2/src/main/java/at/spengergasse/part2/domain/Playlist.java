package at.spengergasse.part2.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Playlist extends AbstractPersistable<Long> {
    private String name;
    private String userName;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "playlist")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ListenedItem> listenedItems;
}
