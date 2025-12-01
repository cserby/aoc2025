import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun testGreet() {
        assertEquals("Hello, World!", greet("World"))
    }

    @Test
    fun testGreetWithEmptyString() {
        assertEquals("Hello, !", greet(""))
    }
}
