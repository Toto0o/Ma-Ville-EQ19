package protoype.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import prototype.Requests.Request;

public class Test2 {

    private Request request;

    public Test2() {
        request = new Request("titre", "description", null, "today", null, null);
    }

    @Test
    public void requestTest1() {
        assertEquals("titre", request.getTitle());
    }

    @Test
    public void requestTest2() {
        assertEquals("description", request.getDescription());
    }

    @Test
    public void requestTest3() {
        assertEquals("today", request.getDate());
    }

}
