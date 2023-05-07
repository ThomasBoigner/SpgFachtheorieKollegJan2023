package at.spengergasse.part1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "podcast_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AbstractPersistable<Long> {
    @NotNull
    @NotBlank
    private String firstname;
    @NotNull
    @NotBlank
    private String lastname;
    @NotNull
    @NotBlank
    private String email;

    @Past
    private LocalDate subscriptionStart;
    @Past
    private LocalDate subscriptionEnd;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Rating> ratings = new ArrayList<>(5);

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Favorite> favorites = new ArrayList<>(5);

    @Version
    private Integer version;

    @CreationTimestamp
    private LocalDateTime creationTS;
    @UpdateTimestamp
    private LocalDateTime updateTS;
}
