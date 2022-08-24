package be.vdab.jpataken.services;

import be.vdab.jpataken.exceptions.ArtikelNietGevondenException;
import be.vdab.jpataken.repositories.ArtikelRepository;

import java.math.BigDecimal;

public class ArtikelService {
    private final ArtikelRepository artikelRepository;

    public ArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    public void verhoogVerkoopPrijs(long id, BigDecimal bedrag) {
        artikelRepository.findById(id).orElseThrow(ArtikelNietGevondenException::new).verhoogVerkoopPrijs(bedrag);
    }
}
