import groovy.json.JsonSlurper
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

public class TestGreetingService extends Specification {

    def greetingController = new GreetingController()

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
}