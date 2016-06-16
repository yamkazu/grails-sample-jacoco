package test

class BookController {

    def index() {
        [books: Book.list(sort: 'id', order: 'asc')]
    }
}
