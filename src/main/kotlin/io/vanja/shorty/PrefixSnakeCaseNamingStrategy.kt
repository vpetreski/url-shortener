package io.vanja.shorty

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import java.util.*

/**
 * Enables us to have shorty_table without having to @Table(name = "shorty_table") on Entities and converts FooBar to foo_bar.
 */
class PrefixSnakeCaseNamingStrategy : PhysicalNamingStrategyStandardImpl() {
    override fun toPhysicalCatalogName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalColumnName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalSchemaName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalSequenceName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalTableName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier, "shorty_")
    }

    private fun convertToSnakeCase(identifier: Identifier?, prefix: String = ""): Identifier? {
        return if (identifier != null) {
            val newName = identifier.text
                .replace("([a-z])([A-Z])".toRegex(), "$1_$2")
                .lowercase(Locale.getDefault())
            Identifier.toIdentifier(prefix + newName)
        } else {
            null
        }
    }
}