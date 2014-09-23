
<div data-ng-controller="ForecastCtrl" >
    <h1>Forecast List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newForecast()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['forecast']" default="New forecast"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in forecasts"  data-ng-click="editForecast(instance)">
                            
                            	
                             		<td data-sortable="'bet'" filter="{'bet':'text'}"  data-title="'bet'"  >
                            	
                            	{{instance.bet}}
                            </td>
                            
                            	
                            		<td data-sortable="'dateCreated'"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'name'"  data-title="'name'"  >
                            	
                            	{{instance.name}}
                            </td>
                            
                            	
                            		<td data-sortable="'percent'"  data-title="'percent'"  >
                            	
                            	{{instance.percent}}
                            </td>
                            
                            	
                            		<td data-sortable="'ticker'"  data-title="'ticker'"  >
                            	
                            	{{instance.ticker}}
                            </td>
                            
                            	
                            		<td data-sortable="'user'"  data-title="'user'"  >
                            	
                            	{{instance.user}}
                            </td>
                            
                            	
                            		<td data-sortable="'when'"  data-title="'when'"  >
                            	
                            	{{instance.when}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
