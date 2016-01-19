package lvs

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RecordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Record.list(params), model:[recordCount: Record.count()]
    }

    def show(Record record) {
        respond record
    }

    def create() {
        respond new Record(params)
    }

    @Transactional
    def save(Record record) {
        if (record == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (record.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond record.errors, view:'create'
            return
        }

        record.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'record.label', default: 'Record'), record.id])
                redirect record
            }
            '*' { respond record, [status: CREATED] }
        }
    }

    def edit(Record record) {
        respond record
    }

    @Transactional
    def update(Record record) {
        if (record == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (record.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond record.errors, view:'edit'
            return
        }

        record.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'record.label', default: 'Record'), record.id])
                redirect record
            }
            '*'{ respond record, [status: OK] }
        }
    }

    @Transactional
    def delete(Record record) {

        if (record == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        record.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'record.label', default: 'Record'), record.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'record.label', default: 'Record'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
