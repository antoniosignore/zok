

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="PortfolioCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Portfolio Edit</h5>
                    
                    

	
    <form name="portfolioForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newPortfolio()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['portfolio','BB']" default="New portfolio"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/portfolio/list'"  title="${message(code: 'default.list.label',args:['Portfolio'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Portfolio']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="portfolio.id"  ng-disabled="portfolioForm.$invalid" title="${message(code: 'default.save.label',args:['Portfolio'], default: 'Save')}" ng-enabled="!portfolioForm.$invalid" data-ng-click="manualSavePortfolio()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Portfolio']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="portfolio.id"  ng-disabled="portfolioForm.$invalid" title="${message(code: 'default.update.label',args:['Portfolio'], default: 'Update')}" ng-enabled="!portfolioForm.$invalid" data-ng-click="manualSavePortfolio()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Portfolio']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="portfolio.id"  title="${message(code: 'default.delete.label',args:['Portfolio'], default: 'Delete')}" data-ng-click="confirmDeletePortfolio()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Portfolio']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="dateCreated">
                dateCreated 
            </label>
            <input type="date" name="dateCreated" precision="null" data-ng-model='portfolio.dateCreated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="description">
                description 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="description" required="required" data-ng-model='portfolio.description' />
		<div class="error" ng-show="portfolioForm.description.$dirty && portfolioForm.description.$invalid">
		<small class="error" ng-show="!portfolioForm.description.$pristine && portfolioForm.description.$invalid"><g:message code="default.invalid.label" args="['description']" default=" Invalid description : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="firstDate">
                firstDate 
            </label>
            <input type="date" name="firstDate" precision="null" data-ng-model='portfolio.firstDate' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastDate">
                lastDate 
            </label>
            <input type="date" name="lastDate" precision="null" data-ng-model='portfolio.lastDate' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="lastUpdated">
                lastUpdated 
            </label>
            <input type="date" name="lastUpdated" precision="null" data-ng-model='portfolio.lastUpdated' />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="name">
                name 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="name" required="required" data-ng-model='portfolio.name' />
		<div class="error" ng-show="portfolioForm.name.$dirty && portfolioForm.name.$invalid">
		<small class="error" ng-show="!portfolioForm.name.$pristine && portfolioForm.name.$invalid"><g:message code="default.invalid.label" args="['name']" default=" Invalid name : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="portfolioType">
                portfolioType 
            </label>
            <select name="portfolioType" from="${com.netnumeri.server.enums.PortfolioTypeEnum?.values()}" keys="${com.netnumeri.server.enums.PortfolioTypeEnum.values()*.name()}" required="" value="${portfolio?.portfolioType?.name()}" />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="user">
                user 
            </label>
            <select id="user" name="user.id" data-ng-model='portfolio.user' data-ng-controller="MemberCtrl" data-ng-init="getAllMember()" ng-options="c.id for c in members track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="wealth">
                wealth 
            </label>
            <div class="input-group"><input class="form-control" name="wealth" data-ng-model='portfolio.wealth'  required="required"/>
		<div class="error" ng-show="portfolioForm.wealth.$dirty && portfolioForm.wealth.$invalid">
		<small class="error" ng-show="!portfolioForm.wealth.$pristine && portfolioForm.wealth.$invalid"><g:message code="default.invalid.label" args="['wealth']" default=" Invalid wealth : "/> </small>
		<small class="error" ng-show="portfolioForm.wealth.$error.number"><g:message code="default.invalid.number.label" args="['wealth']" default=" Invalid wealth "/></small></div></div>
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
