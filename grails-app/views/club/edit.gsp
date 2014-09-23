

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="ClubCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Club Edit</h5>
                    
                    

	
    <form name="clubForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newClub()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['club','BB']" default="New club"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/club/list'"  title="${message(code: 'default.list.label',args:['Club'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Club']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="club.id"  ng-disabled="clubForm.$invalid" title="${message(code: 'default.save.label',args:['Club'], default: 'Save')}" ng-enabled="!clubForm.$invalid" data-ng-click="manualSaveClub()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Club']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="club.id"  ng-disabled="clubForm.$invalid" title="${message(code: 'default.update.label',args:['Club'], default: 'Update')}" ng-enabled="!clubForm.$invalid" data-ng-click="manualSaveClub()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Club']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="club.id"  title="${message(code: 'default.delete.label',args:['Club'], default: 'Delete')}" data-ng-click="confirmDeleteClub()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Club']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="agreement">
                agreement 
            </label>
            <input type="file" id="agreement" data-ng-model='club.agreement'  name="agreement" />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='club.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="inauguralMeeting">
                inauguralMeeting 
            </label>
            <input type="date" name="inauguralMeeting" precision="null" data-ng-model='club.inauguralMeeting' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="joiningFee">
                joiningFee 
            </label>
            <div class="input-group"><input class="form-control" name="joiningFee" data-ng-model='club.joiningFee'  required="required"/>
		<div class="error" ng-show="clubForm.joiningFee.$dirty && clubForm.joiningFee.$invalid">
		<small class="error" ng-show="!clubForm.joiningFee.$pristine && clubForm.joiningFee.$invalid"><g:message code="default.invalid.label" args="['joiningFee']" default=" Invalid joiningFee : "/> </small>
		<small class="error" ng-show="clubForm.joiningFee.$error.number"><g:message code="default.invalid.number.label" args="['joiningFee']" default=" Invalid joiningFee "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='club.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="monthlySubscription">
                monthlySubscription 
            </label>
            <div class="input-group"><input class="form-control" name="monthlySubscription" data-ng-model='club.monthlySubscription'  required="required"/>
		<div class="error" ng-show="clubForm.monthlySubscription.$dirty && clubForm.monthlySubscription.$invalid">
		<small class="error" ng-show="!clubForm.monthlySubscription.$pristine && clubForm.monthlySubscription.$invalid"><g:message code="default.invalid.label" args="['monthlySubscription']" default=" Invalid monthlySubscription : "/> </small>
		<small class="error" ng-show="clubForm.monthlySubscription.$error.number"><g:message code="default.invalid.number.label" args="['monthlySubscription']" default=" Invalid monthlySubscription "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='club.name' />
		<div class="error" ng-show="clubForm.name.$dirty && clubForm.name.$invalid">
		<small class="error" ng-show="!clubForm.name.$pristine && clubForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="yearsTimeSpan">
                yearsTimeSpan 
            </label>
            <div class="input-group"><input class="form-control" name="yearsTimeSpan" type="number" data-ng-model='club.yearsTimeSpan'  required="required"/>
		<div class="error" ng-show="clubForm.yearsTimeSpan.$dirty && clubForm.yearsTimeSpan.$invalid">
		<small class="error" ng-show="!clubForm.yearsTimeSpan.$pristine && clubForm.yearsTimeSpan.$invalid"><g:message code="default.invalid.label" args="['yearsTimeSpan']" default=" Invalid yearsTimeSpan : "/> </small>
		<small class="error" ng-show="clubForm.yearsTimeSpan.$error.number"><g:message code="default.invalid.number.label" args="['yearsTimeSpan']" default=" Invalid yearsTimeSpan "/></small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
