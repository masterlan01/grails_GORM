package lvs

class Record {

    String title
    String link
    String comment
    Date dateCreated

    static belongsTo = [ type : Type ]

    static constraints = {
        title(blank: false, unique: true)
        link(blank: false, unique: true, url: true)
        comment(blank: true, nullable: true)
        dateCreated(blank: false)
    }

}
