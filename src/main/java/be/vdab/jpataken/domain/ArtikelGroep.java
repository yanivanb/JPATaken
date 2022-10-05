package be.vdab.jpataken.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "artikelgroepen")
public class ArtikelGroep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artikelGroep")
    @OrderBy("naam")
    private Set<Artikel> artikels;

    protected ArtikelGroep(){}

    public ArtikelGroep(String naam) {
        this.naam = naam;
        this.artikels = new LinkedHashSet<>();
    }

    public Set<Artikel> getArtikels() {
        return Collections.unmodifiableSet(artikels);
    }

    public String getNaam() {
        return naam;
    }

    public long getId() {
        return id;
    }

    public boolean add(Artikel artikel) {
        var toegevoegd = artikels.add(artikel);
        var oudArtikelGroep = artikel.getArtikelGroep();
        if (oudArtikelGroep != null && oudArtikelGroep != this) {
            oudArtikelGroep.artikels.remove(artikel);
        }
        if (this != oudArtikelGroep) {
            artikel.setArtikelGroep(this);
        }
        return toegevoegd;
    }
}
