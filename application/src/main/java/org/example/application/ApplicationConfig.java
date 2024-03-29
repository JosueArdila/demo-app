package org.example.application;

import org.example.business.usecases.AddCourseUseCase;
import org.example.business.usecases.CreateProgramUseCase;
import org.example.generic.EventStoreRepository;
import com.mongodb.ConnectionString;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.net.URI;

@Configuration
public class ApplicationConfig {

    public static final String EXCHANGE = "scoreextraction";


    @Bean
    public CreateProgramUseCase createProgramUseCase() {
        return new CreateProgramUseCase();
    }

    @Bean
    public AddCourseUseCase addCourseUseCase(EventStoreRepository repository) {
        return new AddCourseUseCase(repository);
    }

//    @Bean("commands")
//    @Primary
//    public MongoTemplate mongoTemplateCommands(@Value("${spring.commands.uri}") String uri) {
//        ConnectionString connectionString = new ConnectionString(uri);
//        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(connectionString));
//    }
//
//    @Bean("queries")
//    public MongoTemplate mongoTemplateQuerys(@Value("${spring.queries.uri}") String uri) {
//        ConnectionString connectionString = new ConnectionString(uri);
//        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(connectionString));
//    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(@Value("${spring.bus.uri}") String uri) {
//        return new RabbitTemplate(new CachingConnectionFactory(URI.create(uri)));
//    }

    @Bean
    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
        var rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitAdmin.declareExchange(new TopicExchange(EXCHANGE));
        return rabbitAdmin;
    }


}
