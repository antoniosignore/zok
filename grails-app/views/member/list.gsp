
<div data-ng-controller="MemberCtrl" >
    <h1>Member List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newMember()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['member']" default="New member"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in members"  data-ng-click="editMember(instance)">
                            
                            	
                             		<td data-sortable="'address1'" filter="{'address1':'text'}"  data-title="'address1'"  >
                            	
                            	{{instance.address1}}
                            </td>
                            
                            	
                            		<td data-sortable="'address2'"  data-title="'address2'"  >
                            	
                            	{{instance.address2}}
                            </td>
                            
                            	
                            		<td data-sortable="'city'"  data-title="'city'"  >
                            	
                            	{{instance.city}}
                            </td>
                            
                            	
                            		<td data-sortable="'club'"  data-title="'club'"  >
                            	
                            	{{instance.club}}
                            </td>
                            
                            	
                            		<td data-sortable="'company'"  data-title="'company'"  >
                            	
                            	{{instance.company}}
                            </td>
                            
                            	
                            		<td data-sortable="'country'"  data-title="'country'"  >
                            	
                            	{{instance.country}}
                            </td>
                            
                            	
                            		<td data-sortable="'dateCreated'"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'email'"  data-title="'email'"  >
                            	
                            	{{instance.email}}
                            </td>
                            
                            	
                            		<td data-sortable="'facebook'"  data-title="'facebook'"  >
                            	
                            	{{instance.facebook}}
                            </td>
                            
                            	
                            		<td data-sortable="'firstname'"  data-title="'firstname'"  >
                            	
                            	{{instance.firstname}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastname'"  data-title="'lastname'"  >
                            	
                            	{{instance.lastname}}
                            </td>
                            
                            	
                            		<td data-sortable="'linkedin'"  data-title="'linkedin'"  >
                            	
                            	{{instance.linkedin}}
                            </td>
                            
                            	
                            		<td data-sortable="'mobile'"  data-title="'mobile'"  >
                            	
                            	{{instance.mobile}}
                            </td>
                            
                            	
                            		<td data-sortable="'passwordHash'"  data-title="'passwordHash'"  >
                            	
                            	{{instance.passwordHash}}
                            </td>
                            
                            	
                            		<td data-sortable="'phone'"  data-title="'phone'"  >
                            	
                            	{{instance.phone}}
                            </td>
                            
                            	
                            		<td data-sortable="'state'"  data-title="'state'"  >
                            	
                            	{{instance.state}}
                            </td>
                            
                            	
                            		<td data-sortable="'timezone'"  data-title="'timezone'"  >
                            	
                            	{{instance.timezone}}
                            </td>
                            
                            	
                            		<td data-sortable="'token'"  data-title="'token'"  >
                            	
                            	{{instance.token}}
                            </td>
                            
                            	
                            		<td data-sortable="'twitter'"  data-title="'twitter'"  >
                            	
                            	{{instance.twitter}}
                            </td>
                            
                            	
                            		<td data-sortable="'username'"  data-title="'username'"  >
                            	
                            	{{instance.username}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
