package com.dtmc.trading

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class SignalController extends ArrestedController {

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
            Signal instance = Signal.get(id)
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
				renderNotFound(id, "${message(code: 'default.Signal.notfound.label', default:'Signal not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Signal.list()
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
			Signal instance = new Signal() 
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						 
						  if(data.day) instance.day = setDate(data.day)
						
						
						
						  if(data.direction) instance.direction = data.direction 
						
						
						
						  if(data.instrument) instance.instrument = data.instrument 
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.name) instance.name = data.name 
						
						
						if(data.strategyExecution) instance.strategyExecution = com.dtmc.trading.StrategyExecution.get(data.strategyExecution.id as Long)
						
						
						  if(data.value) instance.value = data.value 
						
						

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
			renderMissingParam("${message(code: 'default.Signal.missing.label', default: 'Signal missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Signal instance = Signal.get(data.id as Long)
            if(instance){ 
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.day) instance.day = data.day
                            
                            if(data.direction) instance.direction = data.direction
                            
                            if(data.instrument) instance.instrument = data.instrument
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.name) instance.name = data.name
                            
                            if(data.strategyExecution) instance.strategyExecution = com.dtmc.trading.StrategyExecution.get(data.strategyExecution.id as Long)
                            
                            if(data.value) instance.value = data.value
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
				renderNotFound(data.id, "${message(code: 'default.Signal.notfound.label', default: 'Signal not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Signal.missing.label', default: 'Signal missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Signal instance = Signal.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Signal.deleted.label', default: 'Signal deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Signal.notfound.label', default: 'Signal not found')}")
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
