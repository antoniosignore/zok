package com.dtmc.finance.finpojo

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class EntryController extends ArrestedController {

    def grailsApplication
    
    static allowedMethods = [show: "GET", list: "GET", save: "POST", update: "PUT", delete: "DELETE"]
	def listing() { 
		withFormat {
			html {
				render(view: "list")
			}
		}
	}
	def edit() {}
	
    def show(Long id) {
        if(id){
            Entry instance = Entry.get(id)
            if(instance){
                withFormat{
                    xml {
                        render instance as XML
                    }
                    json {
                        render instance as JSON
                    }
                }
            }
            else{
				renderNotFound(id, "${message(code: 'default.Entry.notfound.label', default:'Entry not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Entry.list()
        withFormat{
            xml {
                render instances as XML
            }
            json {
                render instances as JSON
            }
        }
    }

    def save() {
		if (request.JSON.instance) {
			def data = request.JSON.instance
			Entry instance = new Entry() 
						
						  if(data.amount) instance.amount = data.amount 
						
						
						if(data.instrument) instance.instrument = com.dtmc.finance.finpojo.Instrument.get(data.instrument.id as Long)
						
						if(data.portfolio) instance.portfolio = com.dtmc.finance.finpojo.Portfolio.get(data.portfolio.id as Long)
						

            if(instance.save(flush: true)){
                withFormat {
                    xml {
                        response.status = 200
                        render instance as XML
                    }
                    json {
                        response.status = 200
                        render instance as JSON
                    }
                }
            }
            else{
                render409orEdit(instance)
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Entry.missing.label', default: 'Entry missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Entry instance = Entry.get(data.id as Long)
            if(instance){ 
                            if(data.amount) instance.amount = data.amount
                            
                            if(data.instrument) instance.instrument = com.dtmc.finance.finpojo.Instrument.get(data.instrument.id as Long)
                            
                            if(data.portfolio) instance.portfolio = com.dtmc.finance.finpojo.Portfolio.get(data.portfolio.id as Long)
                            if(instance.save(flush: true)){
                    withFormat {
                        xml {
                            response.status = 200
                            render instance as XML
                        }
                        json {
                            response.status = 200
                            render instance as JSON
                        }
                    }
                }
                else{
                    render409orEdit(instance)
                }
            }
            else{
				renderNotFound(data.id, "${message(code: 'default.Entry.notfound.label', default: 'Entry not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Entry.missing.label', default: 'Entry missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Entry instance = Entry.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Entry.deleted.label', default: 'Entry deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Entry.notfound.label', default: 'Entry not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }
    private setDate (String d) {
      String dFormat=grailsApplication?.config.arrested.dateFormat ?: 'dd/MM/yyyy'
      return (new SimpleDateFormat(dFormat)).parse(d)
    }
}
