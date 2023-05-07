package at.spengergasse.part1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_premium")
@PrimaryKeyJoinColumn(name = "premiumId")
public class UserPremium extends User {
    private double amountPayed;
    private Period subscriptionLength;
}
