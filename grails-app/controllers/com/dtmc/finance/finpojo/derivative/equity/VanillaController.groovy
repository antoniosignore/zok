package com.dtmc.finance.finpojo.derivative.equity

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class VanillaController extends ArrestedController {

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
            Vanilla instance = Vanilla.get(id)
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
				renderNotFound(id, "${message(code: 'default.Vanilla.notfound.label', default:'Vanilla not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Vanilla.list()
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
			Vanilla instance = new Vanilla() 
						
						  if(data.change) instance.change = data.change 
						
						
						
						  if(data.contractSize) instance.contractSize = data.contractSize 
						
						
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						
						  if(data.dividend) instance.dividend = data.dividend 
						
						
						 
						  if(data.expiration) instance.expiration = setDate(data.expiration)
						
						
						
						  if(data.interestRate) instance.interestRate = data.interestRate 
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.name) instance.name = data.name 
						
						
						
						  if(data.openInterest) instance.openInterest = data.openInterest 
						
						
						
						  if(data.premium) instance.premium = data.premium 
						
						
						
						  if(data.strike) instance.strike = data.strike 
						
						
						
						  if(data.type) instance.type = data.type 
						
						
						if(data.underlying) instance.underlying = com.dtmc.finance.finpojo.Instrument.get(data.underlying.id as Long)
						

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
			renderMissingParam("${message(code: 'default.Vanilla.missing.label', default: 'Vanilla missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Vanilla instance = Vanilla.get(data.id as Long)
            if(instance){ 
                            if(data.change) instance.change = data.change
                            
                            if(data.contractSize) instance.contractSize = data.contractSize
                            
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.dividend) instance.dividend = data.dividend
                            
                            if(data.expiration) instance.expiration = data.expiration
                            
                            if(data.interestRate) instance.interestRate = data.interestRate
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.name) instance.name = data.name
                            
                            if(data.openInterest) instance.openInterest = data.openInterest
                            
                            if(data.premium) instance.premium = data.premium
                            
                            if(data.strike) instance.strike = data.strike
                            
                            if(data.type) instance.type = data.type
                            
                            if(data.underlying) instance.underlying = com.dtmc.finance.finpojo.Instrument.get(data.underlying.id as Long)
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
				renderNotFound(data.id, "${message(code: 'default.Vanilla.notfound.label', default: 'Vanilla not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Vanilla.missing.label', default: 'Vanilla missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Vanilla instance = Vanilla.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Vanilla.deleted.label', default: 'Vanilla deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Vanilla.notfound.label', default: 'Vanilla not found')}")
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
