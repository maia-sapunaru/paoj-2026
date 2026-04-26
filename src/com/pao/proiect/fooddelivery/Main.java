package com.pao.proiect.fooddelivery;

import com.pao.proiect.fooddelivery.exception.NotFoundException;
import com.pao.proiect.fooddelivery.exception.MenuItemUnavailableException;
import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.DeliveryAddress;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.Order;
import com.pao.proiect.fooddelivery.model.OrderItem;
import com.pao.proiect.fooddelivery.model.OrderStatus;
import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.model.RestaurantCategory;
import com.pao.proiect.fooddelivery.service.OrderService;
import com.pao.proiect.fooddelivery.service.RestaurantService;
import com.pao.proiect.fooddelivery.service.UserService;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        RestaurantService restaurantService = RestaurantService.getInstance();
        OrderService orderService = OrderService.getInstance();

        Client client1 = new Client(
                1,
                "Ana Popescu",
                "0711111111",
                new DeliveryAddress("Bucuresti", "Strada Lalelelor", "10", "Ap. 5")
        );

        Client client2 = new Client(
                2,
                "Mihai Ionescu",
                "0722222222",
                new DeliveryAddress("Bucuresti", "Strada Libertatii", "25", "Et. 2")
        );

        Driver driver1 = new Driver(1, "George Marin", "0733333333", "B123XYZ", true, 4.8);
        Driver driver2 = new Driver(2, "Radu Pavel", "0744444444", "B456ABC", true, 4.6);

        Restaurant restaurant1 = new Restaurant(1, "Pizza Fast", "Bd. Unirii 10", RestaurantCategory.PIZZA);
        Restaurant restaurant2 = new Restaurant(2, "Asian Bowl", "Calea Victoriei 20", RestaurantCategory.ASIAN);

        MenuItem item1 = new MenuItem(1, "Margherita", 30.0, true, 1);
        MenuItem item2 = new MenuItem(2, "Diavola", 35.0, true, 1);
        MenuItem item3 = new MenuItem(3, "Sushi Set", 55.0, true, 2);
        MenuItem item4 = new MenuItem(4, "Noodles", 40.0, false, 2);

        try {
            System.out.println("1. Adauga un restaurant nou");
            restaurantService.addRestaurant(restaurant1);
            restaurantService.addRestaurant(restaurant2);
            System.out.println("Restaurante adaugate cu succes.\n");

            System.out.println("2. Adauga un client nou");
            userService.addClient(client1);
            userService.addClient(client2);
            System.out.println("Clienti adaugati cu succes.\n");

            System.out.println("3. Adauga un sofer nou");
            userService.addDriver(driver1);
            userService.addDriver(driver2);
            System.out.println("Soferi adaugati cu succes.\n");

            System.out.println("4. Adauga un produs in meniul unui restaurant");
            restaurantService.addMenuItemToRestaurant(1, item1);
            restaurantService.addMenuItemToRestaurant(1, item2);
            restaurantService.addMenuItemToRestaurant(2, item3);
            restaurantService.addMenuItemToRestaurant(2, item4);
            System.out.println("Produse adaugate in meniuri.\n");

            System.out.println("5. Afiseaza toate restaurantele");
            for (Restaurant restaurant : restaurantService.getAllRestaurants()) {
                System.out.println(restaurant);
            }
            System.out.println();

            System.out.println("6. Cauta produse dupa nume");
            List<MenuItem> foundItems = restaurantService.searchMenuItemsByName("a");
            for (MenuItem item : foundItems) {
                System.out.println(item);
            }
            System.out.println();

            System.out.println("7. Plaseaza o comanda");
            Order order1 = orderService.placeOrder(
                    1,
                    client1,
                    restaurant1,
                    Arrays.asList(
                            new OrderItem(item1, 2),
                            new OrderItem(item2, 1)
                    )
            );
            System.out.println(order1);
            System.out.println();

            System.out.println("8. Atribuie un sofer unei comenzi");
            orderService.assignDriver(1, driver1);
            System.out.println(orderService.findOrderById(1));
            System.out.println();

            System.out.println("9. Afiseaza toate comenzile unui client");
            Order order2 = orderService.placeOrder(
                    2,
                    client1,
                    restaurant2,
                    List.of(new OrderItem(item3, 1))
            );

            List<Order> clientOrders = orderService.getOrdersByClient(client1.getId());
            for (Order order : clientOrders) {
                System.out.println(order);
            }
            System.out.println();

            System.out.println("10. Afiseaza comenzile active sortate dupa valoare totala");
            for (Order order : orderService.getActiveOrdersSortedByTotal()) {
                System.out.println(order);
            }
            System.out.println();

            System.out.println("Produse sortate alfabetic");
            for (MenuItem item : restaurantService.getSortedMenuItems()) {
                System.out.println(item);
            }
            System.out.println();

            System.out.println("Demonstratie exceptie custom:");
            orderService.placeOrder(
                    3,
                    client2,
                    restaurant2,
                    List.of(new OrderItem(item4, 1))
            );

        } catch (MenuItemUnavailableException | NotFoundException | IllegalArgumentException e) {
            System.out.println("Eroare: " + e.getMessage());
        }


        try {
            restaurantService.findRestaurantById(999);
        } catch (NotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        System.out.println("\nActualizare status comanda 1 la DELIVERED");
        orderService.updateOrderStatus(1, OrderStatus.DELIVERED);
        System.out.println(orderService.findOrderById(1));
    }
}