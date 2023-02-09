package org.ktorm.support.postgresql

import org.ktorm.dsl.cast
import org.ktorm.expression.ArgumentExpression
import org.ktorm.expression.FunctionExpression
import org.ktorm.schema.*

public fun <T : Enum<T>> arrayPosition(value: Array<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> {
    val columnSqlType = column.sqlType
    return if (columnSqlType is EnumSqlType<T>) {
        arrayPosition(value, column, columnSqlType.toEnumArraySqlType())
    } else {
        arrayPosition(value.map { it?.name }.toTypedArray(), column.cast(TextSqlType))
    }
}

public inline fun <reified T : Enum<T>> arrayPosition(value: Collection<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> {
    val columnSqlType = column.sqlType
    return if (columnSqlType is EnumSqlType<T>) {
        arrayPosition(value.toTypedArray(), column, columnSqlType.toEnumArraySqlType())
    } else {
        arrayPosition(value.map { it?.name }.toTypedArray(), column.cast(TextSqlType))
    }
}

public fun arrayPosition(value: TextArray, column: ColumnDeclaring<String>): FunctionExpression<Int> =
    arrayPosition(value, column, TextArraySqlType)

public fun <T : Any> arrayPosition(value: Array<T?>, column: ColumnDeclaring<T>, arraySqlType: SqlType<Array<T?>>): FunctionExpression<Int> =
    FunctionExpression(
        functionName = "array_position",
        arguments = listOf(ArgumentExpression(value, arraySqlType), column.asExpression()),
        sqlType = IntSqlType
    )
