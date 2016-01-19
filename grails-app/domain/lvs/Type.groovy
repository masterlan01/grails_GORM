package lvs

class Type {

    String typeName
    static hasMany = [recordc:Record]

    static constraints = {
        typeName(blank: false)
    }

    @Override
    String toString(){typeName}

}
