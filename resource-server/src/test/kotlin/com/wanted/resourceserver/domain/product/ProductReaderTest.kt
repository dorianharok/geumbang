package com.wanted.resourceserver.domain.product

import com.wanted.resourceserver.fixture.ProductFixture
import com.wanted.resourceserver.infra.db.ProductRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class ProductReaderTest {

    private val productRepository = mockk<ProductRepository>()
    private val productReader = ProductReader(productRepository)

    @Test
    fun `상품을 가져온다`() {
        // given
        every { productRepository.findByIdOrNull(any()) } returns ProductFixture.gold999()

        // when
        val product = productReader.read(1L)

        // then
        assertThat(product).extracting("id", "price", "type")
            .containsExactlyInAnyOrder(
                1L, BigDecimal.valueOf(110000), GoldType.GOLD_999
            )
    }
}