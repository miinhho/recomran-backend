package io.miinhho.recomran

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class RecomRanApplication

fun main(args: Array<String>) {
	runApplication<RecomRanApplication>(*args)
}
