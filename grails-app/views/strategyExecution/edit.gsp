

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="StrategyExecutionCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">StrategyExecution Edit</h5>
                    
                    

	
    <form name="strategyExecutionForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newStrategyExecution()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['strategyExecution','BB']" default="New strategyExecution"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/strategyExecution/list'"  title="${message(code: 'default.list.label',args:['StrategyExecution'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['StrategyExecution']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="strategyExecution.id"  ng-disabled="strategyExecutionForm.$invalid" title="${message(code: 'default.save.label',args:['StrategyExecution'], default: 'Save')}" ng-enabled="!strategyExecutionForm.$invalid" data-ng-click="manualSaveStrategyExecution()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['StrategyExecution']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="strategyExecution.id"  ng-disabled="strategyExecutionForm.$invalid" title="${message(code: 'default.update.label',args:['StrategyExecution'], default: 'Update')}" ng-enabled="!strategyExecutionForm.$invalid" data-ng-click="manualSaveStrategyExecution()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['StrategyExecution']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="strategyExecution.id"  title="${message(code: 'default.delete.label',args:['StrategyExecution'], default: 'Delete')}" data-ng-click="confirmDeleteStrategyExecution()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['StrategyExecution']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='strategyExecution.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='strategyExecution.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='strategyExecution.name' />
		<div class="error" ng-show="strategyExecutionForm.name.$dirty && strategyExecutionForm.name.$invalid">
		<small class="error" ng-show="!strategyExecutionForm.name.$pristine && strategyExecutionForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
