(defproject com.billpiel/stackplan "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Apache 2.0 License"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:plugins [[lein-midje "3.1.3"]]}
             :test {:dependencies [[midje "1.6.3"]]}}
  :aliases {"midje" ["with-profile" "dev,test" "midje"]})
