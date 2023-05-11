package be.chesteric31.athena.draco.domain

import org.bson.types.Binary
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "fotos")
data class Foto(@Id var id: String? = null, val type: String, val image: Binary) {

}
