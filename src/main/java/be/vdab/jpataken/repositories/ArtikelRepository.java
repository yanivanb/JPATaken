package be.vdab.jpataken.repositories;

import be.vdab.jpataken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class ArtikelRepository {
    private final EntityManager manager;


    public ArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Optional<Artikel> findById(long id){
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }

    public void create(Artikel artikel) {
        manager.persist(artikel);
    }
}
