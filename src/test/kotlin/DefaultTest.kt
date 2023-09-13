import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MyTests {

    @Test
    fun testJar() {
        // Initialize class from JAR

        assertEquals(null, Src001.oob(""))
    }
}