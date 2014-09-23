

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="VanillaCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Vanilla Edit</h5>
                    
                    

	
    <form name="vanillaForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newVanilla()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['vanilla','BB']" default="New vanilla"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/vanilla/list'"  title="${message(code: 'default.list.label',args:['Vanilla'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Vanilla']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="vanilla.id"  ng-disabled="vanillaForm.$invalid" title="${message(code: 'default.save.label',args:['Vanilla'], default: 'Save')}" ng-enabled="!vanillaForm.$invalid" data-ng-click="manualSaveVanilla()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Vanilla']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="vanilla.id"  ng-disabled="vanillaForm.$invalid" title="${message(code: 'default.update.label',args:['Vanilla'], default: 'Update')}" ng-enabled="!vanillaForm.$invalid" data-ng-click="manualSaveVanilla()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Vanilla']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="vanilla.id"  title="${message(code: 'default.delete.label',args:['Vanilla'], default: 'Delete')}" data-ng-click="confirmDeleteVanilla()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Vanilla']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="change">
                change 
            </label>
            <div class="input-group"><input class="form-control" name="change" data-ng-model='vanilla.change'  required="required"/>
		<div class="error" ng-show="vanillaForm.change.$dirty && vanillaForm.change.$invalid">
		<small class="error" ng-show="!vanillaForm.change.$pristine && vanillaForm.change.$invalid"><g:message code="default.invalid.label" args="['change']" default=" Invalid change : "/> </small>
		<small class="error" ng-show="vanillaForm.change.$error.number"><g:message code="default.invalid.number.label" args="['change']" default=" Invalid change "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="contractSize">
                contractSize 
            </label>
            <div class="input-group"><input class="form-control" name="contractSize" type="number" data-ng-model='vanilla.contractSize'  required="required"/>
		<div class="error" ng-show="vanillaForm.contractSize.$dirty && vanillaForm.contractSize.$invalid">
		<small class="error" ng-show="!vanillaForm.contractSize.$pristine && vanillaForm.contractSize.$invalid"><g:message code="default.invalid.label" args="['contractSize']" default=" Invalid contractSize : "/> </small>
		<small class="error" ng-show="vanillaForm.contractSize.$error.number"><g:message code="default.invalid.number.label" args="['contractSize']" default=" Invalid contractSize "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='vanilla.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="dividend">
                dividend 
            </label>
            <div class="input-group"><input class="form-control" name="dividend" data-ng-model='vanilla.dividend'  required="required"/>
		<div class="error" ng-show="vanillaForm.dividend.$dirty && vanillaForm.dividend.$invalid">
		<small class="error" ng-show="!vanillaForm.dividend.$pristine && vanillaForm.dividend.$invalid"><g:message code="default.invalid.label" args="['dividend']" default=" Invalid dividend : "/> </small>
		<small class="error" ng-show="vanillaForm.dividend.$error.number"><g:message code="default.invalid.number.label" args="['dividend']" default=" Invalid dividend "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="expiration">
                expiration 
            </label>
            <input type="date" name="expiration" precision="null" data-ng-model='vanilla.expiration' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="interestRate">
                interestRate 
            </label>
            <div class="input-group"><input class="form-control" name="interestRate" data-ng-model='vanilla.interestRate'  required="required"/>
		<div class="error" ng-show="vanillaForm.interestRate.$dirty && vanillaForm.interestRate.$invalid">
		<small class="error" ng-show="!vanillaForm.interestRate.$pristine && vanillaForm.interestRate.$invalid"><g:message code="default.invalid.label" args="['interestRate']" default=" Invalid interestRate : "/> </small>
		<small class="error" ng-show="vanillaForm.interestRate.$error.number"><g:message code="default.invalid.number.label" args="['interestRate']" default=" Invalid interestRate "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='vanilla.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='vanilla.name' />
		<div class="error" ng-show="vanillaForm.name.$dirty && vanillaForm.name.$invalid">
		<small class="error" ng-show="!vanillaForm.name.$pristine && vanillaForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="openInterest">
                openInterest 
            </label>
            <div class="input-group"><input class="form-control" name="openInterest" type="number" data-ng-model='vanilla.openInterest'  required="required"/>
		<div class="error" ng-show="vanillaForm.openInterest.$dirty && vanillaForm.openInterest.$invalid">
		<small class="error" ng-show="!vanillaForm.openInterest.$pristine && vanillaForm.openInterest.$invalid"><g:message code="default.invalid.label" args="['openInterest']" default=" Invalid openInterest : "/> </small>
		<small class="error" ng-show="vanillaForm.openInterest.$error.number"><g:message code="default.invalid.number.label" args="['openInterest']" default=" Invalid openInterest "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="premium">
                premium 
            </label>
            <div class="input-group"><input class="form-control" name="premium" data-ng-model='vanilla.premium'  required="required"/>
		<div class="error" ng-show="vanillaForm.premium.$dirty && vanillaForm.premium.$invalid">
		<small class="error" ng-show="!vanillaForm.premium.$pristine && vanillaForm.premium.$invalid"><g:message code="default.invalid.label" args="['premium']" default=" Invalid premium : "/> </small>
		<small class="error" ng-show="vanillaForm.premium.$error.number"><g:message code="default.invalid.number.label" args="['premium']" default=" Invalid premium "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="strike">
                strike 
            </label>
            <div class="input-group"><input class="form-control" name="strike" data-ng-model='vanilla.strike'  required="required"/>
		<div class="error" ng-show="vanillaForm.strike.$dirty && vanillaForm.strike.$invalid">
		<small class="error" ng-show="!vanillaForm.strike.$pristine && vanillaForm.strike.$invalid"><g:message code="default.invalid.label" args="['strike']" default=" Invalid strike : "/> </small>
		<small class="error" ng-show="vanillaForm.strike.$error.number"><g:message code="default.invalid.number.label" args="['strike']" default=" Invalid strike "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="type">
                type 
            </label>
            <select name="type" from="${com.netnumeri.server.enums.OptionType?.values()}" keys="${com.netnumeri.server.enums.OptionType.values()*.name()}" required="" value="${vanilla?.type?.name()}" />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="underlying">
                underlying 
            </label>
            <select id="underlying" name="underlying.id" data-ng-model='vanilla.underlying' data-ng-controller="InstrumentCtrl" data-ng-init="getAllInstrument()" ng-options="c.id for c in instruments track by c.id" required="" class="many-to-one"/>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
