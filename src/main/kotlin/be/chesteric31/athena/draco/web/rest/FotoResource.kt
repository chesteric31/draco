package be.chesteric31.athena.draco.web.rest

import be.chesteric31.athena.draco.domain.Foto
import be.chesteric31.athena.draco.domain.FotoRepository
import org.bson.types.Binary
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("fotos")
class FotoResource(private val repository: FotoRepository, private val rabbitTemplate: RabbitTemplate) {

    @PostMapping("/")
    fun receiveFoto(@RequestParam(name = "type") type: String, @RequestParam(name = "file") file: MultipartFile) {
        var entity = Foto(null, type, Binary(file.bytes),  LocalDate.now(), null)
        entity = repository.save(entity)
        rabbitTemplate.convertAndSend("topic.fotos", "route_in", entity.id!!)
    }

    @GetMapping("/{id}", produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun getFoto(@PathVariable id: String): ByteArray? {
        return repository.findByIdOrNull(id)?.image?.data
    }

    @PostMapping("/{id}")
    fun receiveIndex(@PathVariable id: String, @RequestParam(name = "index") index: Long): Foto {
        val found = repository.findByIdOrNull(id)
        found?.index = index
        println("${id} - ${index}")
        return repository.save(found!!)
    }
}
