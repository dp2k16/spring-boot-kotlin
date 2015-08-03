import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

data public class Greeting(val id: Long, val content: String)

RestController public class GreetingController {

    val counter = AtomicLong()

    RequestMapping("/greeting")
    public fun greeting(RequestParam(value = "name", defaultValue = "World") name: String): Greeting = Greeting(counter.incrementAndGet(), "Hello, $name")

}

ComponentScan EnableAutoConfiguration public class Application

public fun main(args: Array<String>) {
    SpringApplication.run(javaClass<Application>(), *args)
}