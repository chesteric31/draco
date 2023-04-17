package be.chesteric31.athena.draco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DracoApplication

fun main(args: Array<String>) {
	runApplication<DracoApplication>(*args)
}
