

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="StrategySubscriptionCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">StrategySubscription Edit</h5>
                    
                    

	
    <form name="strategySubscriptionForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newStrategySubscription()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['strategySubscription','BB']" default="New strategySubscription"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/strategySubscription/list'"  title="${message(code: 'default.list.label',args:['StrategySubscription'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['StrategySubscription']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="strategySubscription.id"  ng-disabled="strategySubscriptionForm.$invalid" title="${message(code: 'default.save.label',args:['StrategySubscription'], default: 'Save')}" ng-enabled="!strategySubscriptionForm.$invalid" data-ng-click="manualSaveStrategySubscription()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['StrategySubscription']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="strategySubscription.id"  ng-disabled="strategySubscriptionForm.$invalid" title="${message(code: 'default.update.label',args:['StrategySubscription'], default: 'Update')}" ng-enabled="!strategySubscriptionForm.$invalid" data-ng-click="manualSaveStrategySubscription()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['StrategySubscription']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="strategySubscription.id"  title="${message(code: 'default.delete.label',args:['StrategySubscription'], default: 'Delete')}" data-ng-click="confirmDeleteStrategySubscription()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['StrategySubscription']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='strategySubscription.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='strategySubscription.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="strategy">
                strategy 
            </label>
            <select id="strategy" name="strategy.id" data-ng-model='strategySubscription.strategy' data-ng-controller="StrategyCatalogCtrl" data-ng-init="getAllStrategyCatalog()" ng-options="c.id for c in strategycatalogs track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="user">
                user 
            </label>
            <select id="user" name="user.id" data-ng-model='strategySubscription.user' data-ng-controller="MemberCtrl" data-ng-init="getAllMember()" ng-options="c.id for c in members track by c.id" required="" class="many-to-one"/>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
