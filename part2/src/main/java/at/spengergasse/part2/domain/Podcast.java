package at.spengergasse.part2.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
public class Podcast extends Item {
    @ElementCollection
    private List<Integer> positionForAdd;
    private int maxQuantityAds;
}
