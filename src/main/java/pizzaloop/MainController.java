package pizzaloop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * Created  by S.M.D.V.DHANANJAYA
 */
@Controller
@RequestMapping(path="/pizzaApp")
public class MainController {
    private static final String SUCCESS= "Saved";

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    /*
    * READ Operation
    * This method will list all the pizzas in the table
    * URI to access this: http://localhost:8080/pizzaApp/pizzaAll
    */
    @GetMapping(path="/pizzaAll")
    public @ResponseBody Iterable<PizzaDetails> getPizzaDetails() {
        return pizzaRepository.findAll();
    }

    /*
     * READ Operation
     * This method will list all the cart item in the table
     * URI to access this: http://localhost:8080/pizzaApp/cartAll
     */
    @GetMapping(path="/cartAll")
    public @ResponseBody Iterable<CartDetails> getCartDetails() {
        return cartRepository.findAll();
    }

    /*
    * READ Operation based on Pizza ID
    * This method will return the details of a pizza specified by the ID
    * URI to access this: http://localhost:8080/pizzaApp/findByPizzaId?id=2
    */
    @GetMapping(path="/findByPizzaId")
    public @ResponseBody List<PizzaDetails> getPizzaById(@RequestParam Integer id) {
        return pizzaRepository.findByPizzaId(id);
    }

    /*
     * READ Operation based on User Email
     * This method will return User Details if user available
     * URI to access this: http://localhost:8080/pizzaApp/findByEmail?email=dvishwa2@gmail.com
     */
    @GetMapping(path="/findByEmail")
    public @ResponseBody List<UserDetails> getUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email);
    }

    /*
     * READ Operation based on User Password
     * This method will return User Details if user available
     * URI to access this: http://localhost:8080/pizzaApp/findByPassword?password=1234
     */
    @GetMapping(path="/findByPassword")
    public @ResponseBody List<UserDetails> getUserByPassword(@RequestParam String password) {
        return userRepository.findByPassword(password);
    }

    /*
    * CREATE Operation
    * This method will crate new pizza item in the database table
    * and returns the SUCCESS message
    * URI to access this: http://localhost:8080/pizzaApp/addPizza?name=VegiPizza&description=VegiSupreme&price=2500&imageUrl=image
    */
    @GetMapping(path="/addPizza")
    public @ResponseBody String addNewPizza(@RequestParam String name, @RequestParam String description, @RequestParam Double price, @RequestParam String imageUrl) {
        PizzaDetails pizzaDetails = new PizzaDetails();
        pizzaDetails.setName(name);
        pizzaDetails.setDescription(description);
        pizzaDetails.setPrice(price);
        pizzaDetails.setImageUrl(imageUrl);
        pizzaRepository.save(pizzaDetails);
        return SUCCESS;
    }

    /*
     * CREATE Operation
     * This method will crate new user in the database table
     * and returns the SUCCESS message
     * URI to access this: http://localhost:8080/pizzaApp/addUser?name=Vishwa&email=dvishwa2@gmail.com&phone=0772760429&password=1234
     */
    @GetMapping(path="/addUser")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email, @RequestParam long phone, @RequestParam String password) {
        UserDetails userDetails = new UserDetails();
        userDetails.setName(name);
        userDetails.setEmail(email);
        userDetails.setPhone(phone);
        userDetails.setPassword(password);
        userRepository.save(userDetails);
        return SUCCESS;
    }

    /*
     * CREATE Operation
     * This method will crate new cart item in the database table
     * and returns the SUCCESS message
     * URI to access this: http://localhost:8080/pizzaApp/addCart?pizzaName=VegitablePizza&pizzaImageUrl=image&pizzaPrice=678&pizzaQuantity=4
     */
    @GetMapping(path="/addCart")
    public @ResponseBody String addNewCart(@RequestParam String pizzaName, @RequestParam String pizzaImageUrl, @RequestParam float pizzaPrice, @RequestParam int pizzaQuantity) {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setPizzaName(pizzaName);
        cartDetails.setPizzaImageUrl(pizzaImageUrl);
        cartDetails.setPizzaPrice(pizzaPrice);
        cartDetails.setPizzaQuantity(pizzaQuantity);
        cartRepository.save(cartDetails);
        return SUCCESS;
    }

    /*
    * DELETE Operation
    * This method will delete existing pizza item by finding it using the ID
    * and returns the deleted item
    * URI to access this: http://localhost:8080/pizzaApp/deleteByPizzaId?id=2
    */
    @GetMapping(path="/deleteByPizzaId")
    public @ResponseBody List<PizzaDetails> deletePizzaById(@RequestParam Integer id) {
        return pizzaRepository.deleteByPizzaId(id);
    }

    /*
     * DELETE Operation
     * This method will delete existing cart item by finding it using the ID
     * and returns the deleted item
     * URI to access this: http://localhost:8080/pizzaApp/deleteByCartId?id=2
     */
    @GetMapping(path="/deleteByCartId")
    public @ResponseBody List<CartDetails> deleteCartById(@RequestParam Integer id) {
        return cartRepository.deleteByCartId(id);
    }

    /*
     * DELETE Operation
     * This method will delete all cart item
     * URI to access this: http://localhost:8080/pizzaApp/deleteCartAll
     */
    @GetMapping(path="/deleteCartAll")
    public @ResponseBody void deleteCartAll() {
        cartRepository.deleteAll();
    }

    /*
    * UPDATE Operation
    * This method will update existing pizza details by finding it using the ID
    * and returns the updated data
    * URI to access this: http://localhost:8080/pizzaApp/updatePizza?id=1&name=updatedname&description=updated&price=1234.56&imageUrl=image
    */
    @GetMapping(path="/updatePizza")
    public @ResponseBody List<PizzaDetails> updatePizzaDetails(@RequestParam Integer id, @RequestParam String name, @RequestParam String description, @RequestParam Double price ,@RequestParam String imageUrl) {
        //First get all the pizza details according to the provided ID
        List<PizzaDetails> pizzaDetailsList = pizzaRepository.findByPizzaId(id);
        if(!pizzaDetailsList.isEmpty()) {
            //Iterate through the pizza list
            for(PizzaDetails pizzaDetails: pizzaDetailsList) {
                //Set new values
                pizzaDetails.setName(name);
                pizzaDetails.setDescription(description);
                pizzaDetails.setPrice(price);
                pizzaDetails.setImageUrl(imageUrl);
                //Update existing pizza item
                pizzaRepository.save(pizzaDetails);
            }
        }
        return pizzaRepository.findByPizzaId(id);
    }

    /*
     * UPDATE Operation
     * This method will update existing user details by finding it using the ID
     * and returns the updated data
     * URI to access this: http://localhost:8080/pizzaApp/updateUser?email=updated&password=1234
     */
    @GetMapping(path="/updateUser")
    public @ResponseBody List<UserDetails> updateUserDetails(@RequestParam String email, @RequestParam String password) {
        //First get all the user details according to the provided ID
        List<UserDetails> userDetailsList = userRepository.findByEmail(email);
        if(!userDetailsList.isEmpty()) {
            //Iterate through the user list
            for(UserDetails userDetails: userDetailsList) {
                //Set new values
                userDetails.setPassword(password);
                //Update existing user item
                userRepository.save(userDetails);
            }
        }
        return userRepository.findByEmail(email);
    }

}
