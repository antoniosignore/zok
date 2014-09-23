

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="StockCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Stock Edit</h5>
                    
                    

	
    <form name="stockForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newStock()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['stock','BB']" default="New stock"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/stock/list'"  title="${message(code: 'default.list.label',args:['Stock'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Stock']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="stock.id"  ng-disabled="stockForm.$invalid" title="${message(code: 'default.save.label',args:['Stock'], default: 'Save')}" ng-enabled="!stockForm.$invalid" data-ng-click="manualSaveStock()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Stock']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="stock.id"  ng-disabled="stockForm.$invalid" title="${message(code: 'default.update.label',args:['Stock'], default: 'Update')}" ng-enabled="!stockForm.$invalid" data-ng-click="manualSaveStock()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Stock']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="stock.id"  title="${message(code: 'default.delete.label',args:['Stock'], default: 'Delete')}" data-ng-click="confirmDeleteStock()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Stock']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='stock.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="description">
                description 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="description" required="required" data-ng-model='stock.description' />
		<div class="error" ng-show="stockForm.description.$dirty && stockForm.description.$invalid">
		<small class="error" ng-show="!stockForm.description.$pristine && stockForm.description.$invalid"><g:message code="default.invalid.label" args="['description']" default=" Invalid description : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='stock.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='stock.name' />
		<div class="error" ng-show="stockForm.name.$dirty && stockForm.name.$invalid">
		<small class="error" ng-show="!stockForm.name.$pristine && stockForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
