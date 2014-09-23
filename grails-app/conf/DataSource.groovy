
dataSource {
    //ORACLE
    dbCreate = "create-drop"
    url = "jdbc:oracle:thin:@127.0.0.1:1521:XE"
    pooled = true
    username = "dtmc"
    password = "dtmc"
    driverClassName = "oracle.jdbc.driver.OracleDriver"
//    dialect = "org.hibernate.dialect.OracleDialect"
    dialect = org.hibernate.dialect.Oracle10gDialect

    properties {
        maxActive = -1
        minEvictableIdleTimeMillis = 1800000
        timeBetweenEvictionRunsMillis = 1800000
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1 from dual"
    }
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context

    jdbc.use_get_generated_keys = true
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop"
            username = "dtmc_dev"
            password = "dtmc_dev"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            username = "dtmc_test"
            password = "dtmc_test"
        }
    }
    production {
        dataSource {
            dbCreate = "none"
            username = "dtmc"
            password = "dtmc"
        }
    }
}
