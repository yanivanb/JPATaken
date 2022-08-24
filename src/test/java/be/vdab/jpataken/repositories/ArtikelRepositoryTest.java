package be.vdab.jpataken.repositories;

import be.vdab.jpataken.domain.Artikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(showSql = false)
@Import(ArtikelRepository.class)
class ArtikelRepositoryTest  extends AbstractTransactionalJUnit4SpringContextTests {

    private final ArtikelRepository repository;

    private static final String ARTIKELS = "artikels";

    private Artikel artikel;

    ArtikelRepositoryTest(ArtikelRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("KeukenPapier", BigDecimal.TEN, BigDecimal.valueOf(20));
    }

    @Test
    void findById() {
        assertThat(repository.findById(1)).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("appel"));
    }

    @Test
    void create() {
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }
}