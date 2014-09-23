package com.dtmc.club

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class ClubController extends ArrestedController {

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
            Club instance = Club.get(id)
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
				renderNotFound(id, "${message(code: 'default.Club.notfound.label', default:'Club not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Club.list()
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
			Club instance = new Club() 
						
						  if(data.agreement) instance.agreement = data.agreement 
						
						
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						 
						  if(data.inauguralMeeting) instance.inauguralMeeting = setDate(data.inauguralMeeting)
						
						
						
						  if(data.joiningFee) instance.joiningFee = data.joiningFee 
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.monthlySubscription) instance.monthlySubscription = data.monthlySubscription 
						
						
						
						  if(data.name) instance.name = data.name 
						
						
						
						  if(data.yearsTimeSpan) instance.yearsTimeSpan = data.yearsTimeSpan 
						
						

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
			renderMissingParam("${message(code: 'default.Club.missing.label', default: 'Club missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Club instance = Club.get(data.id as Long)
            if(instance){ 
                            if(data.agreement) instance.agreement = data.agreement
                            
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.inauguralMeeting) instance.inauguralMeeting = data.inauguralMeeting
                            
                            if(data.joiningFee) instance.joiningFee = data.joiningFee
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.monthlySubscription) instance.monthlySubscription = data.monthlySubscription
                            
                            if(data.name) instance.name = data.name
                            
                            if(data.yearsTimeSpan) instance.yearsTimeSpan = data.yearsTimeSpan
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
				renderNotFound(data.id, "${message(code: 'default.Club.notfound.label', default: 'Club not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Club.missing.label', default: 'Club missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Club instance = Club.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Club.deleted.label', default: 'Club deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Club.notfound.label', default: 'Club not found')}")
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
