

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="BetCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Bet Edit</h5>
                    
                    

	
    <form name="betForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newBet()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['bet','BB']" default="New bet"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/bet/list'"  title="${message(code: 'default.list.label',args:['Bet'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Bet']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="bet.id"  ng-disabled="betForm.$invalid" title="${message(code: 'default.save.label',args:['Bet'], default: 'Save')}" ng-enabled="!betForm.$invalid" data-ng-click="manualSaveBet()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Bet']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="bet.id"  ng-disabled="betForm.$invalid" title="${message(code: 'default.update.label',args:['Bet'], default: 'Update')}" ng-enabled="!betForm.$invalid" data-ng-click="manualSaveBet()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Bet']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="bet.id"  title="${message(code: 'default.delete.label',args:['Bet'], default: 'Delete')}" data-ng-click="confirmDeleteBet()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Bet']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='bet.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='bet.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='bet.name' />
		<div class="error" ng-show="betForm.name.$dirty && betForm.name.$invalid">
		<small class="error" ng-show="!betForm.name.$pristine && betForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="score">
                score 
            </label>
            <div class="input-group"><input class="form-control" name="score" data-ng-model='bet.score'  required="required"/>
		<div class="error" ng-show="betForm.score.$dirty && betForm.score.$invalid">
		<small class="error" ng-show="!betForm.score.$pristine && betForm.score.$invalid"><g:message code="default.invalid.label" args="['score']" default=" Invalid score : "/> </small>
		<small class="error" ng-show="betForm.score.$error.number"><g:message code="default.invalid.number.label" args="['score']" default=" Invalid score "/></small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
