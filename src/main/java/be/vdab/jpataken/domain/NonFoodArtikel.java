package be.vdab.jpataken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "artikels")
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel{
    private int garantie;

    protected NonFoodArtikel(){}

    public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int garantie) {
        super(naam, aankoopprijs, verkoopprijs);
        this.garantie = garantie;
    }

    public int getGarantie() {
        return garantie;
    }
}
