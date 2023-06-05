package be.chesteric31.athena.draco

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Config {

    @Bean
    fun queueIn(): Queue? {
        return Queue("queue_in")
    }

    @Bean
    fun queueOut(): Queue? {
        return Queue("queue_out")
    }

    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange("topic.fotos")
    }

    @Bean
    fun bindingToBroker(): Binding? {
        return BindingBuilder
            .bind(queueIn())
            .to(exchange())
            .with("route_in")
    }

    @Bean
    fun bindingFromBroker(): Binding? {
        return BindingBuilder
            .bind(queueOut())
            .to(exchange())
            .with("route_out")
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory?): RabbitTemplate? {
        val rabbitTemplate = RabbitTemplate(connectionFactory!!)
        rabbitTemplate.messageConverter = consumerJackson2MessageConverter(objectMapper())!!
        return rabbitTemplate
    }

    @Bean
    fun consumerJackson2MessageConverter(objectMapper: ObjectMapper): Jackson2JsonMessageConverter? {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(kotlinModule())
        return objectMapper
    }

}
