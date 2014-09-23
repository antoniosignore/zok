
<div data-ng-controller="SignalCtrl" >
    <h1>Signal List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newSignal()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['signal']" default="New signal"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in signals"  data-ng-click="editSignal(instance)">
                            
                            	
                             		<td data-sortable="'dateCreated'" filter="{'dateCreated':'text'}"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'day'"  data-title="'day'"  >
                            	
                            	{{instance.day}}
                            </td>
                            
                            	
                            		<td data-sortable="'direction'"  data-title="'direction'"  >
                            	
                            	{{instance.direction}}
                            </td>
                            
                            	
                            		<td data-sortable="'instrument'"  data-title="'instrument'"  >
                            	
                            	{{instance.instrument}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'name'"  data-title="'name'"  >
                            	
                            	{{instance.name}}
                            </td>
                            
                            	
                            		<td data-sortable="'strategyExecution'"  data-title="'strategyExecution'"  >
                            	
                            	{{instance.strategyExecution}}
                            </td>
                            
                            	
                            		<td data-sortable="'value'"  data-title="'value'"  >
                            	
                            	{{instance.value}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
