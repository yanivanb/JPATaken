package be.vdab.jpataken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "artikels")
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {
    private int houdbaarheid;

    protected FoodArtikel(){}

    public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int houdbaarheid) {
        super(naam, aankoopprijs, verkoopprijs);
        this.houdbaarheid = houdbaarheid;
    }

    public int getHoudbaarheid() {
        return houdbaarheid;
    }
}
