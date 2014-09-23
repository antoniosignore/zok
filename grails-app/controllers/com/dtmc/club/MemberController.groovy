package com.dtmc.club

import grails.converters.JSON
import grails.converters.XML
import arrested.ArrestedController
import java.text.SimpleDateFormat
class MemberController extends ArrestedController {

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
            Member instance = Member.get(id)
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
				renderNotFound(id, "${message(code: 'default.Member.notfound.label', default:'Member not found')}")
				
            }
        }
        else{
            renderMissingParam("${message(code: 'default.id.missing.label', default: 'id missing')}")
        }
    }

    def list() {
        def instances = Member.list()
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
			Member instance = new Member() 
						
						  if(data.address1) instance.address1 = data.address1 
						
						
						
						  if(data.address2) instance.address2 = data.address2 
						
						
						
						  if(data.city) instance.city = data.city 
						
						
						if(data.club) instance.club = com.dtmc.club.Club.get(data.club.id as Long)
						
						
						  if(data.company) instance.company = data.company 
						
						
						
						  if(data.country) instance.country = data.country 
						
						
						 
						  if(data.dateCreated) instance.dateCreated = setDate(data.dateCreated)
						
						
						
						  if(data.email) instance.email = data.email 
						
						
						
						  if(data.facebook) instance.facebook = data.facebook 
						
						
						
						  if(data.firstname) instance.firstname = data.firstname 
						
						
						 
						  if(data.lastUpdated) instance.lastUpdated = setDate(data.lastUpdated)
						
						
						
						  if(data.lastname) instance.lastname = data.lastname 
						
						
						
						  if(data.linkedin) instance.linkedin = data.linkedin 
						
						
						
						  if(data.mobile) instance.mobile = data.mobile 
						
						
						
						  if(data.passwordHash) instance.passwordHash = data.passwordHash 
						
						
						
						  if(data.phone) instance.phone = data.phone 
						
						
						
						  if(data.state) instance.state = data.state 
						
						
						
						  if(data.timezone) instance.timezone = data.timezone 
						
						
						
						  if(data.token) instance.token = data.token 
						
						
						
						  if(data.twitter) instance.twitter = data.twitter 
						
						
						
						  if(data.username) instance.username = data.username 
						
						

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
			renderMissingParam("${message(code: 'default.Member.missing.label', default: 'Member missing')}")
        }
    }

    def update() {
        if (params.instance) {
            def data = JSON.parse(params.instance)
            Member instance = Member.get(data.id as Long)
            if(instance){ 
                            if(data.address1) instance.address1 = data.address1
                            
                            if(data.address2) instance.address2 = data.address2
                            
                            if(data.city) instance.city = data.city
                            
                            if(data.club) instance.club = com.dtmc.club.Club.get(data.club.id as Long)
                            
                            if(data.company) instance.company = data.company
                            
                            if(data.country) instance.country = data.country
                            
                            if(data.dateCreated) instance.dateCreated = data.dateCreated
                            
                            if(data.email) instance.email = data.email
                            
                            if(data.facebook) instance.facebook = data.facebook
                            
                            if(data.firstname) instance.firstname = data.firstname
                            
                            if(data.lastUpdated) instance.lastUpdated = data.lastUpdated
                            
                            if(data.lastname) instance.lastname = data.lastname
                            
                            if(data.linkedin) instance.linkedin = data.linkedin
                            
                            if(data.mobile) instance.mobile = data.mobile
                            
                            if(data.passwordHash) instance.passwordHash = data.passwordHash
                            
                            if(data.phone) instance.phone = data.phone
                            
                            if(data.state) instance.state = data.state
                            
                            if(data.timezone) instance.timezone = data.timezone
                            
                            if(data.token) instance.token = data.token
                            
                            if(data.twitter) instance.twitter = data.twitter
                            
                            if(data.username) instance.username = data.username
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
				renderNotFound(data.id, "${message(code: 'default.Member.notfound.label', default: 'Member not found')}")
            }
        }
        else{
			renderMissingParam("${message(code: 'default.Member.missing.label', default: 'Member missing')}")
        }
    }

    def delete(Long id) {
        if (id){
            Member instance = Member.get(id)
            if (instance){
                instance.delete(flush: true)
              	renderSuccess(id, "${message(code: 'default.Member.deleted.label', default: 'Member deleted')}")
            }
            else{
				renderNotFound(id, "${message(code: 'default.Member.notfound.label', default: 'Member not found')}")
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
