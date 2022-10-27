package org.ktorm.support.postgresql

import org.ktorm.dsl.cast
import org.ktorm.expression.ArgumentExpression
import org.ktorm.expression.FunctionExpression
import org.ktorm.schema.*

public inline fun <reified T : Enum<T>> arrayPosition(value: Collection<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> =
    arrayPosition(value.toTypedArray(), column)

public inline fun <reified T : Enum<T>> arrayPosition(value: Array<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> {
    val columnSqlType = column.sqlType
    return if (columnSqlType is EnumSqlType<T> && columnSqlType.dataTypeName ==null) {
        val castedColumn = column.cast(enumSqlType<T>("text"))
        arrayPosition(value, castedColumn, castedColumn.sqlType.toArraySqlType())
    } else {
        arrayPosition(value, column, columnSqlType.toArraySqlType())
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
