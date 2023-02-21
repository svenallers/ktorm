package org.ktorm.schema

import org.ktorm.expression.QuerySourceExpression

/** TODO (is derived and virtual the same?)
 * A real table, derived table or virtual table that can only be used as source
 */
public interface SourceTable {
    public val columns: List<SourceColumn<*>>
    public fun asExpression(): QuerySourceExpression
}
