package ediblemuon

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id

@Entity
data class Greeting(
        @Id
        @GeneratedValue(strategy = AUTO)
        var id: Long = 0,
        var content: String = ""
)

interface GreetingRepository : CrudRepository<Greeting, Long> {

}

/**
 * Simple REST controller.
 * @param GreetingRepository repository
 */
@RestController
class GreetingController @Autowired constructor(val repository: GreetingRepository) {

    @RequestMapping("/greetingById")
    fun greetingFromDb(@RequestParam(value = "id", defaultValue = "1") id: Long): Greeting = repository.findOne(id)

    val counter = AtomicLong()

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting = Greeting(counter.incrementAndGet(), "Hello, $name")

}

@SpringBootApplication
open class Application {

    @Bean
    open fun init(repository: GreetingRepository) = CommandLineRunner {
        repository.save(Greeting(1, "Hello, World"))
        repository.save(Greeting(2, "Goodbye, World"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}