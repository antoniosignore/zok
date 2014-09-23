

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="ForecastCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Forecast Edit</h5>
                    
                    

	
    <form name="forecastForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newForecast()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['forecast','BB']" default="New forecast"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/forecast/list'"  title="${message(code: 'default.list.label',args:['Forecast'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Forecast']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="forecast.id"  ng-disabled="forecastForm.$invalid" title="${message(code: 'default.save.label',args:['Forecast'], default: 'Save')}" ng-enabled="!forecastForm.$invalid" data-ng-click="manualSaveForecast()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Forecast']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="forecast.id"  ng-disabled="forecastForm.$invalid" title="${message(code: 'default.update.label',args:['Forecast'], default: 'Update')}" ng-enabled="!forecastForm.$invalid" data-ng-click="manualSaveForecast()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Forecast']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="forecast.id"  title="${message(code: 'default.delete.label',args:['Forecast'], default: 'Delete')}" data-ng-click="confirmDeleteForecast()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Forecast']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="bet">
                bet 
            </label>
            <select id="bet" name="bet.id" data-ng-model='forecast.bet' data-ng-controller="BetCtrl" data-ng-init="getAllBet()" ng-options="c.id for c in bets track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='forecast.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='forecast.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='forecast.name' />
		<div class="error" ng-show="forecastForm.name.$dirty && forecastForm.name.$invalid">
		<small class="error" ng-show="!forecastForm.name.$pristine && forecastForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="percent">
                percent 
            </label>
            <div class="input-group"><input class="form-control" name="percent" data-ng-model='forecast.percent'  required="required"/>
		<div class="error" ng-show="forecastForm.percent.$dirty && forecastForm.percent.$invalid">
		<small class="error" ng-show="!forecastForm.percent.$pristine && forecastForm.percent.$invalid"><g:message code="default.invalid.label" args="['percent']" default=" Invalid percent : "/> </small>
		<small class="error" ng-show="forecastForm.percent.$error.number"><g:message code="default.invalid.number.label" args="['percent']" default=" Invalid percent "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="ticker">
                ticker 
            </label>
            <select id="ticker" name="ticker.id" data-ng-model='forecast.ticker' data-ng-controller="StockCtrl" data-ng-init="getAllStock()" ng-options="c.id for c in stocks track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="user">
                user 
            </label>
            <select id="user" name="user.id" data-ng-model='forecast.user' data-ng-controller="MemberCtrl" data-ng-init="getAllMember()" ng-options="c.id for c in members track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="when">
                when 
            </label>
            <input type="date" name="when" precision="null" data-ng-model='forecast.when' />
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
