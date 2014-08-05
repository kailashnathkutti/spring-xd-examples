/**
 * Simple implementation of a Spring Integration transformer that creates an order object
 *
 */
import com.pivotal.example.xd.RetailOrder

split = payload.split(",")
return new RetailOrder(Integer.valueOf(split[0]),Integer.valueOf(split[1]),Double.valueOf(split[2]),Integer.valueOf(split[3]))
