(ns movie-database.ui.components
  (:require
   [fulcro.client.core :as fc]
   [movie-database.api.mutations :as api]
   [om.next :as om :refer [defui]]
   [om.dom :as dom]))

(defui ^:once PlaceholderImage
  Object
  (render [this]
    (let [{:keys [w h label]} (om/props this)
          label (or label (str w "x" h))]
      (dom/svg #js {:width w :height h}
        (dom/rect #js {:width w :height h :style #js {:fill        "rgb(200,200,200)"
                                                      :strokeWidth 2
                                                      :stroke      "black"}})
        (dom/text #js {:textAnchor "middle" :x (/ w 2) :y (/ h 2)} label)))))

(def ui-placeholder (om/factory PlaceholderImage))

(defui ^:once Counter
  static om/IQuery
  (query [this] [:counter/count])
  Object
  (render [this]
          (let [{:keys [:counter/count]} (om/props this)]
            (dom/div nil
                     (dom/p nil count)
                     (dom/button #js {:onClick #(om/transact! this `[(api/inc-counter {})])}
                                 "+1" )))))

(def ui-counter (om/factory Counter))

(def person-style #js {:margin "0" :padding "0" :font-weight "lighter"})
(def person-style-bold (assoc person-style :font-weight "bold"))

(defui ^:once Person
  static om/Ident
  (ident [this props] [:people/by-id (:db/id props)])
  static om/IQuery
  (query [this] [:db/id :person/name :person/age])
  Object
  (render [this]
          (let [{:keys [db/id person/name person/age]} (om/props this)]
            (dom/div #js {:className "person" :key id :style #js {:display "flex"
                                                                  :flexDirection "column"
                                                                  :justifyContent "center"
                                                                  :alignItems "center"}}
                     (dom/p #js {:style person-style} name)
                     (dom/p #js {:style person-style} age)))))

(def ui-person (om/factory Person {:keyfn :db/id}))

(defui ^:once PersonList
  static om/Ident
  (ident [this props] [:person-list/by-id (:db/id props)])
  static om/IQuery
  (query [this] [:db/id :person-list/label {:person-list/people (om/get-query Person)}])
  Object
  (render [this]
          (let [{:keys [db/id person-list/label person-list/people]} (om/props this)]
            (dom/div #js {:className "person-list" :key id}
                     (dom/h3 #js {:style #js {:text-align "center"
                                              :background-color "gray"
                                              :color "#fff"
                                              :font-weight "lighter"
                                              :padding "5px 10px 5px 10px"}} (str label " (" (count people) ")"))
                     (dom/div #js {:style #js {:display "flex"
                                               :justifyContent "space-around"
                                               :alignItems "center"}}
                              (map ui-person people))))))

(def ui-person-list (om/factory PersonList))

(defui ^:once Collection
  static om/IQuery
  (query [this] [:ui/react-key [:counter/count (om/get-query Counter)] {:lists (om/get-query PersonList)}])
  Object
  (render [this]
          (let [{:keys [lists ui/react-key counter/count]} (om/props this)]
            (dom/div #js {:key react-key}
                     (ui-counter count)
                     (map ui-person-list lists)))))
