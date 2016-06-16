package test

class Author implements Comparable<Author> {

    String name

    @Override
    int compareTo(Author author) {
        this.id <=> author.id
    }
}
