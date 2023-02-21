package org.ktorm.schema

import org.ktorm.expression.ScalarExpression

public data class Values(val values: List<ScalarExpression<*>>, val alias: String = "values"): SourceTable {
}

public data class Value<T: Any>(val expression: ScalarExpression<T>, val column: SourceColumn<T>)

fun values(expr: ScalarExpression, ..., alias = null, columnNames = null): Values {
    Table()
}
