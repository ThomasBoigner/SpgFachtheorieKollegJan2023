package at.spengergasse.part1.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "category")
public class Category extends AbstractPersistable<Long> {
    private boolean top;
    private boolean onlyPremium;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "category")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Podcast> podcast;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "category")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Favorite> favorites = new ArrayList<>(5);
}
