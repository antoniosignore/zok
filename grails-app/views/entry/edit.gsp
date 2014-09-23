

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="EntryCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Entry Edit</h5>
                    
                    

	
    <form name="entryForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newEntry()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['entry','BB']" default="New entry"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/entry/list'"  title="${message(code: 'default.list.label',args:['Entry'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Entry']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="entry.id"  ng-disabled="entryForm.$invalid" title="${message(code: 'default.save.label',args:['Entry'], default: 'Save')}" ng-enabled="!entryForm.$invalid" data-ng-click="manualSaveEntry()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Entry']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="entry.id"  ng-disabled="entryForm.$invalid" title="${message(code: 'default.update.label',args:['Entry'], default: 'Update')}" ng-enabled="!entryForm.$invalid" data-ng-click="manualSaveEntry()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Entry']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="entry.id"  title="${message(code: 'default.delete.label',args:['Entry'], default: 'Delete')}" data-ng-click="confirmDeleteEntry()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Entry']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="amount">
                amount 
            </label>
            <div class="input-group"><input class="form-control" name="amount" type="number" data-ng-model='entry.amount'  required="required"/>
		<div class="error" ng-show="entryForm.amount.$dirty && entryForm.amount.$invalid">
		<small class="error" ng-show="!entryForm.amount.$pristine && entryForm.amount.$invalid"><g:message code="default.invalid.label" args="['amount']" default=" Invalid amount : "/> </small>
		<small class="error" ng-show="entryForm.amount.$error.number"><g:message code="default.invalid.number.label" args="['amount']" default=" Invalid amount "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="instrument">
                instrument 
            </label>
            <select id="instrument" name="instrument.id" data-ng-model='entry.instrument' data-ng-controller="InstrumentCtrl" data-ng-init="getAllInstrument()" ng-options="c.id for c in instruments track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="portfolio">
                portfolio 
            </label>
            <select id="portfolio" name="portfolio.id" data-ng-model='entry.portfolio' data-ng-controller="PortfolioCtrl" data-ng-init="getAllPortfolio()" ng-options="c.id for c in portfolios track by c.id" required="" class="many-to-one"/>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
