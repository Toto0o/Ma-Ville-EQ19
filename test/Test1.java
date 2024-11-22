package prototype.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import prototype.Quartier.Quartier;

public class Test1 {

    private Quartier quartier;

    public Test1() {
        this.quartier = new Quartier("6587 de normanville");
    }

    @Test
    public void quartierTest1() {
        //Test if method to find quartier works
        String expected = "Rosemont";
        assertEquals(expected, quartier.getQuartierFromAddress("6587 de Normanville"));
    }

    @Test
    public void quartierTest2() {
        //Test if it return expected quartier
        String expected = "Rosemont";
        assertEquals(expected, quartier.getName());
    }

    @Test
    public void quartierTest3() {
        //Test if name setter works
        String name = "Outremont";
        quartier.setName(name);
        assertEquals(name, quartier.getName());
    }

}
