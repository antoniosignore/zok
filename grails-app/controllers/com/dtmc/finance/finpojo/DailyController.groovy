package com.dtmc.finance.finpojo

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class DailyController extends ArrestedController {

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
            Daily instance = Daily.get(id)
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
				renderNotFound(id, "${message(code: 'default.Daily.notfound.label', default:'Daily not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Daily.list()
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
			Daily instance = new Daily() 
						
						  if(data.closeprice) instance.closeprice = data.closeprice 
						
						
						 
						  if(data.dailydate) instance.dailydate = setDate(data.dailydate)
						
						
						
						  if(data.high) instance.high = data.high 
						
						
						if(data.instrument) instance.instrument = com.dtmc.finance.finpojo.Instrument.get(data.instrument.id as Long)
						
						
						  if(data.low) instance.low = data.low 
						
						
						
						  if(data.openInterest) instance.openInterest = data.openInterest 
						
						
						
						  if(data.openprice) instance.openprice = data.openprice 
						
						
						
						  if(data.volume) instance.volume = data.volume 
						
						

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
			renderMissingParam("${message(code: 'default.Daily.missing.label', default: 'Daily missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Daily instance = Daily.get(data.id as Long)
            if(instance){ 
                            if(data.closeprice) instance.closeprice = data.closeprice
                            
                            if(data.dailydate) instance.dailydate = data.dailydate
                            
                            if(data.high) instance.high = data.high
                            
                            if(data.instrument) instance.instrument = com.dtmc.finance.finpojo.Instrument.get(data.instrument.id as Long)
                            
                            if(data.low) instance.low = data.low
                            
                            if(data.openInterest) instance.openInterest = data.openInterest
                            
                            if(data.openprice) instance.openprice = data.openprice
                            
                            if(data.volume) instance.volume = data.volume
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
				renderNotFound(data.id, "${message(code: 'default.Daily.notfound.label', default: 'Daily not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Daily.missing.label', default: 'Daily missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Daily instance = Daily.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Daily.deleted.label', default: 'Daily deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Daily.notfound.label', default: 'Daily not found')}")
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
