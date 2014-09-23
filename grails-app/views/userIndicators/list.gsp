
<div data-ng-controller="UserIndicatorsCtrl" >
    <h1>UserIndicators List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newUserIndicators()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['userIndicators']" default="New userIndicators"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in userIndicatorss"  data-ng-click="editUserIndicators(instance)">
                            
                            	
                             		<td data-sortable="'dateCreated'" filter="{'dateCreated':'text'}"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'double1'"  data-title="'double1'"  >
                            	
                            	{{instance.double1}}
                            </td>
                            
                            	
                            		<td data-sortable="'double2'"  data-title="'double2'"  >
                            	
                            	{{instance.double2}}
                            </td>
                            
                            	
                            		<td data-sortable="'integer1'"  data-title="'integer1'"  >
                            	
                            	{{instance.integer1}}
                            </td>
                            
                            	
                            		<td data-sortable="'integer2'"  data-title="'integer2'"  >
                            	
                            	{{instance.integer2}}
                            </td>
                            
                            	
                            		<td data-sortable="'integer3'"  data-title="'integer3'"  >
                            	
                            	{{instance.integer3}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'name'"  data-title="'name'"  >
                            	
                            	{{instance.name}}
                            </td>
                            
                            	
                            		<td data-sortable="'str1'"  data-title="'str1'"  >
                            	
                            	{{instance.str1}}
                            </td>
                            
                            	
                            		<td data-sortable="'type'"  data-title="'type'"  >
                            	
                            	{{instance.type}}
                            </td>
                            
                            	
                            		<td data-sortable="'user'"  data-title="'user'"  >
                            	
                            	{{instance.user}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
