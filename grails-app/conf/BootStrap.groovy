
import arrested.ArrestedToken
import com.dtmc.club.Club
import com.dtmc.club.Member
import com.dtmc.finance.finpojo.Portfolio
import com.dtmc.finance.finpojo.asset.Stock
import com.dtmc.indicator.UserIndicators
import com.netnumeri.server.enums.IndicatorEnum
import com.netnumeri.server.enums.PortfolioTypeEnum
import org.apache.shiro.crypto.hash.Sha256Hash

class BootStrap {

    def init = { servletContext ->

        Member adminUser
        ArrestedToken token

        Club club = new Club(name: 'ethical').save(flush: true, failOnError: true)

        adminUser = new Member(club: club, username: "admin",
                passwordHash: new Sha256Hash("admin").toHex(), dateCreated: new Date()).save(flush: true, failOnError: true)

        //Create tokens for users
        token = new ArrestedToken(token: 'token', valid: true, owner: adminUser.id).save(flush: true)
        adminUser.setToken(token.id)
        adminUser.save(flush: true, failOnError: true)

        new Stock("AAPL", "Apple Computers").save(flush: true, failOnError: true)
        new Stock("IBM", "International Business Machines").save(flush: true, failOnError: true)
        new Stock("SSRI", "Silver Standard Resources").save(flush: true, failOnError: true)

        Portfolio portfolio = new Portfolio(name: 'mio',description: 'mia descrizione', user: adminUser, portfolioType: PortfolioTypeEnum.Main)
        portfolio.save(flush: true, failOnError: true)

//        Entry entry = new Entry()
//      portfolioService.buy(portfolio, stock, 100);
//      portfolio.save(failOnError: true, insert: true, flush: true);

//        if (UserIndicators.getAll() == null || UserIndicators.getAll().size() == 0) {
//
//            UserIndicators userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.Normalized, name: "normalized");
//            userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SingularSpectrumFirstComponent, name: "ssa0", integer1: 50);
//            userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SingularSpectrumSecondComponent, name: "ssa1", integer1: 50);
//            userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SingularSpectrumThirdComponent, name: "ssa2", integer1: 50);
//            userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SingularSpectrumSecondThirdComponent, name: "ssa23", integer1: 50);
//            userIndicators.save(flush: true, failOnError: true)
//
////                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SingularSpectrumPrediction, name: "ssa predict", integer1: 50);
////                userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SimpleMovingAverage, name: "sma 10", integer1: 10);
//            userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SimpleMovingAverage, name: "sma 50", integer1: 50);
//            userIndicators.save(flush: true, failOnError: true)
//
//            userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.WeightedMovingAverage, name: "wma 50", integer1: 50);
//            userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SingularSpectrumFirstSecondComponent, name: "ssa01", integer1: 50);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.PriceChannelUpper, name: "PC Upper", integer1: 10, double2: 50);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.PriceChannelLower, name: "PC Lower", integer1: 10, double2: 50);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.UpperBollingerBand, name: "BB Upper", integer1: 10, double1: 2);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.LowerBollingerBand, name: "BB Lower", integer1: 10, double1: 2);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.SimpleMovingVariance, name: "Simple Moving variance", integer1: 10);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.Momentum, name: "Momentum", integer1: 10);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.MACD, name: "MACD", integer1: 13, integer2: 26);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.MACDSignal, name: "MACD-Signal", integer1: 13, integer2: 26, integer3: 5);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.RelativeStrengthIndex, name: "Relative Strength Index", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.RelativeStrengthIndex2, name: "Relative Strength Index 2", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.CommodityChannelIndexOverPeriod, name: "Commodity Channel Index Over Period", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.CommodityChannelIndicator, name: "Commodity Channel", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.MoneyFlowOverPeriod, name: "MoneyFlow Over Period", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.ChaikinMoneyFlowOverPeriod, name: "Chaikin MoneyFlow Over Period", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.ChaikinOscillatorOverPeriod, name: "Chaikin Oscillator Over Period", integer1: 1);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.AroonOscillatorOverPeriod, name: "Aroon Oscillator Over Period", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.AroonDownOverPeriod, name: "Aaron Down Over Period", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.AroonUpOverPeriod, name: "Aaron Up Over Period", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.TrueRangePeriod, name: "True Range Over Period");
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.kFastStochasticPeriod, name: "K Fast Stochastic", integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.dStochastic, name: "D Stochastic Indicator", integer1: 1, integer2: 14, integer3: 10);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.DStochasticSmoothed, name: "D Stochastic Smoothed", integer1: 10, smoothing1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.ChaikinVolatility, name: "Chaikin Volatility", smoothing1: 10, integer1: 14);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.PlusDirectionalMovementPeriod, name: "Plus DMI Indicator");
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.RateOfChangePeriod, name: "Rate of Change Over Period", integer1: 10);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.Kairi, name: "Kairi Indicator", smoothing1: 10);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.MomentumPctPeriod, name: "Momentum Pct Period Indicator", integer1: 10);
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.MarketFacilitationIndexOverPeriod, name: "Market Facilitation Index");
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.BalanceOfPowerOverPeriod, name: "Balance of Power");
//                userIndicators.save(flush: true, failOnError: true)
//
//                userIndicators = new UserIndicators(user: adminUser, type: IndicatorEnum.PriceActionOverPeriod, name: "Price action over period");
//                userIndicators.save(flush: true, failOnError: true)
//
//        }
    }

    def destroy = {
    }

}