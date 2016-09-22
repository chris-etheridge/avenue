(defproject avenue "0.2.7"
  :description "A super simple routing library."
  :url "https://github.com/chris-etheridge/avenue"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.89"]
                 [org.clojure/core.async "0.2.385"
                  :exclusions [org.clojure/tools.reader]]
                 [rum "0.10.5"]]

  :plugins [[lein-figwheel "0.5.4-7"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src"]
                :figwheel     {:on-jsload "avenue.core/on-js-reload"}

                :compiler {:main                 avenue.core
                           :asset-path           "js/compiled/out"
                           :output-to            "resources/public/js/compiled/avenue.js"
                           :output-dir           "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads             [devtools.preload]}}
               {:id           "release"
                :source-paths ["src"]
                :compiler     {:output-to       "target/js/avenue.js"
                               :main            avenue.core
                               :optimizations   :advanced
                               :pretty-print    false
                               :compiler-stats  true
                               :closure-defines {"goog.DEBUG" false}
                               :warnings        true
                               :verbose         true}}]}

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.7.2"]
                                  [figwheel-sidecar "0.5.4-7"]
                                  [com.cemerick/piggieback "0.2.1"]]
                   :source-paths ["src" "dev"]
                   :repl-options {:init             (set! *print-length* 50)
                                  :nrepl-middleware [[cemerick.piggieback/wrap-cljs-repl]
                                                     [lighttable.nrepl.handler/lighttable-ops]]}}})
