package com.dtmc.finance.finpojo

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class ForecastController extends ArrestedController {

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
            Forecast instance = Forecast.get(id)
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
				renderNotFound(id, "${message(code: 'default.Forecast.notfound.label', default:'Forecast not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Forecast.list()
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
			Forecast instance = new Forecast() 
						if(data.bet) instance.bet = com.dtmc.finance.finpojo.Bet.get(data.bet.id as Long)
						
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.name) instance.name = data.name 
						
						
						
						  if(data.percent) instance.percent = data.percent 
						
						
						if(data.ticker) instance.ticker = com.dtmc.finance.finpojo.asset.Stock.get(data.ticker.id as Long)
						
						if(data.user) instance.user = com.dtmc.club.Member.get(data.user.id as Long)
						
						 
						  if(data.when) instance.when = setDate(data.when)
						
						

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
			renderMissingParam("${message(code: 'default.Forecast.missing.label', default: 'Forecast missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Forecast instance = Forecast.get(data.id as Long)
            if(instance){ 
                            if(data.bet) instance.bet = com.dtmc.finance.finpojo.Bet.get(data.bet.id as Long)
                            
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.name) instance.name = data.name
                            
                            if(data.percent) instance.percent = data.percent
                            
                            if(data.ticker) instance.ticker = com.dtmc.finance.finpojo.asset.Stock.get(data.ticker.id as Long)
                            
                            if(data.user) instance.user = com.dtmc.club.Member.get(data.user.id as Long)
                            
                            if(data.when) instance.when = data.when
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
				renderNotFound(data.id, "${message(code: 'default.Forecast.notfound.label', default: 'Forecast not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Forecast.missing.label', default: 'Forecast missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Forecast instance = Forecast.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Forecast.deleted.label', default: 'Forecast deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Forecast.notfound.label', default: 'Forecast not found')}")
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
