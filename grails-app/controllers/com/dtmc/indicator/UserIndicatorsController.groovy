package com.dtmc.indicator

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class UserIndicatorsController extends ArrestedController {

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
            UserIndicators instance = UserIndicators.get(id)
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
				renderNotFound(id, "${message(code: 'default.UserIndicators.notfound.label', default:'UserIndicators not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = UserIndicators.list()
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
			UserIndicators instance = new UserIndicators() 
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						
						  if(data.double1) instance.double1 = data.double1 
						
						
						
						  if(data.double2) instance.double2 = data.double2 
						
						
						
						  if(data.integer1) instance.integer1 = data.integer1 
						
						
						
						  if(data.integer2) instance.integer2 = data.integer2 
						
						
						
						  if(data.integer3) instance.integer3 = data.integer3 
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.name) instance.name = data.name 
						
						
						
						  if(data.str1) instance.str1 = data.str1 
						
						
						
						  if(data.type) instance.type = data.type 
						
						
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
			renderMissingParam("${message(code: 'default.UserIndicators.missing.label', default: 'UserIndicators missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            UserIndicators instance = UserIndicators.get(data.id as Long)
            if(instance){ 
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.double1) instance.double1 = data.double1
                            
                            if(data.double2) instance.double2 = data.double2
                            
                            if(data.integer1) instance.integer1 = data.integer1
                            
                            if(data.integer2) instance.integer2 = data.integer2
                            
                            if(data.integer3) instance.integer3 = data.integer3
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.name) instance.name = data.name
                            
                            if(data.str1) instance.str1 = data.str1
                            
                            if(data.type) instance.type = data.type
                            
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
				renderNotFound(data.id, "${message(code: 'default.UserIndicators.notfound.label', default: 'UserIndicators not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.UserIndicators.missing.label', default: 'UserIndicators missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            UserIndicators instance = UserIndicators.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.UserIndicators.deleted.label', default: 'UserIndicators deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.UserIndicators.notfound.label', default: 'UserIndicators not found')}")
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
