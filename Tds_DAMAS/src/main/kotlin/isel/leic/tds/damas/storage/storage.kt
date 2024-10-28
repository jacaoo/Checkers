package isel.leic.tds.damas.storage

interface Storage<Key,Data> {
    fun create(id: Key, data: Data)
    fun read(id: Key): Data?
    fun update(id: Key, data: Data)
    fun delete(id: Key)
}
