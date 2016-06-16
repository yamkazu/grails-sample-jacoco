package test

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification {

    def 'test book'() {
        when:
        def book = new Book(title: 'Grails Book').save()

        then:
        book
    }
}
