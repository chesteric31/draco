package be.chesteric31.athena.draco.web.rest

import be.chesteric31.athena.draco.DracoApplication
import be.chesteric31.athena.draco.domain.Foto
import be.chesteric31.athena.draco.domain.FotoRepository
import org.bson.types.Binary
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FotoResource(private val repository: FotoRepository, private val rabbitTemplate: RabbitTemplate) {

    @PostMapping("/")
    fun receiveFotos(@RequestParam(name = "type") type: String, @RequestParam(name = "file") file: MultipartFile) {
        println("coucou")
        var entity = Foto(null, type, Binary(file.bytes))
        entity = repository.save(entity)
        println(entity.id)
        rabbitTemplate.convertAndSend("topic.fotos", "route", entity.id!!)
    }
}
