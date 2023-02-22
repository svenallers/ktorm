package org.ktorm.schema

import org.ktorm.expression.QuerySourceExpression

/** TODO (is derived and virtual the same?)
 * A real table, derived table or virtual table that can only be used as source
 */
public interface TableDeclaring { // TODO with generic like for ColumnDeclaring???
    public val columns: List<ColumnDeclaring<*>>
    public fun asExpression(): QuerySourceExpression
}
