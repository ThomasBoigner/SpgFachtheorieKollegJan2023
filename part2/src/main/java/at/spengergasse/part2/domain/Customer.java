package at.spengergasse.part2.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Customer extends AbstractPersistable<Long> {
    private String firstname;
    private String lastname;
    private String companyName;
    private double totalCost;
    @CreationTimestamp
    private LocalDateTime registrationDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fk_customer_admin")
    private Admin responsibleAdmin;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "customer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Advertisement> advertisements;
}
