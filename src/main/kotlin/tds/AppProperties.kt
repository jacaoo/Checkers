package tds
//Este ficheiro vai servir apenas para manter as constantes num sítio específico em vez do ficheiro original de onde fora criadas
import java.util.*

private const val APPLICATION_PROPERTIES = "/application.properties"

object AppProperties {

    public val p: Properties

    init {
        p = Properties()
        val inputStream = AppProperties::class.java.getResourceAsStream(APPLICATION_PROPERTIES)
        //use, executes the block of code and then closes the resource
        inputStream?.use { p.load(it) }
            ?: throw IllegalArgumentException("Properties file not found: ${APPLICATION_PROPERTIES}")
    }
}
