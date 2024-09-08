package com.wanted.resourceserver.domain

import com.wanted.resourceserver.domain.order.OrderService
import com.wanted.resourceserver.infra.db.OrderItemRepository
import com.wanted.resourceserver.infra.db.OrderRepository
import com.wanted.resourceserver.infra.db.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
abstract class ContainerTest {

    companion object {
        @Container
        private val mariaDBContainer = MariaDBContainer<Nothing>("mariadb:10.5.8").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mariaDBContainer::getJdbcUrl)
            registry.add("spring.datasource.username", mariaDBContainer::getUsername)
            registry.add("spring.datasource.password", mariaDBContainer::getPassword)
            registry.add("spring.datasource.driver-class-name") { "org.mariadb.jdbc.Driver" }
        }
    }

    @Autowired
    protected lateinit var orderService: OrderService

    @Autowired
    protected lateinit var orderRepository: OrderRepository

    @Autowired
    protected lateinit var orderItemRepository: OrderItemRepository

    @Autowired
    protected lateinit var productRepository: ProductRepository

    init {
        mariaDBContainer.start()
    }
}