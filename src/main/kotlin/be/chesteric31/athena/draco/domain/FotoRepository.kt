package be.chesteric31.athena.draco.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FotoRepository: MongoRepository<Foto, String> {
}
