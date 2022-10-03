package be.vdab.jpataken.domain;

import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "artikels")
@DiscriminatorColumn(name = "soort")
public abstract class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private BigDecimal aankoopprijs;
    private BigDecimal verkoopprijs;

    @ElementCollection
    @CollectionTable(name = "kortingen",
            joinColumns = @JoinColumn(name = "artikelId"))
    @OrderBy("vanafAantal")
    private Set<Korting> kortingen;

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
        this.kortingen = new LinkedHashSet<>();
    }

    protected Artikel(){}

    public long getId() {
        return id;
    }

    public Set<Korting> getKortingen() {
        return Collections.unmodifiableSet(kortingen);
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public BigDecimal getVerkoopprijs() {
        return verkoopprijs;
    }

    public void verhoogVerkoopPrijs(BigDecimal bedrag) {
        if (bedrag.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException();
        }
        verkoopprijs = verkoopprijs.add(bedrag);
    }
}
