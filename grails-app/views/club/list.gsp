
<div data-ng-controller="ClubCtrl" >
    <h1>Club List</h1>
 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
	   
	
        <div>
            <p></p>
             <a class="btn btn-primary btn-primary" data-ng-click="newClub()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['club']" default="New club"/></a>
            <p></p>
        </div>
        <div>
            <div>
                <div>
                    
					<button ng-click="tableParams.reload()" class="btn btn-default">Reload</button>
    				<button ng-click="tableParams.sorting({})" class="btn btn-default">Clear sorting</button> 
					<div loading-container="tableParams.settings().$loading">
                    <table class="table"  ng-table="tableParams" show-filter="true">
                        <tr  data-ng-repeat="instance in clubs"  data-ng-click="editClub(instance)">
                            
                            	
                             		<td data-sortable="'agreement'" filter="{'agreement':'text'}"  data-title="'agreement'"  >
                            	
                            	{{instance.agreement}}
                            </td>
                            
                            	
                            		<td data-sortable="'dateCreated'"  data-title="'dateCreated'"  >
                            	
                            	{{instance.dateCreated}}
                            </td>
                            
                            	
                            		<td data-sortable="'inauguralMeeting'"  data-title="'inauguralMeeting'"  >
                            	
                            	{{instance.inauguralMeeting}}
                            </td>
                            
                            	
                            		<td data-sortable="'joiningFee'"  data-title="'joiningFee'"  >
                            	
                            	{{instance.joiningFee}}
                            </td>
                            
                            	
                            		<td data-sortable="'lastUpdated'"  data-title="'lastUpdated'"  >
                            	
                            	{{instance.lastUpdated}}
                            </td>
                            
                            	
                            		<td data-sortable="'monthlySubscription'"  data-title="'monthlySubscription'"  >
                            	
                            	{{instance.monthlySubscription}}
                            </td>
                            
                            	
                            		<td data-sortable="'name'"  data-title="'name'"  >
                            	
                            	{{instance.name}}
                            </td>
                            
                            	
                            		<td data-sortable="'yearsTimeSpan'"  data-title="'yearsTimeSpan'"  >
                            	
                            	{{instance.yearsTimeSpan}}
                            </td>
                            
                        </tr>
                    </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
