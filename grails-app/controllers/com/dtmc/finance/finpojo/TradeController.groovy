package com.dtmc.finance.finpojo

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class TradeController extends ArrestedController {

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
            Trade instance = Trade.get(id)
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
				renderNotFound(id, "${message(code: 'default.Trade.notfound.label', default:'Trade not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Trade.list()
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
			Trade instance = new Trade() 
						
						  if(data.amount) instance.amount = data.amount 
						
						
						
						  if(data.blog) instance.blog = data.blog 
						
						
						
						  if(data.cost) instance.cost = data.cost 
						
						
						if(data.instrument) instance.instrument = com.dtmc.finance.finpojo.Instrument.get(data.instrument.id as Long)
						
						if(data.portfolio) instance.portfolio = com.dtmc.finance.finpojo.Portfolio.get(data.portfolio.id as Long)
						
						
						  if(data.price) instance.price = data.price 
						
						
						
						  if(data.tradeAction) instance.tradeAction = data.tradeAction 
						
						
						 
						  if(data.tradeDate) instance.tradeDate = setDate(data.tradeDate)
						
						

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
			renderMissingParam("${message(code: 'default.Trade.missing.label', default: 'Trade missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Trade instance = Trade.get(data.id as Long)
            if(instance){ 
                            if(data.amount) instance.amount = data.amount
                            
                            if(data.blog) instance.blog = data.blog
                            
                            if(data.cost) instance.cost = data.cost
                            
                            if(data.instrument) instance.instrument = com.dtmc.finance.finpojo.Instrument.get(data.instrument.id as Long)
                            
                            if(data.portfolio) instance.portfolio = com.dtmc.finance.finpojo.Portfolio.get(data.portfolio.id as Long)
                            
                            if(data.price) instance.price = data.price
                            
                            if(data.tradeAction) instance.tradeAction = data.tradeAction
                            
                            if(data.tradeDate) instance.tradeDate = data.tradeDate
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
				renderNotFound(data.id, "${message(code: 'default.Trade.notfound.label', default: 'Trade not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Trade.missing.label', default: 'Trade missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Trade instance = Trade.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Trade.deleted.label', default: 'Trade deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Trade.notfound.label', default: 'Trade not found')}")
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
