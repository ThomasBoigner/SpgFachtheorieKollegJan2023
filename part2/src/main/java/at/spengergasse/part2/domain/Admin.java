package at.spengergasse.part2.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Admin extends AbstractPersistable<Long> {
    private String firstname;
    private String lastname;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "responsibleAdmin")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Customer> customers = new ArrayList<>(5);
}
