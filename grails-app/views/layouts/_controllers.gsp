
<div class="container-fluid" data-ng-controller="UserCtrl"  data-ng-show="appConfig.token!=''">
	 <a  ng-class="isSelected('club')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('club')" onclick='window.location.href="#/club/list"' title="${message(code: 'default.club.label', default: 'Club')}">
		<g:message code="default.club.label"  default="Club"/>
	</a>
	 <a  ng-class="isSelected('member')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('member')" onclick='window.location.href="#/member/list"' title="${message(code: 'default.member.label', default: 'Member')}">
		<g:message code="default.member.label"  default="Member"/>
	</a>
	 <a  ng-class="isSelected('stock')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('stock')" onclick='window.location.href="#/stock/list"' title="${message(code: 'default.stock.label', default: 'Stock')}">
		<g:message code="default.stock.label"  default="Stock"/>
	</a>
	 <a  ng-class="isSelected('entry')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('entry')" onclick='window.location.href="#/entry/list"' title="${message(code: 'default.entry.label', default: 'Entry')}">
		<g:message code="default.entry}.label"  default="Entry"/>
	</a>
	 <a  ng-class="isSelected('forecast')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('forecast')" onclick='window.location.href="#/forecast/list"' title="${message(code: 'default.forecast.label', default: 'Forecast')}">
		<g:message code="default.forecast}.label"  default="Forecast"/>
	</a>
	 <a  ng-class="isSelected('vanilla')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('vanilla')" onclick='window.location.href="#/vanilla/list"' title="${message(code: 'default.vanilla.label', default: 'Vanilla')}">
		<g:message code="default.vanilla}.label"  default="Vanilla"/>
	</a>
	 <a  ng-class="isSelected('instrument')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('instrument')" onclick='window.location.href="#/instrument/list"' title="${message(code: 'default.instrument.label', default: 'Instrument')}">
		<g:message code="default.instrument}.label"  default="Instrument"/>
	</a>
	 <a  ng-class="isSelected('trade')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('trade')" onclick='window.location.href="#/trade/list"' title="${message(code: 'default.trade.label', default: 'Trade')}">
		<g:message code="default.trade}.label"  default="Trade"/>
	</a>
	 <a  ng-class="isSelected('portfolio')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('portfolio')" onclick='window.location.href="#/portfolio/list"' title="${message(code: 'default.portfolio.label', default: 'Portfolio')}">
		<g:message code="default.portfolio}.label"  default="Portfolio"/>
	</a>
	 <a  ng-class="isSelected('bet')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('bet')" onclick='window.location.href="#/bet/list"' title="${message(code: 'default.bet.label', default: 'Bet')}">
		<g:message code="default.bet}.label"  default="Bet"/>
	</a>
	 <a  ng-class="isSelected('daily')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('daily')" onclick='window.location.href="#/daily/list"' title="${message(code: 'default.daily.label', default: 'Daily')}">
		<g:message code="default.daily}.label"  default="Daily"/>
	</a>
	 <a  ng-class="isSelected('signal')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('signal')" onclick='window.location.href="#/signal/list"' title="${message(code: 'default.signal.label', default: 'Signal')}">
		<g:message code="default.signal}.label"  default="Signal"/>
	</a>
	 <a  ng-class="isSelected('strategySubscription')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('strategySubscription')" onclick='window.location.href="#/strategySubscription/list"' title="${message(code: 'default.strategySubscription.label', default: 'StrategySubscription')}">
		<g:message code="default.strategySubscription}.label"  default="StrategySubscription"/>
	</a>
	 <a  ng-class="isSelected('strategyExecution')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('strategyExecution')" onclick='window.location.href="#/strategyExecution/list"' title="${message(code: 'default.strategyExecution.label', default: 'StrategyExecution')}">
		<g:message code="default.strategyExecution}.label"  default="StrategyExecution"/>
	</a>
	 <a  ng-class="isSelected('strategyCatalog')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('strategyCatalog')" onclick='window.location.href="#/strategyCatalog/list"' title="${message(code: 'default.strategyCatalog.label', default: 'StrategyCatalog')}">
		<g:message code="default.strategyCatalog}.label"  default="StrategyCatalog"/>
	</a>
	 <a  ng-class="isSelected('userIndicators')? 'btn btn-primary' :'btn btn-default'"  ng-click="setSelectedController('userIndicators')" onclick='window.location.href="#/userIndicators/list"' title="${message(code: 'default.userIndicators.label', default: 'UserIndicators')}">
		<g:message code="default.userIndicators}.label"  default="UserIndicators"/>
	</a>

</div>
