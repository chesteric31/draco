package be.chesteric31.athena.draco

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    fun queue(): Queue? {
        return Queue("queue")
    }

    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange("topic.fotos")
    }

    @Bean
    fun binding(): Binding? {
        return BindingBuilder
            .bind(queue())
            .to(exchange())
            .with("route")
    }
}
