package Z1.CarTest;

import Z1.Car.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CarTest {
    private Car myFerrari = mock(Car.class);

    @Test
    public void test_instance_car(){
        assertTrue(myFerrari instanceof Car);
    }

    @Test
    public void test_default_behavior_needsFuel(){
        assertFalse(myFerrari.needsFuel(), "New test double should return False as boolean");
    }

    @Test
    public void test_default_behavior_temperature(){
        assertEquals(0.0, myFerrari.getEngineTemperature(), "New test double should return 0.0");
    }

    @Test
    public void test_stubbing_mock(){
        when(myFerrari.needsFuel()).thenReturn(true);
        assertTrue(myFerrari.needsFuel());
    }

    @Test
    public void test_exception(){
        when(myFerrari.needsFuel()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> {
            myFerrari.needsFuel();
        });
    }

    @Test
    public void testVerification(){
        myFerrari.driveTo("Kartuzy");
        myFerrari.needsFuel();

        verify(myFerrari).driveTo("Kartuzy");
        verify(myFerrari).needsFuel();
        assertFalse(myFerrari.needsFuel());
    }


    @Test
    public void overwritting_stub(){

        when(myFerrari.needsFuel()).thenReturn(true);
        when(myFerrari.needsFuel()).thenReturn(false);
        myFerrari.needsFuel();

        verify(myFerrari).needsFuel();

        assertFalse(myFerrari.needsFuel());

    }

    @Test
    public void never_used_stub(){


        when(myFerrari.getEngineTemperature()).thenReturn(10.0);

        verify(myFerrari, never()).getEngineTemperature();

    }


    @Test
    public void few_times_used_stub(){


        when(myFerrari.getEngineTemperature()).thenReturn(10.0);

        myFerrari.getEngineTemperature();
        myFerrari.getEngineTemperature();
        myFerrari.getEngineTemperature();


        verify(myFerrari, times(3)).getEngineTemperature();

    }



    @Test
    public void answer_joy(){

        when(myFerrari.getEngineTemperature()).thenReturn(50.0);
        when(myFerrari.needsFuel()).thenAnswer(invocationOnMock -> {

            if (myFerrari.getEngineTemperature() > 50) {
                return false;
            }
            else {
                return true;
            }

        });

        myFerrari.needsFuel();

        verify(myFerrari, times(1)).getEngineTemperature();
        verify(myFerrari).needsFuel();

        assertTrue(myFerrari.needsFuel());

    }



}