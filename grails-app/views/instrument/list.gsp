
<div data-ng-controller="InstrumentCtrl" >
    <h1>Instrument List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newInstrument()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['instrument']" default="New instrument"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in instruments"  data-ng-click="editInstrument(instance)">
                            
                            	
                             		<td data-sortable="'dateCreated'" filter="{'dateCreated':'text'}"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'name'"  data-title="'name'"  >
                            	
                            	{{instance.name}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
