

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="StrategyCatalogCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">StrategyCatalog Edit</h5>
                    
                    

	
    <form name="strategyCatalogForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newStrategyCatalog()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['strategyCatalog','BB']" default="New strategyCatalog"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/strategyCatalog/list'"  title="${message(code: 'default.list.label',args:['StrategyCatalog'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['StrategyCatalog']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="strategyCatalog.id"  ng-disabled="strategyCatalogForm.$invalid" title="${message(code: 'default.save.label',args:['StrategyCatalog'], default: 'Save')}" ng-enabled="!strategyCatalogForm.$invalid" data-ng-click="manualSaveStrategyCatalog()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['StrategyCatalog']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="strategyCatalog.id"  ng-disabled="strategyCatalogForm.$invalid" title="${message(code: 'default.update.label',args:['StrategyCatalog'], default: 'Update')}" ng-enabled="!strategyCatalogForm.$invalid" data-ng-click="manualSaveStrategyCatalog()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['StrategyCatalog']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="strategyCatalog.id"  title="${message(code: 'default.delete.label',args:['StrategyCatalog'], default: 'Delete')}" data-ng-click="confirmDeleteStrategyCatalog()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['StrategyCatalog']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='strategyCatalog.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='strategyCatalog.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='strategyCatalog.name' />
		<div class="error" ng-show="strategyCatalogForm.name.$dirty && strategyCatalogForm.name.$invalid">
		<small class="error" ng-show="!strategyCatalogForm.name.$pristine && strategyCatalogForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
