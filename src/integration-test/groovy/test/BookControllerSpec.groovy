package test

import grails.test.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@Integration
class BookControllerSpec extends Specification {

    @Value('${local.server.port}')
    int port

    RestTemplate template = new RestTemplate()

    def setup() {
        Book.withNewTransaction {
            def (yamada, tanaka, satou) = ['山田', '田中', '佐藤'].collect { new Author(name: it).save(failOnError: true) }

            new Book(title: 'Groovy').addToAuthors(yamada).addToAuthors(tanaka).addToAuthors(satou).save(failOnError: true)
            new Book(title: 'Grails').addToAuthors(tanaka).save(failOnError: true)
        }
    }

    def cleanup() {
        Book.withNewTransaction {
            Book.executeUpdate('delete from Book')
            Author.executeUpdate('delete from Author')
        }
    }

    def "Test Book API"() {
        when:
        def entity = template.getForEntity("http://localhost:${port}/book", Map)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == [
            books: [
                [title: 'Groovy', authors: [[name: '山田'], [name: '田中'], [name: '佐藤']]],
                [title: 'Grails', authors: [[name: '田中']]],
            ]
        ]
    }
}
