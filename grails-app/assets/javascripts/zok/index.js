'use strict';
var zok = angular.module('zok', ['services','ngRoute']);
zok.config([
    '$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/dashboard', {templateUrl: '/zok/auth/dashboard', controller: 'UserCtrl'}).
            when('/login', {templateUrl: '/zok/auth/showLogin', controller: 'UserCtrl'}).
            when('/signup', {templateUrl: '/zok/auth/showSignup', controller: 'UserCtrl'}).
            when('/updatepassword', {templateUrl: '/zok/auth/showUpdatePassword', controller: 'UserCtrl'}).
            when('/updateusername', {templateUrl: '/zok/auth/showUpdateUsername', controller: 'UserCtrl'}).
            when('/confirmupdate', {templateUrl: '/zok/auth/showUpdated', controller: 'UserCtrl'}).
            when('/club/create', {templateUrl: '/zok/club/edit', controller: 'ClubCtrl'}).
            when('/club/edit', {templateUrl: '/zok/club/edit', controller: 'ClubCtrl'}).
            when('/club/list', {templateUrl: '/zok/club/listing', controller: 'ClubCtrl'}).
            when('/club', {templateUrl: '/zok/club/listing', controller: 'ClubCtrl'}).
            when('/member/create', {templateUrl: '/zok/member/edit', controller: 'MemberCtrl'}).
            when('/member/edit', {templateUrl: '/zok/member/edit', controller: 'MemberCtrl'}).
            when('/member/list', {templateUrl: '/zok/member/listing', controller: 'MemberCtrl'}).
            when('/member', {templateUrl: '/zok/member/listing', controller: 'MemberCtrl'}).
            when('/stock/create', {templateUrl: '/zok/stock/edit', controller: 'StockCtrl'}).
            when('/stock/edit', {templateUrl: '/zok/stock/edit', controller: 'StockCtrl'}).
            when('/stock/list', {templateUrl: '/zok/stock/listing', controller: 'StockCtrl'}).
            when('/stock', {templateUrl: '/zok/stock/listing', controller: 'StockCtrl'}).
            when('/entry/create', {templateUrl: '/zok/entry/edit', controller: 'EntryCtrl'}).
            when('/entry/edit', {templateUrl: '/zok/entry/edit', controller: 'EntryCtrl'}).
            when('/entry/list', {templateUrl: '/zok/entry/listing', controller: 'EntryCtrl'}).
            when('/entry', {templateUrl: '/zok/entry/listing', controller: 'EntryCtrl'}).
            when('/forecast/create', {templateUrl: '/zok/forecast/edit', controller: 'ForecastCtrl'}).
            when('/forecast/edit', {templateUrl: '/zok/forecast/edit', controller: 'ForecastCtrl'}).
            when('/forecast/list', {templateUrl: '/zok/forecast/listing', controller: 'ForecastCtrl'}).
            when('/forecast', {templateUrl: '/zok/forecast/listing', controller: 'ForecastCtrl'}).
            when('/vanilla/create', {templateUrl: '/zok/vanilla/edit', controller: 'VanillaCtrl'}).
            when('/vanilla/edit', {templateUrl: '/zok/vanilla/edit', controller: 'VanillaCtrl'}).
            when('/vanilla/list', {templateUrl: '/zok/vanilla/listing', controller: 'VanillaCtrl'}).
            when('/vanilla', {templateUrl: '/zok/vanilla/listing', controller: 'VanillaCtrl'}).
            when('/instrument/create', {templateUrl: '/zok/instrument/edit', controller: 'InstrumentCtrl'}).
            when('/instrument/edit', {templateUrl: '/zok/instrument/edit', controller: 'InstrumentCtrl'}).
            when('/instrument/list', {templateUrl: '/zok/instrument/listing', controller: 'InstrumentCtrl'}).
            when('/instrument', {templateUrl: '/zok/instrument/listing', controller: 'InstrumentCtrl'}).
            when('/trade/create', {templateUrl: '/zok/trade/edit', controller: 'TradeCtrl'}).
            when('/trade/edit', {templateUrl: '/zok/trade/edit', controller: 'TradeCtrl'}).
            when('/trade/list', {templateUrl: '/zok/trade/listing', controller: 'TradeCtrl'}).
            when('/trade', {templateUrl: '/zok/trade/listing', controller: 'TradeCtrl'}).
            when('/portfolio/create', {templateUrl: '/zok/portfolio/edit', controller: 'PortfolioCtrl'}).
            when('/portfolio/edit', {templateUrl: '/zok/portfolio/edit', controller: 'PortfolioCtrl'}).
            when('/portfolio/list', {templateUrl: '/zok/portfolio/listing', controller: 'PortfolioCtrl'}).
            when('/portfolio', {templateUrl: '/zok/portfolio/listing', controller: 'PortfolioCtrl'}).
            when('/bet/create', {templateUrl: '/zok/bet/edit', controller: 'BetCtrl'}).
            when('/bet/edit', {templateUrl: '/zok/bet/edit', controller: 'BetCtrl'}).
            when('/bet/list', {templateUrl: '/zok/bet/listing', controller: 'BetCtrl'}).
            when('/bet', {templateUrl: '/zok/bet/listing', controller: 'BetCtrl'}).
            when('/daily/create', {templateUrl: '/zok/daily/edit', controller: 'DailyCtrl'}).
            when('/daily/edit', {templateUrl: '/zok/daily/edit', controller: 'DailyCtrl'}).
            when('/daily/list', {templateUrl: '/zok/daily/listing', controller: 'DailyCtrl'}).
            when('/daily', {templateUrl: '/zok/daily/listing', controller: 'DailyCtrl'}).
            when('/signal/create', {templateUrl: '/zok/signal/edit', controller: 'SignalCtrl'}).
            when('/signal/edit', {templateUrl: '/zok/signal/edit', controller: 'SignalCtrl'}).
            when('/signal/list', {templateUrl: '/zok/signal/listing', controller: 'SignalCtrl'}).
            when('/signal', {templateUrl: '/zok/signal/listing', controller: 'SignalCtrl'}).
            when('/strategySubscription/create', {templateUrl: '/zok/strategySubscription/edit', controller: 'StrategySubscriptionCtrl'}).
            when('/strategySubscription/edit', {templateUrl: '/zok/strategySubscription/edit', controller: 'StrategySubscriptionCtrl'}).
            when('/strategySubscription/list', {templateUrl: '/zok/strategySubscription/listing', controller: 'StrategySubscriptionCtrl'}).
            when('/strategySubscription', {templateUrl: '/zok/strategySubscription/listing', controller: 'StrategySubscriptionCtrl'}).
            when('/strategyExecution/create', {templateUrl: '/zok/strategyExecution/edit', controller: 'StrategyExecutionCtrl'}).
            when('/strategyExecution/edit', {templateUrl: '/zok/strategyExecution/edit', controller: 'StrategyExecutionCtrl'}).
            when('/strategyExecution/list', {templateUrl: '/zok/strategyExecution/listing', controller: 'StrategyExecutionCtrl'}).
            when('/strategyExecution', {templateUrl: '/zok/strategyExecution/listing', controller: 'StrategyExecutionCtrl'}).
            when('/strategyCatalog/create', {templateUrl: '/zok/strategyCatalog/edit', controller: 'StrategyCatalogCtrl'}).
            when('/strategyCatalog/edit', {templateUrl: '/zok/strategyCatalog/edit', controller: 'StrategyCatalogCtrl'}).
            when('/strategyCatalog/list', {templateUrl: '/zok/strategyCatalog/listing', controller: 'StrategyCatalogCtrl'}).
            when('/strategyCatalog', {templateUrl: '/zok/strategyCatalog/listing', controller: 'StrategyCatalogCtrl'}).
            when('/userIndicators/create', {templateUrl: '/zok/userIndicators/edit', controller: 'UserIndicatorsCtrl'}).
            when('/userIndicators/edit', {templateUrl: '/zok/userIndicators/edit', controller: 'UserIndicatorsCtrl'}).
            when('/userIndicators/list', {templateUrl: '/zok/userIndicators/listing', controller: 'UserIndicatorsCtrl'}).
            when('/userIndicators', {templateUrl: '/zok/userIndicators/listing', controller: 'UserIndicatorsCtrl'}).
            otherwise({redirectTo: '/login'});
    }
]);
