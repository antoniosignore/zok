package com.dtmc.trading

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class StrategySubscriptionController extends ArrestedController {

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
            StrategySubscription instance = StrategySubscription.get(id)
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
				renderNotFound(id, "${message(code: 'default.StrategySubscription.notfound.label', default:'StrategySubscription not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = StrategySubscription.list()
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
			StrategySubscription instance = new StrategySubscription() 
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						if(data.strategy) instance.strategy = com.dtmc.trading.StrategyCatalog.get(data.strategy.id as Long)
						
						if(data.user) instance.user = com.dtmc.club.Member.get(data.user.id as Long)
						

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
			renderMissingParam("${message(code: 'default.StrategySubscription.missing.label', default: 'StrategySubscription missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            StrategySubscription instance = StrategySubscription.get(data.id as Long)
            if(instance){ 
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.strategy) instance.strategy = com.dtmc.trading.StrategyCatalog.get(data.strategy.id as Long)
                            
                            if(data.user) instance.user = com.dtmc.club.Member.get(data.user.id as Long)
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
				renderNotFound(data.id, "${message(code: 'default.StrategySubscription.notfound.label', default: 'StrategySubscription not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.StrategySubscription.missing.label', default: 'StrategySubscription missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            StrategySubscription instance = StrategySubscription.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.StrategySubscription.deleted.label', default: 'StrategySubscription deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.StrategySubscription.notfound.label', default: 'StrategySubscription not found')}")
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
