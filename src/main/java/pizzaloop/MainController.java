package pizzaloop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * Created  by NISANSALA
 */
@Controller
@RequestMapping(path="/demo")
public class MainController {
    private static final String SUCCESS= "Saved";

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private CartRepository cartRepository;

    /*
    * READ Operation
    * This method will list all the pizzas in the table
    * URI to access this: http://localhost:8080/demo/all
    */
    @GetMapping(path="/all")
    public @ResponseBody Iterable<PizzaDetails> getPizzaDetails() {
        return pizzaRepository.findAll();
    }

    /*
     * READ Operation
     * This method will list all the cart item in the table
     * URI to access this: http://localhost:8080/demo/cart
     */
    @GetMapping(path="/cart")
    public @ResponseBody Iterable<CartDetails> getCartDetails() {
        return cartRepository.findAll();
    }

    /*
    * READ Operation based on Pizza ID
    * This method will return the details of a pizza specified by the ID
    * URI to access this: http://localhost:8080/demo/findByPizzaId?id=2
    */
    @GetMapping(path="/findByPizzaId")
    public @ResponseBody List<PizzaDetails> getPizzaById(@RequestParam Integer id) {
        return pizzaRepository.findByPizzaId(id);
    }

    /*
    * CREATE Operation
    * This method will crate new pizza item in the database table
    * and returns the SUCCESS message
    * URI to access this: http://localhost:8080/demo/addPizza?name=VegiPizza&description=VegiSupreme&price=2500&imageUrl=image
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
     * This method will crate new cart item in the database table
     * and returns the SUCCESS message
     * URI to access this: http://localhost:8080/demo/addCart?pizzaName=VegitablePizza&pizzaImageUrl=image&pizzaPrice=678&pizzaQuantity=4
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
    * URI to access this: http://localhost:8080/demo/deleteByPizzaId?id=2
    */
    @GetMapping(path="/deleteByPizzaId")
    public @ResponseBody List<PizzaDetails> deletePizzaById(@RequestParam Integer id) {
        return pizzaRepository.deleteByPizzaId(id);
    }

    /*
     * DELETE Operation
     * This method will delete existing cart item by finding it using the ID
     * and returns the deleted item
     * URI to access this: http://localhost:8080/demo/deleteByCartId?id=2
     */
    @GetMapping(path="/deleteByCartId")
    public @ResponseBody List<CartDetails> deleteCartById(@RequestParam Integer id) {
        return cartRepository.deleteByCartId(id);
    }

    /*
     * DELETE Operation
     * This method will delete all cart item
     * URI to access this: http://localhost:8080/demo/deleteCartAll
     */
    @GetMapping(path="/deleteCartAll")
    public @ResponseBody void deleteCartAll() {
        cartRepository.deleteAll();
    }

    /*
    * UPDATE Operation
    * This method will update existing pizza details by finding it using the ID
    * and returns the updated data
    * URI to access this: http://localhost:8080/demo/updatePizza?id=1&name=updatedname&description=updated&price=1234.56&imageUrl=image
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

}
