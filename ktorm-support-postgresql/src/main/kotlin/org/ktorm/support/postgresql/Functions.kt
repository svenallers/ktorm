package org.ktorm.support.postgresql

import org.ktorm.dsl.cast
import org.ktorm.expression.ArgumentExpression
import org.ktorm.expression.FunctionExpression
import org.ktorm.schema.*

public inline fun <reified T : Enum<T>> arrayPosition(value: Collection<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> =
    arrayPosition(value.toTypedArray(), column)

public inline fun <reified T : Enum<T>> arrayPosition(value: Array<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> =
    arrayPosition(value, column, column.sqlType.toArraySqlType())

public inline fun <reified T: Any> arrayPosition(value: Array<T?>, column: ColumnDeclaring<T>): FunctionExpression<Int> =
    arrayPosition(value, column, column.sqlType.toArraySqlType())

public inline fun <reified T : Any> arrayPosition(value: Array<T?>, column: ColumnDeclaring<T>, arraySqlType: SqlType<Array<T?>>): FunctionExpression<Int> =
    FunctionExpression(
        functionName = "array_position",
        arguments = listOf(ArgumentExpression(value, arraySqlType), column.asExpression()),
        sqlType = IntSqlType
    )
