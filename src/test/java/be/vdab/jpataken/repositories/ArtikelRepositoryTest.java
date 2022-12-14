package be.vdab.jpataken.repositories;

import be.vdab.jpataken.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(showSql = false)
@Import(ArtikelRepository.class)
@Sql("/ArtikelInsert.sql")
class ArtikelRepositoryTest  extends AbstractTransactionalJUnit4SpringContextTests {

    private final ArtikelRepository repository;

    private final EntityManager manager;
    private static final String ARTIKELS = "artikels";

    private Artikel artikel;
    private ArtikelGroep artikelGroep1;

    public ArtikelRepositoryTest(ArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }
    private long idVanArtikelFood(){
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testfood'",
                long.class);
    }

    private long idVanArtikelNonFood(){
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testnonfood'",
                long.class);
    }

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikel = new NonFoodArtikel("KeukenPapier", BigDecimal.TEN, BigDecimal.valueOf(20), 1, artikelGroep1);
    }

    @Test
    void findById() {
        assertThat(repository.findById(1)).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("appel"));
    }

    @Test
    void create() {
        manager.persist(artikelGroep1);
        repository.create(artikel);
        manager.flush();
        assertThat(artikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void findByWoord(){
        var artikels = repository.findByNaamContains("pp");
        manager.clear();
        assertThat(artikels)
                .hasSize(countRowsInTableWhere(ARTIKELS, "naam like '%"+"pp%'"))
                .extracting(Artikel::getNaam)
                .allSatisfy(naam -> assertThat(naam).containsIgnoringCase("pp"));
                assertThat(artikels)
                .extracting(Artikel::getArtikelGroep)
                .extracting(ArtikelGroep::getNaam)
                .isNotNull();
    }

    @Test
    void artikelVerkoopOpslag(){
        assertThat(repository.artikelVerkoopOpslag(BigDecimal.TEN))
                .isEqualTo(countRowsInTable(ARTIKELS));
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 2.09 and id = 1")).isOne();
    }

    @Test
    void nonFoodArtikelId(){
        assertThat(repository.findById(idVanArtikelNonFood())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testnonfood"));
    }

    @Test
    void artikelFoodId(){
        assertThat(repository.findById(idVanArtikelFood())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testfood"));
    }

    @Test
    void createFoodArtikel() {
        var artikel = new FoodArtikel("FoodArtikel2", BigDecimal.ONE, BigDecimal.TEN, 6, artikelGroep1);
        manager.persist(artikelGroep1);
        repository.create(artikel);
        manager.flush();
        assertThat(countRowsInTableWhere(ARTIKELS,
                "id = " + artikel.getId())).isOne();
    }

    @Test
    void createNonFoodArtikel() {
        var artikel = new NonFoodArtikel("NonFoodArtikel2", BigDecimal.ONE, BigDecimal.TEN, 3, artikelGroep1);
        manager.persist(artikelGroep1);
        repository.create(artikel);
        manager.flush();
        assertThat(countRowsInTableWhere(ARTIKELS,
                "id = " + artikel.getId())).isOne();
    }

    @Test
    void kortingenLezen() {
        assertThat(repository.findById(idVanArtikelFood()))
                .hasValueSatisfying(
                        artikel -> assertThat(artikel.getKortingen())
                                .containsOnly(new Korting(1,10.00)));
    }

    @Test
    void artikelGroepLazyLoaded() {
        assertThat(repository.findById(idVanArtikelNonFood())).hasValueSatisfying(
                artikel -> assertThat(artikel.getArtikelGroep().getNaam()).isEqualTo("groente"));
    }

}