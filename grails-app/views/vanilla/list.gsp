
<div data-ng-controller="VanillaCtrl" >
    <h1>Vanilla List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newVanilla()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['vanilla']" default="New vanilla"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in vanillas"  data-ng-click="editVanilla(instance)">
                            
                            	
                             		<td data-sortable="'change'" filter="{'change':'text'}"  data-title="'change'"  >
                            	
                            	{{instance.change}}
                            </td>
                            
                            	
                            		<td data-sortable="'contractSize'"  data-title="'contractSize'"  >
                            	
                            	{{instance.contractSize}}
                            </td>
                            
                            	
                            		<td data-sortable="'dateCreated'"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'dividend'"  data-title="'dividend'"  >
                            	
                            	{{instance.dividend}}
                            </td>
                            
                            	
                            		<td data-sortable="'expiration'"  data-title="'expiration'"  >
                            	
                            	{{instance.expiration}}
                            </td>
                            
                            	
                            		<td data-sortable="'interestRate'"  data-title="'interestRate'"  >
                            	
                            	{{instance.interestRate}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'name'"  data-title="'name'"  >
                            	
                            	{{instance.name}}
                            </td>
                            
                            	
                            		<td data-sortable="'openInterest'"  data-title="'openInterest'"  >
                            	
                            	{{instance.openInterest}}
                            </td>
                            
                            	
                            		<td data-sortable="'premium'"  data-title="'premium'"  >
                            	
                            	{{instance.premium}}
                            </td>
                            
                            	
                            		<td data-sortable="'strike'"  data-title="'strike'"  >
                            	
                            	{{instance.strike}}
                            </td>
                            
                            	
                            		<td data-sortable="'type'"  data-title="'type'"  >
                            	
                            	{{instance.type}}
                            </td>
                            
                            	
                            		<td data-sortable="'underlying'"  data-title="'underlying'"  >
                            	
                            	{{instance.underlying}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
