package com.dtmc.trading

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class StrategyExecutionController extends ArrestedController {

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
            StrategyExecution instance = StrategyExecution.get(id)
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
				renderNotFound(id, "${message(code: 'default.StrategyExecution.notfound.label', default:'StrategyExecution not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = StrategyExecution.list()
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
			StrategyExecution instance = new StrategyExecution() 
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.name) instance.name = data.name 
						
						

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
			renderMissingParam("${message(code: 'default.StrategyExecution.missing.label', default: 'StrategyExecution missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            StrategyExecution instance = StrategyExecution.get(data.id as Long)
            if(instance){ 
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.name) instance.name = data.name
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
				renderNotFound(data.id, "${message(code: 'default.StrategyExecution.notfound.label', default: 'StrategyExecution not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.StrategyExecution.missing.label', default: 'StrategyExecution missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            StrategyExecution instance = StrategyExecution.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.StrategyExecution.deleted.label', default: 'StrategyExecution deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.StrategyExecution.notfound.label', default: 'StrategyExecution not found')}")
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
