package test

class Book {

    String title

    SortedSet<Author> authors

    static hasMany = [authors: Author]
}
