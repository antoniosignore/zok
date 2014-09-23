

 	<div data-ng-show="errors.showErrors" class="red">
		<div ng-repeat="error in errors.errorMessages">
			<strong></strong> <span ng-bind="error"></span>
		</div>
	</div>
    

    <div class="container" data-ng-controller="TradeCtrl" >
        <div class="small-12 columns" >
            <div class="panel panel-default">
                <div class="panel-body">
                    
                     <h5 class="text-center">Trade Edit</h5>
                    
                    

	
    <form name="tradeForm"  novalidate>
    
    <div>
    <p></p>
        <a class="btn btn-primary btn-primary" data-ng-click="newTrade()"><span class="glyphicon glyphicon-plus"></span><g:message code="default.new.label" args="['trade','BB']" default="New trade"/></a>
        <a class="btn btn-primary btn-primary" onclick="window.location.href = '#/trade/list'"  title="${message(code: 'default.list.label',args:['Trade'], default: 'List')}"><span class="glyphicon glyphicon-align-justify"></span> 	<g:message code="default.list.label" args="['Trade']" default="List"/></a>
        <a class="btn btn-primary btn-success" data-ng-hide="trade.id"  ng-disabled="tradeForm.$invalid" title="${message(code: 'default.save.label',args:['Trade'], default: 'Save')}" ng-enabled="!tradeForm.$invalid" data-ng-click="manualSaveTrade()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.save.label" args="['Trade']" default="Save"/></a>
        <a class="btn btn-primary btn-success" data-ng-show="trade.id"  ng-disabled="tradeForm.$invalid" title="${message(code: 'default.update.label',args:['Trade'], default: 'Update')}" ng-enabled="!tradeForm.$invalid" data-ng-click="manualSaveTrade()"><span class="glyphicon glyphicon-floppy-disk"></span> <g:message code="default.update.label" args="['Trade']" default="Update"/></a>
        <a class="btn btn-primary btn-danger" data-ng-show="trade.id"  title="${message(code: 'default.delete.label',args:['Trade'], default: 'Delete')}" data-ng-click="confirmDeleteTrade()"><span class="glyphicon glyphicon-trash"></span> <g:message code="default.delete.label" args="['Trade']" default="Delete"/></a>
        
   </p>
    </div>
    
    <div>
    
    
    <div>
        
        <div class="form-group">
            <label class="control-label" for="amount">
                amount 
            </label>
            <div class="input-group"><input class="form-control" name="amount" type="number" data-ng-model='trade.amount'  required="required"/>
		<div class="error" ng-show="tradeForm.amount.$dirty && tradeForm.amount.$invalid">
		<small class="error" ng-show="!tradeForm.amount.$pristine && tradeForm.amount.$invalid"><g:message code="default.invalid.label" args="['amount']" default=" Invalid amount : "/> </small>
		<small class="error" ng-show="tradeForm.amount.$error.number"><g:message code="default.invalid.number.label" args="['amount']" default=" Invalid amount "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="blog">
                blog 
            </label>
            <div class="input-group"><input type="text" class="form-control" name="blog" required="required" data-ng-model='trade.blog' />
		<div class="error" ng-show="tradeForm.blog.$dirty && tradeForm.blog.$invalid">
		<small class="error" ng-show="!tradeForm.blog.$pristine && tradeForm.blog.$invalid"><g:message code="default.invalid.label" args="['blog']" default=" Invalid blog : "/> </small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="cost">
                cost 
            </label>
            <div class="input-group"><input class="form-control" name="cost" data-ng-model='trade.cost'  required="required"/>
		<div class="error" ng-show="tradeForm.cost.$dirty && tradeForm.cost.$invalid">
		<small class="error" ng-show="!tradeForm.cost.$pristine && tradeForm.cost.$invalid"><g:message code="default.invalid.label" args="['cost']" default=" Invalid cost : "/> </small>
		<small class="error" ng-show="tradeForm.cost.$error.number"><g:message code="default.invalid.number.label" args="['cost']" default=" Invalid cost "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="instrument">
                instrument 
            </label>
            <select id="instrument" name="instrument.id" data-ng-model='trade.instrument' data-ng-controller="InstrumentCtrl" data-ng-init="getAllInstrument()" ng-options="c.id for c in instruments track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="portfolio">
                portfolio 
            </label>
            <select id="portfolio" name="portfolio.id" data-ng-model='trade.portfolio' data-ng-controller="PortfolioCtrl" data-ng-init="getAllPortfolio()" ng-options="c.id for c in portfolios track by c.id" required="" class="many-to-one"/>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="price">
                price 
            </label>
            <div class="input-group"><input class="form-control" name="price" data-ng-model='trade.price'  required="required"/>
		<div class="error" ng-show="tradeForm.price.$dirty && tradeForm.price.$invalid">
		<small class="error" ng-show="!tradeForm.price.$pristine && tradeForm.price.$invalid"><g:message code="default.invalid.label" args="['price']" default=" Invalid price : "/> </small>
		<small class="error" ng-show="tradeForm.price.$error.number"><g:message code="default.invalid.number.label" args="['price']" default=" Invalid price "/></small></div></div>
        </div>
        
        <div class="form-group">
            <label class="control-label" for="tradeAction">
                tradeAction 
            </label>
            <select name="tradeAction" from="${com.netnumeri.server.finance.beans.TradeEnum?.values()}" keys="${com.netnumeri.server.finance.beans.TradeEnum.values()*.name()}" required="" value="${trade?.tradeAction?.name()}" />
        </div>
        
        <div class="form-group">
            <label class="control-label" for="tradeDate">
                tradeDate 
            </label>
            <input type="date" name="tradeDate" precision="null" data-ng-model='trade.tradeDate' />
        </div>
        
        
    </div>
    </div>
    </form>
    </div>
</div>
</div>
</div>
