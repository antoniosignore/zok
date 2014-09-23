

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="InstrumentCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Instrument Edit</h5>
                    
                    

	
    <form name="instrumentForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newInstrument()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['instrument','BB']" default="New instrument"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/instrument/list'"  title="${message(code: 'default.list.label',args:['Instrument'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Instrument']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="instrument.id"  ng-disabled="instrumentForm.$invalid" title="${message(code: 'default.save.label',args:['Instrument'], default: 'Save')}" ng-enabled="!instrumentForm.$invalid" data-ng-click="manualSaveInstrument()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Instrument']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="instrument.id"  ng-disabled="instrumentForm.$invalid" title="${message(code: 'default.update.label',args:['Instrument'], default: 'Update')}" ng-enabled="!instrumentForm.$invalid" data-ng-click="manualSaveInstrument()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Instrument']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="instrument.id"  title="${message(code: 'default.delete.label',args:['Instrument'], default: 'Delete')}" data-ng-click="confirmDeleteInstrument()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Instrument']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='instrument.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='instrument.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='instrument.name' />
		<div class="error" ng-show="instrumentForm.name.$dirty && instrumentForm.name.$invalid">
		<small class="error" ng-show="!instrumentForm.name.$pristine && instrumentForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
