package be.vdab.jpataken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.*;

class ArtikelTest {

    private Artikel artikel1;
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;

    @BeforeEach
    void beforeEach() {
        //String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int houdbaarheid
        artikelGroep1 = new ArtikelGroep("test");
        artikel1 = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, 2, artikelGroep1);
        artikelGroep2 = new ArtikelGroep("test2");
    }

    @Test
    void artikel1KomtVoorInArtikelGroep1() {
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).contains(artikel1);
    }
    @Test
    void artikel11VerhuistVanArtikelGroep1NaarArtikelGroep2() {
        artikel1.setArtikelGroep(artikelGroep2);
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep2);
        assertThat(artikelGroep1.getArtikels()).doesNotContain(artikel1);
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel1);
    }
    @Test
    void eenNullArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikel1.setArtikelGroep(null));
    }

}