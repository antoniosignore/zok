
<div data-ng-controller="TradeCtrl" >
    <h1>Trade List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newTrade()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['trade']" default="New trade"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in trades"  data-ng-click="editTrade(instance)">
                            
                            	
                             		<td data-sortable="'amount'" filter="{'amount':'text'}"  data-title="'amount'"  >
                            	
                            	{{instance.amount}}
                            </td>
                            
                            	
                            		<td data-sortable="'blog'"  data-title="'blog'"  >
                            	
                            	{{instance.blog}}
                            </td>
                            
                            	
                            		<td data-sortable="'cost'"  data-title="'cost'"  >
                            	
                            	{{instance.cost}}
                            </td>
                            
                            	
                            		<td data-sortable="'instrument'"  data-title="'instrument'"  >
                            	
                            	{{instance.instrument}}
                            </td>
                            
                            	
                            		<td data-sortable="'portfolio'"  data-title="'portfolio'"  >
                            	
                            	{{instance.portfolio}}
                            </td>
                            
                            	
                            		<td data-sortable="'price'"  data-title="'price'"  >
                            	
                            	{{instance.price}}
                            </td>
                            
                            	
                            		<td data-sortable="'tradeAction'"  data-title="'tradeAction'"  >
                            	
                            	{{instance.tradeAction}}
                            </td>
                            
                            	
                            		<td data-sortable="'tradeDate'"  data-title="'tradeDate'"  >
                            	
                            	{{instance.tradeDate}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
