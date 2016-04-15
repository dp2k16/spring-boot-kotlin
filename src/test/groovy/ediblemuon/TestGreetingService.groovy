package ediblemuon

import groovy.json.JsonSlurper
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup


public class TestGreetingService extends Specification {

    def repository = Mock(GreetingRepository);

    def greetingController = new GreetingController(repository);

    def mockMvc = standaloneSetup(greetingController).build()

    def "test greeting restful call without arguments"() {

        when: 'greeting is hit without params'

        def response = mockMvc.perform(get('/greeting')).andReturn().response
        def responseJson = new JsonSlurper().parseText(response.getContentAsString())

        then: 'greeting should be Hello, World'

        response.status == OK.value()
        response.contentType.contains('application/json')

        responseJson.id == 1
        responseJson.content == 'Hello, World'
    }

    def "test greeting restful call and argument"() {

        when: 'greeting is hit with name parameter'

        def response = mockMvc.perform(get('/greeting?name=David')).andReturn().response
        def responseJson = new JsonSlurper().parseText(response.getContentAsString())

        then: 'greeting should be Hello, David'

        response.status == OK.value()
        response.contentType.contains('application/json')

        responseJson.id == 1
        responseJson.content == 'Hello, David'
    }


    def "test greeting restful call without arguments from database"() {

        setup:

        repository.findOne(1L) >> new Greeting(1L, "Hello, World")

        when: 'greeting is hit without params'

        def response = mockMvc.perform(get('/greetingById')).andReturn().response
        def responseJson = new JsonSlurper().parseText(response.getContentAsString())

        then: 'greeting should be Hello, World'

        response.status == OK.value()
        response.contentType.contains('application/json')

        responseJson.id == 1
        responseJson.content == 'Hello, World'
    }


    def "test greeting restful call with arguments from database"() {

        setup:

        repository.findOne(2L) >> new Greeting(2L, "Goodbye, World")

        when: 'greeting is hit without params'

        def response = mockMvc.perform(get('/greetingById?id=2')).andReturn().response
        def responseJson = new JsonSlurper().parseText(response.getContentAsString())

        then: 'greeting should be Hello, World'

        response.status == OK.value()
        response.contentType.contains('application/json')

        responseJson.id == 2
        responseJson.content == 'Goodbye, World'
    }
}