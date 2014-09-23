

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="SignalCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Signal Edit</h5>
                    
                    

	
    <form name="signalForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newSignal()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['signal','BB']" default="New signal"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/signal/list'"  title="${message(code: 'default.list.label',args:['Signal'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Signal']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="signal.id"  ng-disabled="signalForm.$invalid" title="${message(code: 'default.save.label',args:['Signal'], default: 'Save')}" ng-enabled="!signalForm.$invalid" data-ng-click="manualSaveSignal()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Signal']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="signal.id"  ng-disabled="signalForm.$invalid" title="${message(code: 'default.update.label',args:['Signal'], default: 'Update')}" ng-enabled="!signalForm.$invalid" data-ng-click="manualSaveSignal()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Signal']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="signal.id"  title="${message(code: 'default.delete.label',args:['Signal'], default: 'Delete')}" data-ng-click="confirmDeleteSignal()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Signal']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='signal.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="day">
                day 
            </label>
            <input type="date" name="day" precision="null" data-ng-model='signal.day' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="direction">
                direction 
            </label>
            <select name="direction" from="${com.netnumeri.server.finance.beans.TradeEnum?.values()}" keys="${com.netnumeri.server.finance.beans.TradeEnum.values()*.name()}" required="" value="${signal?.direction?.name()}" />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="instrument">
                instrument 
            </label>
            null
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='signal.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='signal.name' />
		<div class="error" ng-show="signalForm.name.$dirty && signalForm.name.$invalid">
		<small class="error" ng-show="!signalForm.name.$pristine && signalForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="strategyExecution">
                strategyExecution 
            </label>
            <select id="strategyExecution" name="strategyExecution.id" data-ng-model='signal.strategyExecution' data-ng-controller="StrategyExecutionCtrl" data-ng-init="getAllStrategyExecution()" ng-options="c.id for c in strategyexecutions track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="value">
                value 
            </label>
            <div class="input-group"><input class="form-control" name="value" data-ng-model='signal.value'  required="required"/>
		<div class="error" ng-show="signalForm.value.$dirty && signalForm.value.$invalid">
		<small class="error" ng-show="!signalForm.value.$pristine && signalForm.value.$invalid"><g:message code="default.invalid.label" args="['value']" default=" Invalid value : "/> </small>
		<small class="error" ng-show="signalForm.value.$error.number"><g:message code="default.invalid.number.label" args="['value']" default=" Invalid value "/></small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
