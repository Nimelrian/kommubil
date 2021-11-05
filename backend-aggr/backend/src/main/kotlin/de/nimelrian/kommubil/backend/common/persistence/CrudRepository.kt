package de.nimelrian.kommubil.backend.common.persistence

interface CrudRepository<Model, Key> {
    fun create(m: Model): Model
    fun findAll(): Iterable<Model>
    fun deleteAll(): Int
}
