package at.spengergasse.part1.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "radio_station")
public class RadioStation extends AbstractPersistable<Long> {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String address;
    private boolean online;
    private boolean offline;
    @NotBlank
    private String link;
    @NotBlank
    private double frequency;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "radioStation")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Podcast> podcasts;
}
