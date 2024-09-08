package com.wanted.resourceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.wanted"])
class ResourceServerApplication

fun main(args: Array<String>) {
    runApplication<ResourceServerApplication>(*args)
}
