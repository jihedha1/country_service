package testApplication;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculTest {

    Calcul c;

    @BeforeAll
    static void setUp() {
    }

    @AfterAll
    static void tearDown() {
    }

    @BeforeEach
    void setUpEach() {
        c = new Calcul();
    }

    @AfterEach
    void tearDownEach() {}

    @Test
    void testsomme() {
        assertEquals(8,c.somme(5,3));
        assertEquals(3,c.somme(0,3));
        assertTrue(15== c.somme(10,5));
    }

    @Test
    void testdivision() {
        assertEquals(1,c.division(5,3));
        assertEquals(5,c.division(15,3));
    }
}