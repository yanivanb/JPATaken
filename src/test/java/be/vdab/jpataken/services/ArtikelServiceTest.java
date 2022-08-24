package be.vdab.jpataken.services;

import be.vdab.jpataken.domain.Artikel;
import be.vdab.jpataken.exceptions.ArtikelNietGevondenException;
import be.vdab.jpataken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class ArtikelServiceTest {

    private ArtikelService service;

    @Mock
    private ArtikelRepository repository;

    private Artikel artikel;

    @BeforeEach
    void beforeEach(){
        service = new ArtikelService(repository);
        artikel = new Artikel("KeukenPapier", BigDecimal.TEN, BigDecimal.valueOf(20));
    }

    @Test
    void verhoogVerkoopPrijs() {
        when(repository.findById(1)).thenReturn(Optional.of(artikel));
        service.verhoogVerkoopPrijs(1, BigDecimal.TEN);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("30");
        verify(repository).findById(1);
    }

    @Test
    void verhoogVerkoopPrijsVoorOnbestaandArtikel() {
        assertThatExceptionOfType(ArtikelNietGevondenException.class).isThrownBy(
                () -> service.verhoogVerkoopPrijs(-1, BigDecimal.TEN));
        verify(repository).findById(-1);
    }
}