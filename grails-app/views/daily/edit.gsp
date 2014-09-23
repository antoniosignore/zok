

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="DailyCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Daily Edit</h5>
                    
                    

	
    <form name="dailyForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newDaily()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['daily','BB']" default="New daily"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/daily/list'"  title="${message(code: 'default.list.label',args:['Daily'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Daily']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="daily.id"  ng-disabled="dailyForm.$invalid" title="${message(code: 'default.save.label',args:['Daily'], default: 'Save')}" ng-enabled="!dailyForm.$invalid" data-ng-click="manualSaveDaily()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Daily']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="daily.id"  ng-disabled="dailyForm.$invalid" title="${message(code: 'default.update.label',args:['Daily'], default: 'Update')}" ng-enabled="!dailyForm.$invalid" data-ng-click="manualSaveDaily()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Daily']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="daily.id"  title="${message(code: 'default.delete.label',args:['Daily'], default: 'Delete')}" data-ng-click="confirmDeleteDaily()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Daily']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="closeprice">
                closeprice 
            </label>
            <div class="input-group"><input class="form-control" name="closeprice" data-ng-model='daily.closeprice'  required="required"/>
		<div class="error" ng-show="dailyForm.closeprice.$dirty && dailyForm.closeprice.$invalid">
		<small class="error" ng-show="!dailyForm.closeprice.$pristine && dailyForm.closeprice.$invalid"><g:message code="default.invalid.label" args="['closeprice']" default=" Invalid closeprice : "/> </small>
		<small class="error" ng-show="dailyForm.closeprice.$error.number"><g:message code="default.invalid.number.label" args="['closeprice']" default=" Invalid closeprice "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="dailydate">
                dailydate 
            </label>
            <input type="date" name="dailydate" precision="null" data-ng-model='daily.dailydate' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="high">
                high 
            </label>
            <div class="input-group"><input class="form-control" name="high" data-ng-model='daily.high'  required="required"/>
		<div class="error" ng-show="dailyForm.high.$dirty && dailyForm.high.$invalid">
		<small class="error" ng-show="!dailyForm.high.$pristine && dailyForm.high.$invalid"><g:message code="default.invalid.label" args="['high']" default=" Invalid high : "/> </small>
		<small class="error" ng-show="dailyForm.high.$error.number"><g:message code="default.invalid.number.label" args="['high']" default=" Invalid high "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="instrument">
                instrument 
            </label>
            <select id="instrument" name="instrument.id" data-ng-model='daily.instrument' data-ng-controller="InstrumentCtrl" data-ng-init="getAllInstrument()" ng-options="c.id for c in instruments track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="low">
                low 
            </label>
            <div class="input-group"><input class="form-control" name="low" data-ng-model='daily.low'  required="required"/>
		<div class="error" ng-show="dailyForm.low.$dirty && dailyForm.low.$invalid">
		<small class="error" ng-show="!dailyForm.low.$pristine && dailyForm.low.$invalid"><g:message code="default.invalid.label" args="['low']" default=" Invalid low : "/> </small>
		<small class="error" ng-show="dailyForm.low.$error.number"><g:message code="default.invalid.number.label" args="['low']" default=" Invalid low "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="openInterest">
                openInterest 
            </label>
            <div class="input-group"><input class="form-control" name="openInterest" type="number" data-ng-model='daily.openInterest'  required="required"/>
		<div class="error" ng-show="dailyForm.openInterest.$dirty && dailyForm.openInterest.$invalid">
		<small class="error" ng-show="!dailyForm.openInterest.$pristine && dailyForm.openInterest.$invalid"><g:message code="default.invalid.label" args="['openInterest']" default=" Invalid openInterest : "/> </small>
		<small class="error" ng-show="dailyForm.openInterest.$error.number"><g:message code="default.invalid.number.label" args="['openInterest']" default=" Invalid openInterest "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="openprice">
                openprice 
            </label>
            <div class="input-group"><input class="form-control" name="openprice" data-ng-model='daily.openprice'  required="required"/>
		<div class="error" ng-show="dailyForm.openprice.$dirty && dailyForm.openprice.$invalid">
		<small class="error" ng-show="!dailyForm.openprice.$pristine && dailyForm.openprice.$invalid"><g:message code="default.invalid.label" args="['openprice']" default=" Invalid openprice : "/> </small>
		<small class="error" ng-show="dailyForm.openprice.$error.number"><g:message code="default.invalid.number.label" args="['openprice']" default=" Invalid openprice "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="volume">
                volume 
            </label>
            <div class="input-group"><input class="form-control" name="volume" type="number" data-ng-model='daily.volume'  required="required"/>
		<div class="error" ng-show="dailyForm.volume.$dirty && dailyForm.volume.$invalid">
		<small class="error" ng-show="!dailyForm.volume.$pristine && dailyForm.volume.$invalid"><g:message code="default.invalid.label" args="['volume']" default=" Invalid volume : "/> </small>
		<small class="error" ng-show="dailyForm.volume.$error.number"><g:message code="default.invalid.number.label" args="['volume']" default=" Invalid volume "/></small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
