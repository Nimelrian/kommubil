package de.nimelrian.kommubil.backend

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import org.postgis.PGbox2d
import org.postgis.PGgeometry
import org.postgis.Point
import java.util.*

object MessagesTable : IdTable<UUID>() {
    override val id = uuid("id").entityId()
    val content = text("content")
    val author = text("author")
    val location = point("location")

    override val primaryKey = PrimaryKey(id)
}

fun Table.point(name: String, srid: Int = 4326): Column<Point> = registerColumn(name, PointColumnType())

infix fun ExpressionWithColumnType<*>.within(box: PGbox2d): Op<Boolean> = WithinOp(this, box)

private class PointColumnType(val srid: Int = 4326) : ColumnType() {
    override fun sqlType() = "GEOMETRY(Point, $srid)"
    override fun valueFromDB(value: Any) = if (value is PGgeometry) value.geometry else value
    override fun notNullValueToDB(value: Any): Any {
        if (value is Point) {
            if (value.srid == Point.UNKNOWN_SRID) value.srid = srid
            return PGgeometry(value)
        }
        return value
    }
}

private class WithinOp(val expr1: Expression<*>, val box: PGbox2d) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        expr1.toQueryBuilder(queryBuilder)
        queryBuilder.append("&& ST_MakeEnvelope(${box.llb.x}, ${box.llb.y}, ${box.urt.x}, ${box.urt.y}, 4326)")
    }
}
