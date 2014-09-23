
<div data-ng-controller="DailyCtrl" >
    <h1>Daily List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newDaily()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['daily']" default="New daily"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in dailys"  data-ng-click="editDaily(instance)">
                            
                            	
                             		<td data-sortable="'closeprice'" filter="{'closeprice':'text'}"  data-title="'closeprice'"  >
                            	
                            	{{instance.closeprice}}
                            </td>
                            
                            	
                            		<td data-sortable="'dailydate'"  data-title="'dailydate'"  >
                            	
                            	{{instance.dailydate}}
                            </td>
                            
                            	
                            		<td data-sortable="'high'"  data-title="'high'"  >
                            	
                            	{{instance.high}}
                            </td>
                            
                            	
                            		<td data-sortable="'instrument'"  data-title="'instrument'"  >
                            	
                            	{{instance.instrument}}
                            </td>
                            
                            	
                            		<td data-sortable="'low'"  data-title="'low'"  >
                            	
                            	{{instance.low}}
                            </td>
                            
                            	
                            		<td data-sortable="'openInterest'"  data-title="'openInterest'"  >
                            	
                            	{{instance.openInterest}}
                            </td>
                            
                            	
                            		<td data-sortable="'openprice'"  data-title="'openprice'"  >
                            	
                            	{{instance.openprice}}
                            </td>
                            
                            	
                            		<td data-sortable="'volume'"  data-title="'volume'"  >
                            	
                            	{{instance.volume}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
