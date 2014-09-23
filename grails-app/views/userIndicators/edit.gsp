

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="UserIndicatorsCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">UserIndicators Edit</h5>
                    
                    

	
    <form name="userIndicatorsForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newUserIndicators()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['userIndicators','BB']" default="New userIndicators"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/userIndicators/list'"  title="${message(code: 'default.list.label',args:['UserIndicators'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['UserIndicators']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="userIndicators.id"  ng-disabled="userIndicatorsForm.$invalid" title="${message(code: 'default.save.label',args:['UserIndicators'], default: 'Save')}" ng-enabled="!userIndicatorsForm.$invalid" data-ng-click="manualSaveUserIndicators()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['UserIndicators']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="userIndicators.id"  ng-disabled="userIndicatorsForm.$invalid" title="${message(code: 'default.update.label',args:['UserIndicators'], default: 'Update')}" ng-enabled="!userIndicatorsForm.$invalid" data-ng-click="manualSaveUserIndicators()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['UserIndicators']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="userIndicators.id"  title="${message(code: 'default.delete.label',args:['UserIndicators'], default: 'Delete')}" data-ng-click="confirmDeleteUserIndicators()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['UserIndicators']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='userIndicators.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="double1">
                double1 
            </label>
            <div class="input-group"><input class="form-control" name="double1" data-ng-model='userIndicators.double1'  required="required"/>
		<div class="error" ng-show="userIndicatorsForm.double1.$dirty && userIndicatorsForm.double1.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.double1.$pristine && userIndicatorsForm.double1.$invalid"><g:message code="default.invalid.label" args="['double1']" default=" Invalid double1 : "/> </small>
		<small class="error" ng-show="userIndicatorsForm.double1.$error.number"><g:message code="default.invalid.number.label" args="['double1']" default=" Invalid double1 "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="double2">
                double2 
            </label>
            <div class="input-group"><input class="form-control" name="double2" data-ng-model='userIndicators.double2'  required="required"/>
		<div class="error" ng-show="userIndicatorsForm.double2.$dirty && userIndicatorsForm.double2.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.double2.$pristine && userIndicatorsForm.double2.$invalid"><g:message code="default.invalid.label" args="['double2']" default=" Invalid double2 : "/> </small>
		<small class="error" ng-show="userIndicatorsForm.double2.$error.number"><g:message code="default.invalid.number.label" args="['double2']" default=" Invalid double2 "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="integer1">
                integer1 
            </label>
            <div class="input-group"><input class="form-control" name="integer1" type="number" data-ng-model='userIndicators.integer1'  required="required"/>
		<div class="error" ng-show="userIndicatorsForm.integer1.$dirty && userIndicatorsForm.integer1.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.integer1.$pristine && userIndicatorsForm.integer1.$invalid"><g:message code="default.invalid.label" args="['integer1']" default=" Invalid integer1 : "/> </small>
		<small class="error" ng-show="userIndicatorsForm.integer1.$error.number"><g:message code="default.invalid.number.label" args="['integer1']" default=" Invalid integer1 "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="integer2">
                integer2 
            </label>
            <div class="input-group"><input class="form-control" name="integer2" type="number" data-ng-model='userIndicators.integer2'  required="required"/>
		<div class="error" ng-show="userIndicatorsForm.integer2.$dirty && userIndicatorsForm.integer2.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.integer2.$pristine && userIndicatorsForm.integer2.$invalid"><g:message code="default.invalid.label" args="['integer2']" default=" Invalid integer2 : "/> </small>
		<small class="error" ng-show="userIndicatorsForm.integer2.$error.number"><g:message code="default.invalid.number.label" args="['integer2']" default=" Invalid integer2 "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="integer3">
                integer3 
            </label>
            <div class="input-group"><input class="form-control" name="integer3" type="number" data-ng-model='userIndicators.integer3'  required="required"/>
		<div class="error" ng-show="userIndicatorsForm.integer3.$dirty && userIndicatorsForm.integer3.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.integer3.$pristine && userIndicatorsForm.integer3.$invalid"><g:message code="default.invalid.label" args="['integer3']" default=" Invalid integer3 : "/> </small>
		<small class="error" ng-show="userIndicatorsForm.integer3.$error.number"><g:message code="default.invalid.number.label" args="['integer3']" default=" Invalid integer3 "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='userIndicators.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='userIndicators.name' />
		<div class="error" ng-show="userIndicatorsForm.name.$dirty && userIndicatorsForm.name.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.name.$pristine && userIndicatorsForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="str1">
                str1 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="str1" required="required" data-ng-model='userIndicators.str1' />
		<div class="error" ng-show="userIndicatorsForm.str1.$dirty && userIndicatorsForm.str1.$invalid">
		<small class="error" ng-show="!userIndicatorsForm.str1.$pristine && userIndicatorsForm.str1.$invalid"><g:message code="default.invalid.label" args="['str1']" default=" Invalid str1 : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="type">
                type 
            </label>
            <select name="type" from="${com.netnumeri.server.enums.IndicatorEnum?.values()}" keys="${com.netnumeri.server.enums.IndicatorEnum.values()*.name()}" required="" value="${userIndicators?.type?.name()}" />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="user">
                user 
            </label>
            <select id="user" name="user.id" data-ng-model='userIndicators.user' data-ng-controller="MemberCtrl" data-ng-init="getAllMember()" ng-options="c.id for c in members track by c.id" required="" class="many-to-one"/>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
