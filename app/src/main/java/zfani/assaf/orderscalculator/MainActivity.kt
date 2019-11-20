package zfani.assaf.orderscalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list1 = listOf(
            OrdersAnalyzer.OrderLine(1, "Product1", 1, BigDecimal(1.0)),
            OrdersAnalyzer.OrderLine(2, "Product2", 2, BigDecimal(2.0)),
            OrdersAnalyzer.OrderLine(3, "Product3", 3, BigDecimal(3.0))
        )

        val order1 = OrdersAnalyzer.Order(1, LocalDateTime.of(2019, 10, 27, 12, 0), list1)

        val list2 = listOf(
            OrdersAnalyzer.OrderLine(4, "Product4", 4, BigDecimal(4.0)),
            OrdersAnalyzer.OrderLine(5, "Product5", 5, BigDecimal(5.0)),
            OrdersAnalyzer.OrderLine(6, "Product6", 6, BigDecimal(6.0))
        )

        val order2 = OrdersAnalyzer.Order(2, LocalDateTime.now().minusDays(1), list2)

        tvText.text = OrdersAnalyzer().totalDailySales(listOf(order1, order2)).toString()
    }
}

class OrdersAnalyzer {

    private val map: MutableMap<DayOfWeek, Int>

    init {
        this.map = EnumMap(java.time.DayOfWeek::class.java)
    }

    data class Order(
        val orderId: Int,
        val creationDate: LocalDateTime,
        val orderLines: List<OrderLine>
    )

    data class OrderLine(
        val productId: Int,
        val name: String,
        val quantity: Int,
        val unitPrice: BigDecimal
    )

    fun totalDailySales(orders: List<Order>): Map<DayOfWeek, Int> {
        for (order in orders) {
            val key = order.creationDate.dayOfWeek
            val value = getOrderLineQuantitySum(order.orderLines)
            if (map.containsKey(key)) map[key] = map[key]!!.plus(value)
            else map[key] = value
        }
        return map
    }

    private fun getOrderLineQuantitySum(orderLines: List<OrderLine>): Int {
        var sum = 0
        for (orderLine in orderLines) {
            sum += orderLine.quantity
        }
        return sum
    }
}

