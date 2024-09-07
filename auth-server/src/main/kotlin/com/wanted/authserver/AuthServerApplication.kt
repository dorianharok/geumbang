package com.wanted.authserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.wanted"])
class AuthServerApplication

fun main(args: Array<String>) {
	runApplication<AuthServerApplication>(*args)
}
