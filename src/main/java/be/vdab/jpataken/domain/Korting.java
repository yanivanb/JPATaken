package be.vdab.jpataken.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Korting {
    private int vanafAantal;
    private double percentage;

    protected Korting(){}

    public Korting(int vanafAantal, double percentage) {
        this.vanafAantal = vanafAantal;
        this.percentage = percentage;
    }

    public int getVanafAantal() {
        return vanafAantal;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public int hashCode() {
        return vanafAantal;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Korting korting &&
                vanafAantal == korting.vanafAantal;
    }
}
