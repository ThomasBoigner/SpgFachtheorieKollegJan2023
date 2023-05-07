package at.spengergasse.part1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "podcast")
public class Podcast extends AbstractPersistable<Long> {
    @NotNull
    @NotBlank
    private String title;
    private Duration duration;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "podcast")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Rating> ratings = new ArrayList<>(5);

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "fk_podcast_category")
    private Category category;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "fk_podcast_radio_station")
    private RadioStation radioStation;
}
