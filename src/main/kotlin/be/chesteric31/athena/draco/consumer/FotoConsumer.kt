package be.chesteric31.athena.draco.consumer

import be.chesteric31.athena.draco.domain.FotoRepository
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FotoConsumer(private val repository: FotoRepository) {

    @RabbitListener(queues = ["queue_out"])
    @RabbitHandler
    fun receiveMessage(fotoMessage: FotoMessage) {
        println("Received <$fotoMessage>")
        val found = repository.findByIdOrNull(fotoMessage.id)
        found?.index = fotoMessage.index
        repository.save(found!!)
    }
}

data class FotoMessage(val id: String, val index: Long) {

}
