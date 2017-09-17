(ns movie-database.client-test-main
  (:require movie-database.tests-to-run
            [fulcro-spec.selectors :as sel]
            [fulcro-spec.suite :as suite]))

(enable-console-print!)

(suite/def-test-suite client-tests {:ns-regex #"movie-database\..*-spec"}
  {:default   #{::sel/none :focused}
   :available #{:focused}})

