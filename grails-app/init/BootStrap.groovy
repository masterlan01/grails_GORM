import lvs.Type

class BootStrap {

    def init = { servletContext ->
        if (!Type.count()) {
            new Type(typeName: "Music").save(failOnError: true)
            new Type(typeName: "Movie").save(failOnError: true)
            new Type(typeName: "Book").save(failOnError: true)
        }
    }
    def destroy = {
    }
}
