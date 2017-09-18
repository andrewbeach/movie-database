(ns movie-database.intro
  (:require [devcards.core :as rc :refer-macros [defcard]]
            [fulcro.client.cards :refer [defcard-fulcro]]
            [om.next :as om :refer-macros [defui]]
            [movie-database.ui.components :as comp]
            [om.dom :as dom]))

(defcard SVGPlaceholder
  "# SVG Placeholder"
  (comp/ui-placeholder {:w 200 :h 200})
  {}
  {:hidden true})

(def my-friends [{:db/id 1 :person/name "Jack" :person/age 35}
                {:db/id 2 :person/name "Josh"}
                 {:db/id 3 :person/name "Andrew" :person/age 29}])

(def my-enemies [{:db/id 4 :person/name "Ken" :person/age 30}
                 {:db/id 5 :person/name "Alex" :person/age 20}])

(defcard Person
  "# Person"
  (fn [state-atom _]
    (dom/div nil
             (dom/p #js {:className "counter"} (str "Count: " (:count @state-atom)))
             (dom/div #js {:className "people"}
                      (map comp/ui-person my-friends))))
  {:count 50}
  {:inspect-data true :history true :hidden true})

(defcard-fulcro multiple-lists
  "# My people lists"
  comp/Collection
  {:counter/count 20
   :lists
   [{:db/id :friends
     :person-list/label "Friends"
     :person-list/people my-friends}
    {:db/id :enemies
     :person-list/label "Enemies"
     :person-list/people my-enemies}]}
  {:inspect-data false})

(defcard-fulcro Counter
  "# Counter"
  comp/Counter
  {:counter/count 4}
  {:inspect-data true :hidden true})
