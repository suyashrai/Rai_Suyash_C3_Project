import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantTest {

    Restaurant restaurant;
    
    @BeforeAll
    public void BeforeAll(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = Mockito.spy(new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
      
        LocalTime mockedTime = LocalTime.parse("12:00:00");
        Mockito.doReturn(mockedTime).when(restaurant).getCurrentTime();
        assertEquals(true, restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
       
        LocalTime mockedTime = LocalTime.parse("01:00:00");

        Mockito.doReturn(mockedTime).when(restaurant).getCurrentTime();
        assertEquals(false, restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
      
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
      
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
       
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

     //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

     @Test
    public void should_return_order_total_for_selected_items() {
        List<String> order = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        assertEquals(388, restaurant.getOrderTotal(order));
    }

    @Test
    public void should_return_zero_when_nothing_is_selected() {
        List<String> order = Arrays.asList();
        assertEquals(0, restaurant.getOrderTotal(order));
    }
     //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}
