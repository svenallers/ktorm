/*
 * Copyright 2018-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ktorm.support.postgresql

import org.ktorm.dsl.cast
import org.ktorm.expression.ArgumentExpression
import org.ktorm.expression.FunctionExpression
import org.ktorm.schema.*

/**
 * TODO.
 */
public inline fun <reified T : Enum<T>> arrayPosition(
    value: Array<T?>,
    column: ColumnDeclaring<T>
): FunctionExpression<Int> {
    val columnSqlType = column.sqlType
    return if (columnSqlType is EnumSqlType<T>) {
        arrayPosition(value, column, columnSqlType.toEnumArraySqlType())
    } else {
        arrayPosition(value.map { it?.name }.toTypedArray(), column.cast(TextSqlType))
    }
}

/**
 * TODO.
 */
public inline fun <reified T : Enum<T>> arrayPosition(
    value: Collection<T?>,
    column: ColumnDeclaring<T>
): FunctionExpression<Int> {
    val columnSqlType = column.sqlType
    return if (columnSqlType is EnumSqlType<T>) {
        //arrayPosition(value.map { it?.name }.toTypedArray(), column.cast(TextSqlType))
        arrayPosition(value.toTypedArray(), column.cast(columnSqlType), columnSqlType.toEnumArraySqlType())
    } else {
        arrayPosition(value.map { it?.name }.toTypedArray(), column.cast(TextSqlType))
    }
}

/**
 * TODO.
 */
public fun arrayPosition(value: TextArray, column: ColumnDeclaring<String>): FunctionExpression<Int> =
    arrayPosition(value, column, TextArraySqlType)

/**
 * TODO.
 */
public fun <T : Any> arrayPosition(
    value: Array<T?>,
    column: ColumnDeclaring<T>,
    arraySqlType: SqlType<Array<T?>>
): FunctionExpression<Int> =
    FunctionExpression(
        functionName = "array_position",
        arguments = listOf(ArgumentExpression(value, arraySqlType), column.asExpression()),
        sqlType = IntSqlType
    )
