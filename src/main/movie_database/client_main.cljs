(ns movie-database.client-main
  (:require [movie-database.client :refer [app]]
            [fulcro.client.core :as core]
            [movie-database.ui.root :as root]))

;; In dev mode, we mount from cljs/user.cljs
(reset! app (core/mount @app root/Root "app"))
