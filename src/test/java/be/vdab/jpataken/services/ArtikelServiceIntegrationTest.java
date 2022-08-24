package be.vdab.jpataken.services;

import be.vdab.jpataken.repositories.ArtikelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import({ ArtikelService.class, ArtikelRepository.class })
public class ArtikelServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String ARTIKELS = "Artikels";
    private final ArtikelService service;
    private final EntityManager manager;

    public ArtikelServiceIntegrationTest(ArtikelService service, EntityManager manager) {
        this.service = service;
        this.manager = manager;
    }

    private long idVanTestArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'appel'", Long.class);
    }

    @Test
    void verhoogVerkoopPrijsVoorOnbestaandArtikel(){
        var id = idVanTestArtikel();
        service.verhoogVerkoopPrijs(id, BigDecimal.TEN);
        manager.flush();
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 11.90 and id = " + id)).isOne();

    }
}
